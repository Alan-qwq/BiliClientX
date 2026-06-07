package com.Alan.BiliClientX.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.adapter.InteractionDebugAdapter;
import com.Alan.BiliClientX.model.InteractionVideoData;

import java.util.List;

public class InteractionDebugActivity extends BaseActivity {

    private static InteractionVideoData staticInteractionData;

    public static void setInteractionData(InteractionVideoData data) {
        staticInteractionData = data;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction_debug);

        setPageName("互动视频变量调试");

        if (staticInteractionData == null || staticInteractionData.hiddenVars == null || staticInteractionData.hiddenVars.isEmpty()) {
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.debug_var_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InteractionDebugAdapter adapter = new InteractionDebugAdapter(staticInteractionData.hiddenVars);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        staticInteractionData = null;
    }
}

