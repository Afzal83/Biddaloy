package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/16/2018.
 */

public class OutboxAdapter extends RecyclerView.Adapter<OutboxAdapter.MyViewHolder> {

    private List<OutboxDataModel> messageList;
    private CallFromMessageList mCallFromMessageList ;

    public OutboxAdapter(List<OutboxDataModel> messageList,CallFromMessageList mCallFromMessageList) {
        this.messageList = messageList;
        this.mCallFromMessageList = mCallFromMessageList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView messageSender, messegeSendTime, messageContent;

        MyViewHolder(View view) {
            super(view);
            senderImage = (ImageView) view.findViewById(R.id.message_sender_image);
            messageSender = (TextView) view.findViewById(R.id.messege_sender);
            messegeSendTime = (TextView) view.findViewById(R.id.messege_send_time);
            messageContent = (TextView) view.findViewById(R.id.messege_content);
        }
    }

    @Override
    public OutboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_outbox_message, parent, false);
        return new OutboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OutboxAdapter.MyViewHolder holder,final int position) {
        OutboxDataModel mModel = messageList.get(position);
        holder.messageSender.setText(mModel.getReceiver_name());
        holder.messegeSendTime.setText(mModel.getTime());
        holder.messageContent.setText(mModel.getSubject());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallFromMessageList.onMessageListClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null == messageList) return 0;
        return messageList.size();
    }
}
