package com.Alan.BiliClientX.activity.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;

public class UIPreviewActivity extends BaseActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_ui_preview);
    }
}