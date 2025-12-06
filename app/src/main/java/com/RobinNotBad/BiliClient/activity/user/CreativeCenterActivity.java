package com.RobinNotBad.BiliClient.activity.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.RobinNotBad.BiliClient.R;
import com.RobinNotBad.BiliClient.activity.base.BaseActivity;
import com.RobinNotBad.BiliClient.api.CreativeCenterApi;
import com.RobinNotBad.BiliClient.util.CenterThreadPool;
import com.RobinNotBad.BiliClient.util.MsgUtil;
import com.RobinNotBad.BiliClient.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreativeCenterActivity extends BaseActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncInflate(R.layout.activity_creative_center, (view, id) -> {
            CenterThreadPool.run(() -> {
                try {
                    JSONObject stats = CreativeCenterApi.getVideoStat();
                    JSONObject scrolls = CreativeCenterApi.getBeUPTime();

                    runOnUiThread(() -> {
                        try {
                            if (stats == null) {
                                MsgUtil.showMsg("先去成为UP主吧~");
                                finish();
                            } else {
                                setStatsText(R.id.totalFans_number, stats, "total_fans", "incr_fans");
                                setStatsText(R.id.totalClick_number, stats, "total_click", "incr_click");
                                setStatsText(R.id.totalLike_number, stats, "total_like", "inc_like");
                                setStatsText(R.id.totalCoin_number, stats, "total_coin", "inc_coin");
                                setStatsText(R.id.totalFavourite_number, stats, "total_fav", "inc_fav");
                                setStatsText(R.id.totalShare_number, stats, "total_share", "inc_share");
                                setStatsText(R.id.totalReply_number, stats, "total_reply", "incr_reply");
                                setStatsText(R.id.totalDm_number, stats, "total_dm", "incr_dm");

                                // 单独处理 scrolls 数据
                                setScrollsText(R.id.totalBeUP_day, scrolls);
                            }
                        } catch (Exception e) {
                            runOnUiThread(() -> MsgUtil.err(e));
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> MsgUtil.err(e));
                }
            });
        });
    }

    // 专门处理 scrolls 数据的方法
    @SuppressLint("SetTextI18n")
    private void setScrollsText(int viewId, JSONObject scrolls) throws JSONException {
        TextView textView = findViewById(viewId);

        if (scrolls == null) {
            textView.setText("0");
            return;
        }

        try {
            // 尝试直接获取整数值
            int totalValue = scrolls.getInt("name");
            int incrValue = scrolls.getInt("inc_be_up_day");

            String totalText = StringUtil.toWan(totalValue);
            String incrSymbol = (incrValue < 0) ? "" : "+";
            String incrText = StringUtil.toWan(incrValue);

            textView.setText(totalText + incrSymbol + incrText);
        } catch (JSONException e) {
            // 如果直接转换失败，尝试从字符串中提取数字
            try {
                String nameStr = scrolls.getString("name");
                String incStr = scrolls.getString("inc_be_up_day");

                // 使用正则表达式提取数字
                int totalValue = extractNumberFromString(nameStr);
                int incrValue = extractNumberFromString(incStr);

                String totalText = StringUtil.toWan(totalValue);
                String incrSymbol = (incrValue < 0) ? "" : "+";
                String incrText = StringUtil.toWan(incrValue);

                textView.setText(totalText + incrSymbol + incrText);
            } catch (Exception ex) {
                // 如果所有方法都失败，显示原始字符串
                String displayText = scrolls.optString("name", "0") + " " +
                        scrolls.optString("inc_be_up_day", "+0");
                textView.setText(displayText);
            }
        }
    }

    // 从字符串中提取数字的辅助方法
    private int extractNumberFromString(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        try {
            // 方法1：尝试直接解析
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // 方法2：使用正则表达式提取数字
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(str);

            if (matcher.find()) {
                try {
                    return Integer.parseInt(matcher.group());
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setStatsText(int viewId, JSONObject jsonObject, String totalKey, String incrKey) throws JSONException {
        TextView textView = findViewById(viewId);
        int totalValue = jsonObject.getInt(totalKey);
        int incrValue = jsonObject.getInt(incrKey);

        String totalText = StringUtil.toWan(totalValue);
        String incrSymbol = (incrValue < 0) ? "" : "+";
        String incrText = StringUtil.toWan(incrValue);

        textView.setText(totalText + incrSymbol + incrText);
    }
}