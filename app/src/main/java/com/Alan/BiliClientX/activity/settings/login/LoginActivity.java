package com.Alan.BiliClientX.activity.settings.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.base.BaseActivity;
import com.Alan.BiliClientX.adapter.viewpager.ViewPagerFragmentAdapter;
import com.Alan.BiliClientX.util.MsgUtil;
import com.Alan.BiliClientX.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

//登录页面，参考了腕上哔哩和WearBili的代码

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_viewpager);
        Log.e("debug", "进入登录页面");
        setPageName("登录");

        boolean from_setup = getIntent().getBooleanExtra("from_setup", false);

        /*
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivity(new Intent(this, SpecialLoginActivity.class).putExtra("from_setup",from_setup));
            finish();
        }
         */

        ViewPager viewPager = findViewById(R.id.viewPager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(QRLoginFragment.newInstance(from_setup));

        viewPager.setOffscreenPageLimit(fragmentList.size());
        ViewPagerFragmentAdapter vpfAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(vpfAdapter);

        findViewById(R.id.loading).setVisibility(View.GONE);
        if (fragmentList.size() > 1 && SharedPreferencesUtil.getBoolean("first_" + LoginActivity.class.getSimpleName(), true)) {
            MsgUtil.showMsgLong("提示：本页面可以左右滑动");
            SharedPreferencesUtil.putBoolean("first_" + LoginActivity.class.getSimpleName(), false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}