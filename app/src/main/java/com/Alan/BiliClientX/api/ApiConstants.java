package com.Alan.BiliClientX.api;

/**
 * B站 API 端点常量
 * 迁移自 PiliPlus 项目，仅保留网页端 API
 */
public final class ApiConstants {

    // ==================== 基础域名 ====================
    public static final String BASE_URL = "https://www.bilibili.com";
    public static final String API_BASE_URL = "https://api.bilibili.com";
    public static final String T_URL = "https://api.vc.bilibili.com";
    public static final String APP_BASE_URL = "https://app.bilibili.com";
    public static final String LIVE_BASE_URL = "https://api.live.bilibili.com";
    public static final String PASS_BASE_URL = "https://passport.bilibili.com";
    public static final String MESSAGE_BASE_URL = "https://message.bilibili.com";
    public static final String DYNAMIC_SHARE_URL = "https://t.bilibili.com";
    public static final String SPACE_BASE_URL = "https://space.bilibili.com";
    public static final String ACCOUNT_BASE_URL = "https://account.bilibili.com";
    public static final String MALL_BASE_URL = "https://mall.bilibili.com";

    // ==================== 推荐视频 ====================
    // 网页端推荐
    public static final String RECOMMEND_LIST_WEB = "/x/web-interface/wbi/index/top/feed/rcmd";
    // 热门视频
    public static final String HOT_LIST = "/x/web-interface/popular";

    // ==================== 视频流 ====================
    // UGC 视频流
    public static final String UGC_URL = "/x/player/wbi/playurl";
    // 番剧视频流
    public static final String PGC_URL = "/pgc/player/web/v2/playurl";
    // PUGV 视频流
    public static final String PUGV_URL = "/pugv/player/web/playurl";
    // TV 视频流
    public static final String TV_PLAY_URL = "/x/tv/playurl";

    // ==================== 视频详情 ====================
    // 字幕/播放信息
    public static final String PLAY_INFO = "/x/player/wbi/v2";
    // 视频详情
    public static final String VIDEO_INTRO = "/x/web-interface/view";
    // 视频超详细信息
    public static final String VIDEO_DETAIL = "/x/web-interface/view/detail";
    // 视频关系（点赞/投币/收藏状态）
    public static final String VIDEO_RELATION = "/x/web-interface/archive/relation";
    // 相关视频
    public static final String RELATED_LIST = "/x/web-interface/archive/related";
    // AI 总结
    public static final String AI_CONCLUSION = "/x/web-interface/view/conclusion/get";
    // 视频标签
    public static final String VIDEO_TAGS = "/x/web-interface/view/detail/tag";
    // 视频截图
    public static final String VIDEO_SHOT = "/x/player/videoshot";

    // ==================== 视频操作 ====================
    // 点赞（网页端）
    public static final String LIKE_VIDEO_WEB = "/x/web-interface/archive/like";
    // 点赞（APP端，更稳定）
    public static final String LIKE_VIDEO_APP = APP_BASE_URL + "/x/v2/view/like";
    // 点踩（APP端，网页端不支持）
    public static final String DISLIKE_VIDEO = APP_BASE_URL + "/x/v2/view/dislike";
    // 投币（网页端）
    public static final String COIN_VIDEO_WEB = "/x/web-interface/coin/add";
    // 投币（APP端）
    public static final String COIN_VIDEO_APP = APP_BASE_URL + "/x/v2/view/coin/add";
    // 一键三连
    public static final String UGC_TRIPLE = "/x/web-interface/archive/like/triple";
    // PGC 一键三连
    public static final String PGC_TRIPLE = "/pgc/season/episode/like/triple";
    // 分享视频
    public static final String SHARE_VIDEO = "/x/web-interface/share/add";

    // ==================== 动态 ====================
    // 关注的UP动态
    public static final String FOLLOW_DYNAMIC = "/x/polymer/web-dynamic/v1/feed/all";
    // UP主列表
    public static final String DYN_UP_LIST = "/x/polymer/web-dynamic/v1/uplist";
    // 用户动态
    public static final String MEMBER_DYNAMIC = "/x/polymer/web-dynamic/v1/feed/space";
    // 动态详情
    public static final String DYNAMIC_DETAIL = "/x/polymer/web-dynamic/v1/detail";
    // 动态搜索
    public static final String DYN_SEARCH = "/x/polymer/web-dynamic/v1/feed/space/search";
    // 动态点赞
    public static final String THUMB_DYNAMIC = "/x/dynamic/feed/dyn/thumb";
    // 发布动态
    public static final String CREATE_DYNAMIC = "/x/dynamic/feed/create/dyn";
    // 发布文本动态
    public static final String CREATE_TEXT_DYNAMIC = "/dynamic_svr/v1/dynamic_svr/create";
    // 删除动态
    public static final String REMOVE_DYNAMIC = "/x/dynamic/feed/operate/remove";
    // 编辑动态
    public static final String EDIT_DYN = "/x/dynamic/feed/edit/dyn";
    // 置顶动态
    public static final String SET_TOP_DYN = "/x/dynamic/feed/space/set_top";
    // 取消置顶
    public static final String RM_TOP_DYN = "/x/dynamic/feed/space/rm_top";
    // 动态图片详情
    public static final String DYN_PIC = "/x/polymer/web-dynamic/v1/detail/pic";
    // 动态提及搜索
    public static final String DYN_MENTION = "/x/polymer/web-dynamic/v1/mention/search";
    // 动态话题推荐
    public static final String DYN_TOPIC_RCMD = "/x/topic/web/dynamic/rcmd";
    // 动态话题详情
    public static final String TOPIC_TOP = APP_BASE_URL + "/x/topic/web/details/top";
    // 动态话题Feed
    public static final String TOPIC_FEED = "/x/polymer/web-dynamic/v1/feed/topic";
    // 动态预约
    public static final String DYN_RESERVE = "/x/dynamic/feed/reserve/click";
    // 未读动态数
    public static final String UNREAD_DYNAMIC = "/x/web-interface/dynamic/entrance";

    // ==================== 评论 ====================
    // 评论列表
    public static final String REPLY_LIST = "/x/v2/reply";
    // 楼中楼
    public static final String REPLY_REPLY_LIST = "/x/v2/reply/reply";
    // 评论点赞
    public static final String LIKE_REPLY = "/x/v2/reply/action";
    // 评论点踩
    public static final String HATE_REPLY = "/x/v2/reply/hate";
    // 发表评论
    public static final String REPLY_ADD = "/x/v2/reply/add";
    // 删除评论
    public static final String REPLY_DEL = "/x/v2/reply/del";
    // 置顶评论
    public static final String REPLY_TOP = "/x/v2/reply/top";

    // ==================== 用户信息 ====================
    // 当前用户信息
    public static final String USER_INFO = "/x/web-interface/nav";
    // 当前用户状态
    public static final String USER_STAT_OWNER = "/x/web-interface/nav/stat";
    // 用户详细信息
    public static final String MEMBER_INFO = "/x/space/wbi/acc/info";
    // 用户名片信息
    public static final String MEMBER_CARD_INFO = "/x/web-interface/card";
    // 用户投稿
    public static final String SEARCH_ARCHIVE = "/x/space/wbi/arc/search";
    // 用户获赞/播放数
    public static final String MEMBER_VIEW = "/x/space/upstat";

    // ==================== 用户关系 ====================
    // 查询用户关系
    public static final String RELATION = "/x/relation";
    public static final String RELATIONS = "/x/relation/relations";
    // 操作用户关系
    public static final String RELATION_MOD = "/x/relation/modify";
    // 关注列表
    public static final String FOLLOWINGS = "/x/relation/followings";
    // 粉丝列表
    public static final String FANS = "/x/relation/fans";
    // 关注分组
    public static final String FOLLOW_UP_TAG = "/x/relation/tags";
    // 设置分组
    public static final String ADD_USERS = "/x/relation/tags/addUsers";
    // 获取指定分组下的UP
    public static final String FOLLOW_UP_GROUP = "/x/relation/tag";
    // 创建分组
    public static final String CREATE_FOLLOW_TAG = "/x/relation/tag/create";
    // 更新分组
    public static final String UPDATE_FOLLOW_TAG = "/x/relation/tag/update";
    // 删除分组
    public static final String DEL_FOLLOW_TAG = "/x/relation/tag/del";
    // 黑名单
    public static final String BLACK_LIST = "/x/relation/blacks";

    // ==================== 收藏夹 ====================
    // 收藏夹列表
    public static final String FAV_RESOURCE_LIST = "/x/v3/fav/resource/list";
    // 收藏视频
    public static final String FAV_VIDEO = "/x/v3/fav/resource/batch-deal";
    // 取消收藏
    public static final String UNFAV_ALL = "/x/v3/fav/resource/unfav-all";
    // 复制收藏
    public static final String COPY_FAV = "/x/v3/fav/resource/copy";
    // 移动收藏
    public static final String MOVE_FAV = "/x/v3/fav/resource/move";
    // 清空收藏
    public static final String CLEAN_FAV = "/x/v3/fav/resource/clean";
    // 排序收藏
    public static final String SORT_FAV = "/x/v3/fav/resource/sort";
    // 收藏夹排序
    public static final String SORT_FAV_FOLDER = "/x/v3/fav/folder/sort";
    // 获取收藏夹列表
    public static final String FAV_FOLDER = "/x/v3/fav/folder/created/list-all";
    public static final String USER_FAV_FOLDER = "/x/v3/fav/folder/created/list";
    // 收藏夹信息
    public static final String FAV_FOLDER_INFO = "/x/v3/fav/folder/info";
    // 创建收藏夹
    public static final String ADD_FOLDER = "/x/v3/fav/folder/add";
    // 编辑收藏夹
    public static final String EDIT_FOLDER = "/x/v3/fav/folder/edit";
    // 删除收藏夹
    public static final String DELETE_FOLDER = "/x/v3/fav/folder/del";
    // 收藏合集
    public static final String FAV_SEASON = "/x/v3/fav/season/fav";
    // 取消收藏合集
    public static final String UNFAV_SEASON = "/x/v3/fav/season/unfav";

    // ==================== 历史记录 ====================
    // 历史记录
    public static final String HISTORY_LIST = "/x/web-interface/history/cursor";
    // 暂停历史记录
    public static final String PAUSE_HISTORY = "/x/v2/history/shadow/set";
    // 历史记录状态
    public static final String HISTORY_STATUS = "/x/v2/history/shadow?jsonp=jsonp";
    // 清空历史记录
    public static final String CLEAR_HISTORY = "/x/v2/history/clear";
    // 删除历史记录
    public static final String DEL_HISTORY = "/x/v2/history/delete";
    // 搜索历史记录
    public static final String SEARCH_HISTORY = "/x/web-interface/history/search";
    // 记录播放进度
    public static final String HEART_BEAT = "/x/click-interface/web/heartbeat";
    // 上报历史
    public static final String HISTORY_REPORT = "/x/v2/history/report";

    // ==================== 稍后再看 ====================
    // 获取稍后再看
    public static final String SEE_YOU_LATER = "/x/v2/history/toview/web";
    // 添加稍后再看
    public static final String TO_VIEW_LATER = "/x/v2/history/toview/add";
    // 删除稍后再看
    public static final String TO_VIEW_DEL = "/x/v2/history/toview/v2/dels";
    // 清空稍后再看
    public static final String TO_VIEW_CLEAR = "/x/v2/history/toview/clear";
    // 复制到稍后再看
    public static final String COPY_TO_VIEW = "/x/v2/history/toview/copy";
    // 移动到稍后再看
    public static final String MOVE_TO_VIEW = "/x/v2/history/toview/move";

    // ==================== 搜索 ====================
    // 热搜
    public static final String HOT_SEARCH_LIST = "https://s.search.bilibili.com/main/hotword";
    // 默认搜索词
    public static final String SEARCH_DEFAULT = "/x/web-interface/wbi/search/default";
    // 搜索建议
    public static final String SEARCH_SUGGEST = "https://s.search.bilibili.com/main/suggest";
    // 分类搜索
    public static final String SEARCH_BY_TYPE = "/x/web-interface/wbi/search/type";
    // 综合搜索
    public static final String SEARCH_ALL = "/x/web-interface/wbi/search/all/v2";

    // ==================== 消息 ====================
    // 未读私信数
    public static final String MSG_UNREAD = T_URL + "/session_svr/v1/session_svr/single_unread";
    // 消息中心未读
    public static final String MSG_FEED_UNREAD = "/x/msgfeed/unread";
    // 回复我的
    public static final String MSG_FEED_REPLY = "/x/msgfeed/reply";
    // @我的
    public static final String MSG_FEED_AT = "/x/msgfeed/at";
    // 收到的赞
    public static final String MSG_FEED_LIKE = "/x/msgfeed/like";
    // 系统消息
    public static final String MSG_SYS_NOTIFY = MESSAGE_BASE_URL + "/x/sys-msg/query_notify_list";
    // 系统消息已读
    public static final String MSG_SYS_UPDATE_CURSOR = MESSAGE_BASE_URL + "/x/sys-msg/update_cursor";
    // 私信会话列表
    public static final String SESSION_LIST = T_URL + "/session_svr/v1/session_svr/get_sessions";
    // 私信消息
    public static final String SESSION_MSG = T_URL + "/svr_sync/v1/svr_sync/fetch_session_msgs";
    // 标记已读
    public static final String ACK_SESSION_MSG = T_URL + "/session_svr/v1/session_svr/update_ack";
    // 发送私信
    public static final String SEND_MSG = T_URL + "/web_im/v1/web_im/send_msg";

    // ==================== 直播 ====================
    // 直播列表
    public static final String LIVE_LIST = LIVE_BASE_URL + "/xlive/web-interface/v1/second/getUserRecommend";
    // 直播间详情
    public static final String LIVE_ROOM_INFO = LIVE_BASE_URL + "/xlive/web-room/v2/index/getRoomPlayInfo";
    // 直播间详情 H5
    public static final String LIVE_ROOM_INFO_H5 = LIVE_BASE_URL + "/xlive/web-room/v1/index/getH5InfoByRoom";
    // 直播间弹幕预获取
    public static final String LIVE_ROOM_DM_PREFETCH = LIVE_BASE_URL + "/xlive/web-room/v1/dM/gethistory";
    // 直播间弹幕密钥
    public static final String LIVE_ROOM_DM_TOKEN = LIVE_BASE_URL + "/xlive/web-room/v1/index/getDanmuInfo";
    // 发送直播弹幕
    public static final String SEND_LIVE_MSG = LIVE_BASE_URL + "/msg/send";

    // ==================== 弹幕 ====================
    // 发送弹幕
    public static final String SHOOT_DANMAKU = "/x/v2/dm/post";
    // 弹幕屏蔽查询
    public static final String DANMAKU_FILTER = "/x/dm/filter/user";
    // 添加屏蔽词
    public static final String DANMAKU_FILTER_ADD = "/x/dm/filter/user/add";
    // 删除屏蔽词
    public static final String DANMAKU_FILTER_DEL = "/x/dm/filter/user/del";
    // 弹幕点赞
    public static final String DANMAKU_LIKE = "/x/v2/dm/thumbup/add";

    // ==================== 番剧 ====================
    // 番剧详情
    public static final String PGC_INFO = "/pgc/view/web/season";
    // PUGV 详情
    public static final String PUGV_INFO = "/pugv/view/web/season";
    // 剧集信息
    public static final String EPISODE_INFO = "/pgc/season/episode/web/info";
    // 追番
    public static final String PGC_ADD = "/pgc/web/follow/add";
    // 取消追番
    public static final String PGC_DEL = "/pgc/web/follow/del";
    // 追番状态更新
    public static final String PGC_UPDATE = "/pgc/web/follow/status/update";
    // 我的追番
    public static final String FAV_PGC = "/x/space/bangumi/follow/list";
    // 番剧时间线
    public static final String PGC_TIMELINE = "/pgc/web/timeline";

    // ==================== 排行榜 ====================
    public static final String RANK_API = "/x/web-interface/ranking/v2";
    public static final String PGC_RANK = "/pgc/web/rank/list";
    public static final String PGC_SEASON_RANK = "/pgc/season/rank/web/list";

    // ==================== 表情 ====================
    public static final String MY_EMOTE = "/x/emote/user/panel/web";

    // ==================== 文章 ====================
    // 文章信息
    public static final String ARTICLE_INFO = "/x/article/viewinfo";
    // 文章详情
    public static final String ARTICLE_VIEW = "/x/article/view";
    // 文章列表
    public static final String ARTICLE_LIST = "/x/article/list/web/articles";
    // Opus 详情
    public static final String OPUS_DETAIL = "/x/polymer/web-dynamic/v1/opus/detail";

    // ==================== 投票 ====================
    public static final String VOTE_INFO = "/x/vote/vote_info";
    public static final String DO_VOTE = "/x/vote/do_vote";

    // ==================== 其他 ====================
    // 在线人数
    public static final String ONLINE_TOTAL = "/x/player/online/total";
    // 查询分P列表
    public static final String AB2C = "/x/player/pagelist";
    // 空间收藏夹
    public static final String SPACE_FAV = "/x/v3/fav/folder/space";
    // 合集系列列表
    public static final String SEASON_SERIES = "/x/polymer/web-space/seasons_series_list";
    // 合集归档
    public static final String SEASON_ARCHIVES = "/x/polymer/web-space/seasons_archives_list";
    // 系列归档
    public static final String SERIES_ARCHIVES = "/x/series/archives";
    // 登录日志
    public static final String LOGIN_LOG = "/x/member/web/login/log";
    // 经验日志
    public static final String EXP_LOG = "/x/member/web/exp/log";
    // 硬币日志
    public static final String COIN_LOG = "/x/member/web/coin/log";
    // 获取硬币
    public static final String GET_COIN = ACCOUNT_BASE_URL + "/site/getCoin";

    private ApiConstants() {
        // 防止实例化
    }
}
