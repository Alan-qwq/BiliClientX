package com.RobinNotBad.BiliClient.api;

import android.text.TextUtils;

import com.RobinNotBad.BiliClient.model.OpusElement;
import com.RobinNotBad.BiliClient.util.Logu;
import com.RobinNotBad.BiliClient.util.NetWorkUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class OpusApi {

    public static void getOpusContent(long id) throws IOException {
        String url = "https://www.bilibili.com/opus/" + id;
        Response response = NetWorkUtil.get(url);
        ResponseBody responseBody = response.body();
        if(responseBody == null) return;

        Document document = Jsoup.parse(responseBody.string());
        Element contentModule = document.select("opus-module-content").first();
        if(contentModule == null) return;

        Elements elements = contentModule.children();
        for (Element element: elements) {
            OpusElement opusElement = new OpusElement();
            if(element.is("p")) {
                Elements spans = element.select("span");
                for (Element span : spans) {
                    String text = span.text();
                    String style = span.attr("style");
                    if (!TextUtils.isEmpty(style)) {
                        Map<String, String> styleMap = parseStyle(style);
                    }
                }
            }
            if(element.is("figure")){

            }

            if(element.is("div")){

            }
        }


    }


    /**
     * 将style字符串解析为键值对
     * 示例输入: "color:red; font-size:14px;"
     * 输出: {color=red, font-size=14px}
     * deepseek写的
     */
    private static Map<String, String> parseStyle(String styleValue) {
        Map<String, String> styleMap = new HashMap<>();

        if (styleValue == null || styleValue.isEmpty()) {
            return styleMap;
        }

        // 分割多个样式声明
        String[] stylePairs = styleValue.split(";");

        for (String pair : stylePairs) {
            // 分割键值对
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().toLowerCase();
                String value = keyValue[1].trim();

                // 移除值中的引号和分号
                value = value.replace(";", "").replace("\"", "").replace("'", "");

                styleMap.put(key, value);
            }
        }

        return styleMap;
    }
}
