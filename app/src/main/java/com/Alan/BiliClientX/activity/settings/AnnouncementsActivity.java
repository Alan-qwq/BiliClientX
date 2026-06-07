package com.Alan.BiliClientX.activity.settings;

import android.os.Bundle;

import com.Alan.BiliClientX.activity.base.RefreshListActivity;
import com.Alan.BiliClientX.adapter.AnnouncementAdapter;
import com.Alan.BiliClientX.api.AppInfoApi;
import com.Alan.BiliClientX.model.Announcement;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.MsgUtil;

import java.util.ArrayList;

//公告列表
//2024-02-23

public class AnnouncementsActivity extends RefreshListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPageName("公告列表");

        CenterThreadPool.run(() -> {
            try {
                ArrayList<Announcement> announcements = AppInfoApi.getAnnouncementList();
                setRefreshing(false);

                AnnouncementAdapter adapter = new AnnouncementAdapter(this, announcements);

                setAdapter(adapter);

            } catch (Exception e) {
                report(e);
                runOnUiThread(() -> MsgUtil.showMsg("连接到哔哩终端接口时发生错误"));
            }
        });
    }

}