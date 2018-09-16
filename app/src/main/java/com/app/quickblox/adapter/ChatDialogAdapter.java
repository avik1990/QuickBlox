package com.app.quickblox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.app.quickblox.R;
import com.quickblox.chat.model.QBChatDialog;

import java.util.ArrayList;

public class ChatDialogAdapter extends RecyclerView.Adapter<ChatDialogAdapter.ViewHolder> {

    ArrayList<QBChatDialog> qbChatDialogs = new ArrayList<>();
    private Context context;

    public ChatDialogAdapter(Context context, ArrayList<QBChatDialog> qbChatDialogs) {
        this.context = context;
        this.qbChatDialogs = qbChatDialogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_chat_dialog, viewGroup, false);
        return new ChatDialogAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.tv_chatdialog_title.setText(qbChatDialogs.get(position).getName());

        if(!holder.tv_chatdialog_title.getText().toString().isEmpty()){
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int randomcolor = generator.getRandomColor();
            TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .round();

            TextDrawable drawable = builder.build(holder.tv_chatdialog_title.getText().toString().trim().substring(0, 1).toUpperCase(), randomcolor);
            holder.ivUserPhoto.setImageDrawable(drawable);
        }


    }

    @Override
    public int getItemCount() {
        return qbChatDialogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserPhoto;
        TextView tv_chatdialog_title, tv_chatdialog_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);
            tv_chatdialog_title = itemView.findViewById(R.id.tv_chatdialog_title);
            tv_chatdialog_msg = itemView.findViewById(R.id.tv_chatdialog_msg);



        }
    }
}
