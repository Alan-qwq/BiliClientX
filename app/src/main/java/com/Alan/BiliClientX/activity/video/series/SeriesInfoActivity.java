package com.Alan.BiliClientX.activity.video.series;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.video.VideoCardAdapter;
import com.Alan.BiliClientX.api.SeriesApi;
import com.Alan.BiliClientX.model.PageInfo;
import com.Alan.BiliClientX.model.VideoCard;
import com.Alan.BiliClientX.util.CenterThreadPool;

import java.util.ArrayList;

public class SeriesInfoActivity extends RefreshListActivity {

    private String type;
    private long mid;
    private int sid;
    private ArrayList<VideoCard> videoList;
    private VideoCardAdapter videoCardAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        mid = intent.getLongExtra("mid", 0);
        sid = intent.getIntExtra("sid", 0);
        String name = intent.getStringExtra("name");

        setPageName(name);

        videoList = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                PageInfo pageInfo = SeriesApi.getSeriesInfo(type, mid, sid, page, videoList);
                if (pageInfo.return_ps != 0) {
                    videoCardAdapter = new VideoCardAdapter(this, videoList);

                    setOnLoadMoreListener(this::continueLoading);
                    setAdapter(videoCardAdapter);

                    Log.e("debug", "return=" + pageInfo.return_ps + "require=" + pageInfo.require_ps);

                    if (pageInfo.return_ps < pageInfo.require_ps) {
                        Log.e("debug", "到底了");
                        setBottom(true);
                    }
                } else showEmptyView();
                setRefreshing(false);
            } catch (Exception e) {
                loadFail(e);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void continueLoading(int page) {
        CenterThreadPool.run(() -> {
            try {
                Log.e("debug", "下一页");
                int lastSize = videoList.size();
                PageInfo pageInfo = SeriesApi.getSeriesInfo(type, mid, sid, page, videoList);
                runOnUiThread(() -> videoCardAdapter.notifyItemRangeInserted(lastSize, pageInfo.return_ps));

                if (pageInfo.return_ps < pageInfo.require_ps || pageInfo.return_ps == 0) {
                    Log.e("debug", "到底了");
                    setBottom(true);
                }
                setRefreshing(false);
            } catch (Exception e) {
                loadFail(e);
            }
        });
    }
}
