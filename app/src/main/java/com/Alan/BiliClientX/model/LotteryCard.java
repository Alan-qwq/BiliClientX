package com.Alan.BiliClientX.model;

import java.io.Serializable;

/**
 * 互动抽奖卡片数据模型
 * 对应 Bilibili 动态中的 ADDITIONAL_TYPE_UPOWER_LOTTERY 类型
 */
public class LotteryCard implements Serializable {
    public String title;        // 标题
    public String hint;         // 提示文字
    public String desc;         // 描述（奖品信息）
    public String descJumpUrl;  // 描述跳转链接
    public String jumpUrl;      // 卡片整体跳转链接
    public String buttonText;   // 按钮文字
    public String buttonJumpUrl; // 按钮跳转链接
}
