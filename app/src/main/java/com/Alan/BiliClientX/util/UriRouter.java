package com.Alan.BiliClientX.util;

import android.content.Context;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URI 路由工具类
 * 用于解析 B站 深度链接并跳转到对应页面
 * 参考 PiliPlus 的 PiliScheme 实现
 */
public class UriRouter {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("/(\\d+)");
    private static final Pattern BV_PATTERN = Pattern.compile("BV[A-Za-z0-9]+");

    /**
     * 从 URL 跳转到对应页面
     *
     * @param context 上下文
     * @param url     深度链接或HTTP链接
     * @return 是否成功跳转
     */
    public static boolean routeFromUrl(Context context, String url) {
        if (url == null || url.isEmpty()) return false;

        try {
            // 处理各种URL格式
            if (url.startsWith("//")) {
                url = "https:" + url;
            } else if (!url.contains("://")) {
                url = "https://" + url;
            }

            Uri uri = Uri.parse(url);
            return route(context, uri);
        } catch (Exception e) {
            Logu.e("UriRouter", "解析URL失败: " + url + " " + e.getMessage());
            return false;
        }
    }

    /**
     * 路由跳转
     */
    private static boolean route(Context context, Uri uri) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();

        if (scheme == null) return false;

        switch (scheme) {
            case "bilibili":
                return routeBilibili(context, uri, host, path);
            case "https":
            case "http":
                return routeHttp(context, uri, host, path);
            default:
                return false;
        }
    }

    /**
     * 处理 bilibili:// 协议
     */
    private static boolean routeBilibili(Context context, Uri uri, String host, String path) {
        if (host == null || path == null) return false;

        switch (host) {
            case "video":
                return handleVideo(context, uri, path);
            case "pgc":
                return handlePgc(context, uri, path);
            case "space":
                return handleSpace(context, uri, path);
            case "live":
                return handleLive(context, uri, path);
            case "bangumi":
                return handleBangumi(context, uri, path);
            case "comment":
                return handleComment(context, uri, path);
            case "opus":
            case "following":
                return handleOpus(context, uri, path);
            default:
                return false;
        }
    }

    /**
     * 处理 https:// 协议
     */
    private static boolean routeHttp(Context context, Uri uri, String host, String path) {
        if (host == null || path == null) return false;

        // www.bilibili.com/video/BVxxx
        if (host.contains("bilibili.com")) {
            if (path.contains("/video/")) {
                return handleVideo(context, uri, path);
            } else if (path.contains("/bangumi/")) {
                return handleBangumi(context, uri, path);
            }
        }

        return false;
    }

    /**
     * 处理视频链接
     * bilibili://video/12345678?dm_progress=123000&cid=12345678&dmid=12345678
     * bilibili://video/{aid}/?comment_root_id=***&comment_secondary_id=***
     */
    private static boolean handleVideo(Context context, Uri uri, String path) {
        String query = uri.getQuery();
        java.util.Map<String, String> params = parseQuery(query);

        // 检查是否是评论跳转
        if (params.containsKey("comment_root_id")) {
            String oid = extractFirstDigit(path);
            String rpidStr = params.get("comment_root_id");
            String secondaryId = params.get("comment_secondary_id");

            if (oid != null && rpidStr != null) {
                try {
                    long aid = Long.parseLong(oid);
                    long rpid = Long.parseLong(rpidStr);
                    long seekReply = rpid;

                    // 如果有 secondary_id，跳转到子评论
                    if (secondaryId != null && !secondaryId.isEmpty()) {
                        seekReply = Long.parseLong(secondaryId);
                    }

                    TerminalContext.getInstance().enterVideoDetailPage(context, aid, null, null, seekReply);
                    return true;
                } catch (NumberFormatException e) {
                    Logu.e("UriRouter", "解析评论ID失败: " + e.getMessage());
                }
            }
        }

        // 检查是否是弹幕跳转（带播放进度）
        String dmProgress = params.get("dm_progress");
        if (dmProgress == null) {
            dmProgress = params.get("start_progress");
        }

        // 提取 aid 或 bvid
        String aidStr = extractFirstDigit(path);
        String bvid = extractBvid(path);

        if (aidStr != null || bvid != null) {
            long aid = aidStr != null ? Long.parseLong(aidStr) : 0;
            long progress = -1;

            if (dmProgress != null) {
                try {
                    progress = Long.parseLong(dmProgress) / 1000; // 毫秒转秒
                } catch (NumberFormatException e) {
                    // 忽略
                }
            }

            // 检查是否有时间参数 t
            String t = params.get("t");
            if (t != null && progress <= 0) {
                try {
                    progress = (long) (Double.parseDouble(t) * 1000);
                } catch (NumberFormatException e) {
                    // 忽略
                }
            }

            TerminalContext.getInstance().enterVideoDetailPage(context, aid, bvid, null, -1);
            return true;
        }

        return false;
    }

    /**
     * 处理番剧链接
     */
    private static boolean handlePgc(Context context, Uri uri, String path) {
        String id = extractFirstDigit(path);
        if (id != null) {
            boolean isEp = path.contains("/ep/");
            // 跳转到番剧页面
            MsgUtil.showMsg("暂不支持跳转到番剧页面");
            return true;
        }
        return false;
    }

    /**
     * 处理用户空间链接
     */
    private static boolean handleSpace(Context context, Uri uri, String path) {
        String mid = extractFirstDigit(path);
        if (mid != null) {
            MsgUtil.showMsg("暂不支持跳转到用户空间");
            return true;
        }
        return false;
    }

    /**
     * 处理直播链接
     */
    private static boolean handleLive(Context context, Uri uri, String path) {
        String roomId = extractFirstDigit(path);
        if (roomId != null) {
            MsgUtil.showMsg("暂不支持跳转到直播间");
            return true;
        }
        return false;
    }

    /**
     * 处理番剧链接
     */
    private static boolean handleBangumi(Context context, Uri uri, String path) {
        String id = extractFirstDigit(path);
        if (id != null) {
            MsgUtil.showMsg("暂不支持跳转到番剧页面");
            return true;
        }
        return false;
    }

    /**
     * 处理动态/文章链接
     * bilibili://opus/detail/1210821975947083808
     * bilibili://following/detail/1210821975947083808
     */
    private static boolean handleOpus(Context context, Uri uri, String path) {
        String id = extractFirstDigit(path);
        if (id != null) {
            try {
                long opusId = Long.parseLong(id);
                TerminalContext.getInstance().enterOpusDetailPage(context, opusId);
                return true;
            } catch (NumberFormatException e) {
                Logu.e("UriRouter", "解析动态ID失败: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * 处理评论链接
     * bilibili://comment/detail/11/397170212/301835879217/?enterUri=bilibili://opus/detail/1210821975947083808
     */
    private static boolean handleComment(Context context, Uri uri, String path) {
        if (path.startsWith("/detail/") || path.startsWith("/msg_fold/")) {
            // 优先使用 enterUri 参数进行跳转
            String enterUri = uri.getQueryParameter("enterUri");
            if (enterUri != null && !enterUri.isEmpty()) {
                boolean handled = routeFromUrl(context, enterUri);
                if (handled) return true;
            }

            // 如果 enterUri 跳转失败，使用原有逻辑
            String[] segments = path.split("/");
            if (segments.length >= 4) {
                try {
                    int type = Integer.parseInt(segments[1]);
                    long oid = Long.parseLong(segments[2]);
                    long rootId = Long.parseLong(segments[3]);

                    String anchor = uri.getQueryParameter("anchor");
                    long seekReply = anchor != null ? Long.parseLong(anchor) : rootId;

                    // 根据 type 判断跳转目标
                    if (type == 17 || type == 11) {
                        // 动态评论
                        TerminalContext.getInstance().enterDynamicDetailPage(context, oid, 0, seekReply);
                    } else if (type == 12) {
                        // 专栏评论
                        TerminalContext.getInstance().enterArticleDetailPage(context, oid, seekReply);
                    } else {
                        // 视频评论
                        TerminalContext.getInstance().enterVideoDetailPage(context, oid, null, null, seekReply);
                    }
                    return true;
                } catch (NumberFormatException e) {
                    Logu.e("UriRouter", "解析评论参数失败: " + e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 提取路径中的第一个数字
     */
    private static String extractFirstDigit(String path) {
        Matcher matcher = DIGIT_PATTERN.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 提取 BV 号
     */
    private static String extractBvid(String path) {
        Matcher matcher = BV_PATTERN.matcher(path);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 解析查询参数
     */
    private static java.util.Map<String, String> parseQuery(String query) {
        java.util.Map<String, String> params = new java.util.HashMap<>();
        if (query == null || query.isEmpty()) return params;

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}
