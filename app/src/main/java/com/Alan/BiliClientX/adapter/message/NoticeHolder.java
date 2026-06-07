package com.Alan.BiliClientX.adapter.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Alan.BiliClientX.BiliTerminal;
import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.activity.user.info.UserInfoActivity;
import com.Alan.BiliClientX.adapter.video.VideoCardHolder;
import com.Alan.BiliClientX.model.MessageCard;
import com.Alan.BiliClientX.model.Reply;
import com.Alan.BiliClientX.util.Logu;
import com.Alan.BiliClientX.util.UriRouter;
import com.Alan.BiliClientX.util.GlideUtil;
import com.Alan.BiliClientX.util.MsgUtil;
import com.Alan.BiliClientX.util.StringUtil;
import com.Alan.BiliClientX.util.TerminalContext;
import com.Alan.BiliClientX.util.ToolsUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;

public class NoticeHolder extends RecyclerView.ViewHolder {
    public final LinearLayout avaterList;
    public final TextView action;
    public final TextView pubdate;
    public final ConstraintLayout extraCard;
    public final View itemView;

    public NoticeHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        avaterList = itemView.findViewById(R.id.avatar_list);
        action = itemView.findViewById(R.id.action);
        pubdate = itemView.findViewById(R.id.pubdate);
        extraCard = itemView.findViewById(R.id.extraCard);
    }

    @SuppressLint("SetTextI18n")
    public void showMessage(MessageCard message, Context context) {
        // 显示头像列表
        avaterList.removeAllViews();
        if (message.user.isEmpty()) {
            avaterList.setVisibility(View.GONE);
        } else {
            avaterList.setVisibility(View.VISIBLE);
            for (int i = 0; i < message.user.size(); i++) {
                ImageView imageView = new ImageView(context);
                Glide.with(BiliTerminal.context)
                        .asDrawable()
                        .load(GlideUtil.url(message.user.get(i).avatar))
                        .transition(GlideUtil.getTransitionOptions())
                        .placeholder(R.mipmap.akari)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ToolsUtil.dp2px(32), ToolsUtil.dp2px(32)));
                imageView.setLeft(ToolsUtil.dp2px(3));
                int finalI = i;
                imageView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra("mid", message.user.get(finalI).mid);
                    context.startActivity(intent);
                });
                avaterList.addView(imageView);

                // 间隔View
                View spacer = new View(context);
                spacer.setLayoutParams(new ViewGroup.LayoutParams(ToolsUtil.dp2px(3), ToolsUtil.dp2px(32)));
                avaterList.addView(spacer);
            }
        }

        // 显示时间
        if (message.timeStamp != 0) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            pubdate.setText(sdf.format(message.timeStamp * 1000));
        } else {
            pubdate.setText(message.timeDesc);
        }

        // 显示内容
        action.setText(message.content);
        StringUtil.setCopy(action);

        // 显示视频卡片
        if (message.videoCard != null) {
            VideoCardHolder holder = new VideoCardHolder(View.inflate(context, R.layout.cell_dynamic_video, extraCard));
            holder.showVideoCard(message.videoCard, context);
            holder.itemView.findViewById(R.id.videoCardView).setOnClickListener(v -> navigateFromUri(context, message));
        }

        // 显示评论/动态卡片
        if (message.replyInfo != null || message.dynamicInfo != null) {
            Reply childReply = message.replyInfo != null ? message.replyInfo : message.dynamicInfo;
            ReplyCardHolder holder = new ReplyCardHolder(View.inflate(context, R.layout.cell_message_reply, extraCard));
            holder.showReplyCard(childReply);
            holder.itemView.findViewById(R.id.cardView).setOnClickListener(v -> navigateFromUri(context, message));
        }
    }

    /**
     * 使用 nativeUri 进行跳转（参考 PiliPlus）
     * nativeUri 格式示例：
     * - 视频评论：bilibili://video/116539542012119?page=0&comment_root_id=300374047217
     * - 动态评论：bilibili://comment/detail/11/397170212/301835879217/?enterUri=bilibili://opus/detail/1210821975947083808
     * - 弹幕：bilibili://video/116491844322987?dm_progress=13934&cid=37966187344&dmid=2118214093671862272
     * - 动态点赞：可能没有 nativeUri，使用 subjectId 跳转
     */
    private void navigateFromUri(Context context, MessageCard message) {
        // 优先使用 nativeUri 进行跳转
        String nativeUri = message.nativeUri;
        if (nativeUri != null && !nativeUri.isEmpty()) {
            boolean handled = UriRouter.routeFromUrl(context, nativeUri);
            if (handled) return;
        }

        // 如果 nativeUri 跳转失败，根据 itemType 使用回退逻辑
        if (message.replyInfo != null || message.dynamicInfo != null) {
            Reply childReply = message.replyInfo != null ? message.replyInfo : message.dynamicInfo;
            switch (message.itemType) {
                case "video":
                    if (childReply != null && childReply.ofBvid != null && !childReply.ofBvid.isEmpty()) {
                        TerminalContext.getInstance().enterVideoDetailPage(context, 0, childReply.ofBvid);
                        return;
                    }
                    break;
                case "dynamic":
                case "album":
                    if (message.subjectId > 0) {
                        TerminalContext.getInstance().enterDynamicDetailPage(context, message.subjectId);
                        return;
                    }
                    break;
                case "article":
                    if (message.subjectId > 0) {
                        TerminalContext.getInstance().enterArticleDetailPage(context, message.subjectId);
                        return;
                    }
                    break;
                case "danmu":
                    // 弹幕类型已经有 nativeUri 处理，这里不需要回退
                    break;
            }
        }

        // 最终回退：显示提示
        MsgUtil.showMsg("无法跳转到对应内容");
    }
}
