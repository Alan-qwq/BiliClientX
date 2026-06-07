package com.Alan.BiliClientX.activity.user;

import android.os.Bundle;
import android.util.Log;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.video.VideoCardAdapter;
import com.Alan.BiliClientX.api.BangumiApi;
import com.Alan.BiliClientX.model.VideoCard;
import com.Alan.BiliClientX.util.CenterThreadPool;

import java.util.ArrayList;
import java.util.List;

//追番列表
//2024-06-07

public class FollowingBangumisActivity extends RefreshListActivity {

    private ArrayList<VideoCard> videoList;
    private VideoCardAdapter videoCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPageName("追番列表");

        recyclerView.setHasFixedSize(true);

        videoList = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                int result = BangumiApi.getFollowingList(page, videoList);
                if (result != -1) {
                    videoCardAdapter = new VideoCardAdapter(this, videoList);
                    setOnLoadMoreListener(this::continueLoading);
                    setRefreshing(false);
                    setAdapter(videoCardAdapter);

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

    private void continueLoading(int page) {
        CenterThreadPool.run(() -> {
            try {
                List<VideoCard> list = new ArrayList<>();
                int result = BangumiApi.getFollowingList(page, list);
                if (result != -1) {
                    Log.e("debug", "下一页");
                    runOnUiThread(() -> {
                        videoList.addAll(list);
                        videoCardAdapter.notifyItemRangeInserted(videoList.size() - list.size(), list.size());
                    });
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