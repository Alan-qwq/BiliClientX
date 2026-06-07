package com.Alan.BiliClientX.adapter.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Alan.BiliClientX.R;
import com.Alan.BiliClientX.model.Reply;

public class ReplyCardHolder extends RecyclerView.ViewHolder {
    final TextView content;
    final TextView tiptext;

    public ReplyCardHolder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.content);
        tiptext = itemView.findViewById(R.id.tip);
    }

    public void showReplyCard(Reply replyInfo) {
        content.setText(replyInfo.message);
    }
}
