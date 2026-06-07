package com.Alan.BiliClientX.activity.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.activity.user.info.UserInfoActivity;
import com.Alan.BiliClientX.util.CenterThreadPool;
import com.Alan.BiliClientX.util.GlideUtil;
import com.Alan.BiliClientX.util.MsgUtil;
import com.Alan.BiliClientX.util.NetWorkUtil;
import com.Alan.BiliClientX.util.SharedPreferencesUtil;
import com.Alan.BiliClientX.util.StringUtil;
import com.Alan.BiliClientX.util.ToolsUtil;

import org.json.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {
    int eggClick_authorWords = 0, eggClick_toUncle = 0, eggClick_Dev = 0;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asyncInflate(R.layout.activity_setting_about, (layoutView, resId) -> {
            Log.e("debug", "进入关于页面");

            try {
                SpannableString version_str = new SpannableString("版本名\n" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                version_str.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ((TextView) findViewById(R.id.app_version)).setText(version_str);

                SpannableString code_str = new SpannableString("版本号\n" + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
                code_str.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ((TextView) findViewById(R.id.app_version_code)).setText(code_str);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            List<ImageView> developerAvaterViews = new ArrayList<>() {{
                add(findViewById(R.id.alanAvatar));
                add(findViewById(R.id.robinAvatar));
                add(findViewById(R.id.duduAvatar));
                add(findViewById(R.id.dadaAvatar));
                add(findViewById(R.id.moyeAvatar));
                add(findViewById(R.id.silentAvatar));
                add(findViewById(R.id.huanliAvatar));
                add(findViewById(R.id.jankAvatar));
            }};
            List<Integer> developerAvaters = new ArrayList<>() {{
                add(-1); // Alan: 实时获取
                add(R.mipmap.avatar_robin);
                add(R.mipmap.avatar_dudu);
                add(-1);
                add(R.mipmap.avatar_moye);
                add(R.mipmap.avatar_silent);
                add(R.mipmap.avatar_huanli);
                add(R.mipmap.avatar_jank);
            }};
            List<MaterialCardView> developerCardList = new ArrayList<>() {{
                add(findViewById(R.id.alan_card));
                add(findViewById(R.id.robin_card));
                add(findViewById(R.id.dudu_card));
                add(findViewById(R.id.dada_card));
                add(findViewById(R.id.moye_card));
                add(findViewById(R.id.silent_card));
                add(findViewById(R.id.huanli_card));
                add(findViewById(R.id.jank_card));
            }};
            List<Long> developerUidList = new ArrayList<>() {{
                add(3493115176422084L);
                add((long) 646521226);
                add((long) 517053179);
                add((long) 432128342);
                add((long) 394675616);
                add((long) 40140732);
                add((long) 673815151);
                add((long) 661403494);
            }};

            // Alan 头像实时获取
            final AboutActivity activity = this;
            final ImageView alanAvatarView = findViewById(R.id.alanAvatar);
            CenterThreadPool.run(() -> {
                try {
                    String url = "https://api.bilibili.com/x/space/acc/info?mid=" + 3493115176422084L;
                    JSONObject json = NetWorkUtil.getJson(url, NetWorkUtil.webHeaders);
                    if (json.getInt("code") == 0) {
                        String face = json.getJSONObject("data").getString("face");
                        activity.runOnUiThread(() -> {
                            try {
                                Glide.with(activity).load(GlideUtil.url(face))
                                        .transition(GlideUtil.getTransitionOptions())
                                        .placeholder(R.mipmap.akari)
                                        .apply(RequestOptions.circleCropTransform())
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .into(alanAvatarView);
                            } catch (Exception ignored) {
                            }
                        });
                    }
                } catch (Exception ignored) {
                }
            });

            // 其他开发者头像使用本地资源
            for (int i = 1; i < developerAvaterViews.size(); i++) {
                if (developerAvaters.get(i) != -1) try {
                    Glide.with(this).load(developerAvaters.get(i))
                            .transition(GlideUtil.getTransitionOptions())
                            .placeholder(R.mipmap.akari)
                            .apply(RequestOptions.circleCropTransform())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(developerAvaterViews.get(i));
                } catch (Exception ignored) {
                }
            }

            for (int i = 0; i < developerCardList.size(); i++) {
                int finalI = i;
                developerCardList.get(i).setOnClickListener(view -> {
                    long uid = developerUidList.get(finalI);
                    if (uid == -1) return;
                    Intent intent = new Intent()
                            .setClass(this, UserInfoActivity.class)
                            .putExtra("mid", uid);
                    startActivity(intent);
                });
            }

            findViewById(R.id.author_words).setOnClickListener(view -> {
                eggClick_authorWords++;
                if (eggClick_authorWords == 7) {
                    eggClick_authorWords = 0;
                    MsgUtil.showText("作者的话", getString(R.string.egg_about_author_words));
                }
            });

            findViewById(R.id.toUncle).setOnClickListener(view -> {
                eggClick_toUncle++;
                if (eggClick_toUncle == 7) {
                    eggClick_toUncle = 0;
                    MsgUtil.showText("给叔叔", getString(R.string.egg_about_to_uncle));
                }
            });

            findViewById(R.id.icon_license_list).setOnClickListener(v -> {
                StringBuilder str = new StringBuilder(getString(R.string.desc_icon_license));

                String[] logItems = getResources().getStringArray(R.array.icon_license);
                for (int i = 0; i < logItems.length; i++)
                    str.append('\n').append((i + 1)).append('.').append(logItems[i]);
                MsgUtil.showText("开源图标的信息", str.toString());
            });

            findViewById(R.id.sponsor_list).setOnClickListener(view -> {
                Intent intent = new Intent(this, SponsorActivity.class);
                startActivity(intent);
            });

            if (!ToolsUtil.isDebugBuild()) findViewById(R.id.debug_tip).setVisibility(View.GONE);
            findViewById(R.id.version_code_card).setOnClickListener(view -> {
                if (SharedPreferencesUtil.getBoolean("developer", false)) {
                    MsgUtil.showMsg("已关闭开发者模式！");
                    SharedPreferencesUtil.putBoolean("developer", false);
                } else {
                    eggClick_Dev++;
                    if (eggClick_Dev == 7) {
                        SharedPreferencesUtil.putBoolean("developer", true);
                        MsgUtil.showMsg("已启用开发者模式！");
                        eggClick_Dev = 0;
                    }
                }
            });

            View scrollView = findViewById(R.id.scrollView);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        });

    }
}
