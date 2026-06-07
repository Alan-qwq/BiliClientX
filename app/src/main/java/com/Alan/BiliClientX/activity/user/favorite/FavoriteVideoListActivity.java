package com.Alan.BiliClientX.activity.user.favorite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.video.VideoCardAdapter;
import com.Alan.BiliClientX.api.FavoriteApi;
import com.Alan.BiliClientX.model.VideoCard;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.MsgUtil;

import java.util.ArrayList;

//收藏夹内
//2023-08-08
//2024-05-01

public class FavoriteVideoListActivity extends RefreshListActivity {

    private long mid;
    private long fid;
    private ArrayList<VideoCard> videoList;
    private VideoCardAdapter videoCardAdapter;

    private int longClickPosition = -1;
    private long longClickTimestamp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mid = intent.getLongExtra("mid", 0);
        fid = intent.getLongExtra("fid", 0);
        String name = intent.getStringExtra("name");

        setPageName(name);

        videoList = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                int result = FavoriteApi.getFolderVideos(mid, fid, page, videoList);
                if (result != -1) {
                    videoCardAdapter = new VideoCardAdapter(this, videoList);

                    videoCardAdapter.setOnLongClickListener(position -> {
                        long timestamp = System.currentTimeMillis();
                        if (longClickPosition == position && timestamp - longClickTimestamp < 4000) {
                            CenterThreadPool.run(() -> {
                                try {
                                    int delResult = FavoriteApi.deleteFavorite(videoList.get(position).aid, fid);
                                    longClickPosition = -1;
                                    if (delResult == 0) runOnUiThread(() -> {
                                        MsgUtil.showMsg("删除成功");
                                        videoList.remove(position);
                                        videoCardAdapter.notifyItemRemoved(position);
                                        videoCardAdapter.notifyItemRangeChanged(position, videoList.size() - position);
                                    });
                                    else
                                        runOnUiThread(() -> MsgUtil.showMsg("删除失败，错误码：" + delResult));
                                } catch (Exception e) {
                                    report(e);
                                }
                            });
                        } else {
                            longClickPosition = position;
                            longClickTimestamp = timestamp;
                            MsgUtil.showMsg("再次长按删除");
                        }
                    });

                    setOnLoadMoreListener(this::continueLoading);
                    setAdapter(videoCardAdapter);
                    setRefreshing(false);

                    if (result == 1) {
                        Log.e("debug", "到底了");
                        setBottom(true);
                    }
                }

            } catch (Exception e) {
                loadFail(e);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void continueLoading(int page) {
        CenterThreadPool.run(() -> {
            try {
                int lastSize = videoList.size();
                int result = FavoriteApi.getFolderVideos(mid, fid, page, videoList);
                if (result != -1) {
                    Log.e("debug", "下一页");
                    runOnUiThread(() -> videoCardAdapter.notifyItemRangeInserted(lastSize, videoList.size() - lastSize));
                    if (result == 1) {
                        Log.e("debug", "到底了");
                        setBottom(true);
                    }
                }
                setRefreshing(false);
            } catch (Exception e) {
                loadFail(e);
            }
        });
    }
}