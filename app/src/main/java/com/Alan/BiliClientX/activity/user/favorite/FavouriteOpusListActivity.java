package com.Alan.BiliClientX.activity.user.favorite;

import android.os.Bundle;
import android.util.Log;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.article.OpusAdapter;
import com.Alan.BiliClientX.api.FavoriteApi;
import com.Alan.BiliClientX.model.Opus;
import com.Alan.BiliClientX.util.CenterThreadPool;

import java.util.ArrayList;

public class FavouriteOpusListActivity extends RefreshListActivity {
    ArrayList<Opus> list;
    OpusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageName("图文收藏夹");

        list = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                FavoriteApi.getFavouriteOpus(list, page);
                adapter = new OpusAdapter(this, list);
                Log.e("", "amount:" + list.size());
                setAdapter(adapter);
                setRefreshing(false);
            } catch (Exception e) {
                loadFail(e);
            }
        });

        setOnLoadMoreListener(this::loadMore);
    }

    public void loadMore(int page) {
        CenterThreadPool.run(() -> {
            try {
                int lastSize = list.size();
                setBottom(!FavoriteApi.getFavouriteOpus(list, page));
                runOnUiThread(() -> adapter.notifyItemRangeInserted(lastSize, list.size() - lastSize));
                setRefreshing(false);
            } catch (Exception e) {
                loadFail(e);
            }

        });
    }
}
