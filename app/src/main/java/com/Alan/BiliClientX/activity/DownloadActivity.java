package com.Alan.BiliClientX.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.api.AppInfoApi;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.FileUtil;
import com.Alan.BiliClientX.util.MsgUtil;
import com.Alan.BiliClientX.util.NetWorkUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class DownloadActivity extends BaseActivity {

    View progressView;
    TextView progressText;

    File rootPath, downPath, downFile;
    String link;
    int scrHeight;

    String dldText = "";
    float dldPercent = 0;

    int type;

    boolean finish = false;

    boolean no_bili_headers = false;

    final Timer timer = new Timer();
    final TimerTask showText = new TimerTask() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            int viewHeight = (int) (dldPercent * scrHeight);
            runOnUiThread(() -> {
                progressText.setText(dldText + "\n" + (dldPercent * 100) + "%");
                ViewGroup.LayoutParams params = progressView.getLayoutParams();
                params.height = viewHeight;
                progressView.setLayoutParams(params);
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Intent intent = getIntent();

        type = intent.getIntExtra("type", 0);  //0=单个文件，1=视频，2=分页视频
        link = intent.getStringExtra("link");
        no_bili_headers = intent.getBooleanExtra("terminal", false);

        progressText = findViewById(R.id.progressText);
        progressView = findViewById(R.id.progressView);

        scrHeight = window_height;

        if (!FileUtil.checkStoragePermission()) FileUtil.requestStoragePermission(this);

        timer.schedule(showText, 100, 100);
        CenterThreadPool.run(() -> {
            if (type == 0) {
                rootPath = new File(intent.getStringExtra("path"));
                if (!rootPath.exists()) rootPath.mkdirs();
                downFile = new File(rootPath, FileUtil.getFileNameFromLink(link));
                download(link, downFile, "下载文件中", true);
            } else {
                String title = FileUtil.stringToFile(intent.getStringExtra("title"));

                rootPath = FileUtil.getVideoDownloadPath();

                if (type == 1) {
                    downPath = new File(rootPath, title);
                    rootPath = downPath;
                }
                if (type == 2) {
                    rootPath = new File(rootPath, FileUtil.stringToFile(intent.getStringExtra("parent_title")));
                    downPath = new File(rootPath, title);
                }

                if (!downPath.exists()) downPath.mkdirs();

                String danmaku = intent.getStringExtra("danmaku");
                String cover = intent.getStringExtra("cover");
                File dmFile = new File(downPath, "danmaku.xml");
                File coverFile = new File(rootPath, "cover.png");
                File videoFile = new File(downPath, "video.mp4");
                downdanmu(danmaku, dmFile);
                if (!coverFile.exists()) download(cover, coverFile, "下载封面", false);
                download(link, videoFile, "下载视频", true);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void download(String url, File file, String desc, boolean exitOnFinish) {
        dldText = desc;
        try {
            ArrayList<String> headers = new ArrayList<>(no_bili_headers ? AppInfoApi.customHeaders : NetWorkUtil.webHeaders);

            // 断点续传：检查已下载的文件大小
            long existingSize = 0;
            if (file.exists()) {
                existingSize = file.length();
                if (existingSize > 0) {
                    headers.add("Range");
                    headers.add("bytes=" + existingSize + "-");
                }
            }

            Request.Builder requestBuilder = new Request.Builder().url(url).get();
            for (int i = 0; i < headers.size(); i += 2)
                requestBuilder.addHeader(headers.get(i), headers.get(i + 1));
            Response response = NetWorkUtil.getOkHttpInstance().newCall(requestBuilder.build()).execute();

            ResponseBody body = response.body();
            if (body == null) {
                runOnUiThread(() -> MsgUtil.showMsg("下载失败：响应为空"));
                response.close();
                finish();
                return;
            }

            long contentLength = body.contentLength();
            boolean isPartial = response.code() == 206;

            // 如果服务器不支持 Range（返回 200），从头开始下载
            if (!isPartial) {
                existingSize = 0;
            }

            long totalFileSize = isPartial ? existingSize + contentLength : contentLength;

            InputStream inputStream = body.byteStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file, isPartial);

            int len;
            byte[] bytes = new byte[1024 * 10];
            long downloadedSize = existingSize;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
                downloadedSize += len;
                if (totalFileSize > 0) {
                    dldPercent = (float) downloadedSize / totalFileSize;
                }
            }
            fileOutputStream.flush();
            inputStream.close();
            fileOutputStream.close();
            if (exitOnFinish) {
                runOnUiThread(() -> MsgUtil.showMsg("下载完成"));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish = true;
                        finish();
                    }
                }, 200);
            }
            body.close();
            response.close();
        } catch (IOException e) {
            runOnUiThread(() -> MsgUtil.showMsg("下载失败"));
            e.printStackTrace();
            finish();
        }
    }


    private void downdanmu(String danmaku, File danmakuFile) {
        try {
            Response response = NetWorkUtil.get(danmaku,
                    no_bili_headers ? AppInfoApi.customHeaders : NetWorkUtil.webHeaders);
            BufferedSink bufferedSink = null;
            try {
                if (!danmakuFile.exists()) danmakuFile.createNewFile();
                Sink sink = Okio.sink(danmakuFile);
                byte[] decompressBytes = decompress(Objects.requireNonNull(response.body()).bytes());//调用解压函数进行解压，返回包含解压后数据的byte数组
                bufferedSink = Okio.buffer(sink);
                bufferedSink.write(decompressBytes);//将解压后数据写入文件（sink）中
                bufferedSink.close();
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            } finally {
                if (bufferedSink != null) {
                    bufferedSink.close();
                }
            }
            if (response.body() != null) response.body().close();
            response.close();
        } catch (IOException e) {
            runOnUiThread(() -> MsgUtil.showMsg("弹幕下载失败！"));
            finish();
            e.printStackTrace();
        }
    }


    public static byte[] decompress(byte[] data) {
        byte[] output;
        Inflater decompresser = new Inflater(true);//这个true是关键
        decompresser.reset();
        decompresser.setInput(data);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[2048];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decompresser.end();
        return output;
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        if (!finish) {
            if (type != 0 && downPath != null) FileUtil.deleteFolder(downPath);
            else if (downFile != null) downFile.delete();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
