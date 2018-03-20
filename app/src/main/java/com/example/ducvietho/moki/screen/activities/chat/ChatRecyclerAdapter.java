package com.example.ducvietho.moki.screen.activities.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Message;
import com.example.ducvietho.moki.utils.UserSession;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ducvietho on 11/29/2017.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatItemViewHolder> {
    private List<Message> mChats;
    private Context mContext;
    private UserSession session;

    public ChatRecyclerAdapter(List<Message> chats, Context context) {
        mChats = chats;
        this.mContext = context;
        session = new UserSession(mContext);
    }


    @Override
    public ChatItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = 0;
        switch (viewType) {
            case 0:
                layoutRes = R.layout.chat_left_item;
                break;
            case 1:
                layoutRes = R.layout.chat_right_item;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ChatItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatItemViewHolder holder, int position) {
        holder.tvMessage.setText(mChats.get(position).getMessage());
        if (mChats.get(position).getSender().getAvatar().equals("-1")) {
            holder.imgAvatar.setImageResource(R.drawable.icon_no_avatar);
        } else {
            Picasso.with(mContext).load(mChats.get(position).getSender().getAvatar()).into(holder.imgAvatar);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mChats.get(position).getSender().getId() == session.getUserDetail().getId()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mChats.isEmpty() ? 0 : mChats.size();
    }

    class ChatItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tv_message)
        TextView tvMessage;

        public ChatItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
