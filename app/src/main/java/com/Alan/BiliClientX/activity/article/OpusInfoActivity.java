package com.Alan.BiliClientX.activity.article;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.activity.dynamic.DynamicInfoActivity;
import com.Alan.BiliClientX.activity.reply.ReplyFragment;
import com.Alan.BiliClientX.adapter.viewpager.ViewPagerFragmentAdapter;
import com.Alan.BiliClientX.event.ReplyEvent;
import com.Alan.BiliClientX.helper.TutorialHelper;
import com.Alan.BiliClientX.model.Opus;
import com.Alan.BiliClientX.util.AnimationUtils;
import com.Alan.BiliClientX.util.MsgUtil;
import com.Alan.BiliClientX.util.TerminalContext;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class OpusInfoActivity extends BaseActivity {
    private long oid;

    private ReplyFragment replyFragment;
    private long seek_reply;

    private ImageView loadingView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_viewpager);
        Intent intent = getIntent();
        oid = intent.getLongExtra("id", 114514);
        seek_reply = getIntent().getLongExtra("seekReply", -1);

        setPageName("文章详情");
        loadingView = findViewById(R.id.loading);

        //TutorialHelper.showTutorialList(this, R.array.tutorial_article, 7);

        ViewPager viewPager = findViewById(R.id.viewPager);


        TerminalContext.getInstance().getOpusById(oid)
                .observe(this, (result) -> result.onSuccess((opus) -> {
                    if (opus.type == Opus.TYPE_DYNAMIC_OLD_STYLE) {
                        Intent intent1 = new Intent(this, DynamicInfoActivity.class);
                        intent1.putExtra("id", oid);
                        intent1.putExtra("seekReply", seek_reply);
                        startActivity(intent1);
                        finish();
                        return;
                    }

                    List<Fragment> fragmentList = new ArrayList<>();

                    OpusInfoFragment oiFragment = OpusInfoFragment.newInstance(oid);
                    fragmentList.add(oiFragment);

                    replyFragment = ReplyFragment.newInstance(opus.commentId, opus.commentType, opus.stats.reply, seek_reply, opus.upInfo.mid);
                    replyFragment.setManager(opus.upInfo);
                    fragmentList.add(replyFragment);

                    ViewPagerFragmentAdapter vpfAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
                    viewPager.setAdapter(vpfAdapter);
                    if (seek_reply != -1) viewPager.setCurrentItem(1);

                    AnimationUtils.crossFade(loadingView, oiFragment.getView());
                    TutorialHelper.showPagerTutorial(this, 2);
                }).onFailure((error) -> {
                    loadingView.setImageResource(R.mipmap.loading_2233_error);
                    MsgUtil.err(error);
                }));


    }

    @Override
    protected boolean eventBusEnabled() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true, priority = 1)
    public void onEvent(ReplyEvent event) {
        //replyFragment.notifyReplyInserted(event);
    }

    @Override
    protected void onDestroy() {
        //TerminalContext.getInstance().leaveDetailPage();
        super.onDestroy();
    }
}
