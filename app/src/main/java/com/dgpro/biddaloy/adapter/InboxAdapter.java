package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Babu on 1/16/2018.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private List<InboxDataModel> messageList;
    private CallFromMessageList mCallfromMessageList ;
    Context mContext;

    public InboxAdapter(Context mContext,List<InboxDataModel> messageList, CallFromMessageList mCallfromMessageList) {
        this.messageList = messageList;
        this.mCallfromMessageList = mCallfromMessageList;
        this.mContext = mContext;
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
    public InboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_message_list, parent, false);
        return new InboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InboxAdapter.MyViewHolder holder, final int position) {
        InboxDataModel mModel = messageList.get(position);
        holder.messageSender.setText(mModel.getSender_name());
        holder.messegeSendTime.setText(mModel.getTime());
        holder.messageContent.setText(mModel.getSubject());

        BiddaloyApplication biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());

        String userImageUrl = biddaloyApplication.baseUrl+ mModel.getSender_image();

        Log.e("senderImage",""+userImageUrl);

        Picasso.with(mContext)
                .load(userImageUrl)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(holder.senderImage);


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallfromMessageList.onMessageListClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
