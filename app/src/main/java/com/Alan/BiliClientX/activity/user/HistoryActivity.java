package com.Alan.BiliClientX.activity.user;

import android.os.Bundle;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.video.VideoCardAdapter;
import com.Alan.BiliClientX.api.HistoryApi;
import com.Alan.BiliClientX.model.ApiResult;
import com.Alan.BiliClientX.model.VideoCard;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.MsgUtil;

import java.util.ArrayList;
import java.util.List;

//历史记录
//2023-08-18
//2024-04-30

public class HistoryActivity extends RefreshListActivity {

    private ApiResult lastResult = new ApiResult();
    private ArrayList<VideoCard> videoList;
    private VideoCardAdapter videoCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPageName("历史记录");

        recyclerView.setHasFixedSize(true);

        videoList = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                lastResult = HistoryApi.getHistory(lastResult, videoList);
                if (lastResult.code == 0) {
                    videoCardAdapter = new VideoCardAdapter(this, videoList);
                    setOnLoadMoreListener(this::continueLoading);
                    setRefreshing(false);
                    setAdapter(videoCardAdapter);

                    if (lastResult.isBottom) {
                        setBottom(true);
                    }
                } else MsgUtil.showMsg(lastResult.message);

            } catch (Exception e) {
                loadFail(e);
            }
        });
    }

    private void continueLoading(int page) {
        CenterThreadPool.run(() -> {
            try {
                List<VideoCard> list = new ArrayList<>();
                lastResult = HistoryApi.getHistory(lastResult, list);
                if (lastResult.code == 0) {
                    runOnUiThread(() -> {
                        videoList.addAll(list);
                        videoCardAdapter.notifyItemRangeInserted(videoList.size() - list.size(), list.size());
                    });
                    if (lastResult.isBottom) {
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