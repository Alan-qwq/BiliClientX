package com.Alan.BiliClientX.activity.user;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.adapter.CoinLogAdapter;
import com.Alan.BiliClientX.api.CoinLogApi;
import com.Alan.BiliClientX.model.CoinLog;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.MsgUtil;

import java.util.ArrayList;
import java.util.List;

public class CoinLogActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<CoinLog> logList;
    private CoinLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_refresh);

        setPageName("硬币变化记录");

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logList = new ArrayList<>();

        CenterThreadPool.run(() -> {
            try {
                logList = CoinLogApi.getCoinLog();

                runOnUiThread(() -> {
                    if (logList.isEmpty()) {
                        MsgUtil.showMsg("暂无硬币变化记录");
                        findViewById(R.id.emptyTip).setVisibility(View.VISIBLE);
                    } else {
                        adapter = new CoinLogAdapter(this, logList);
                        recyclerView.setAdapter(adapter);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    MsgUtil.showMsg("加载失败：" + e.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                    findViewById(R.id.emptyTip).setVisibility(View.VISIBLE);
                });
                e.printStackTrace();
            }
        });
    }
}

