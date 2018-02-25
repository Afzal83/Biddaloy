package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.Network.Model.NoticeDataModel;
import com.dgpro.biddaloy.R;
import com.github.pavlospt.CircleView;


import java.util.List;

/**
 * Created by Babu on 12/28/2017.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private List<NoticeDataModel> noticeList;

    public NoticeAdapter(List<NoticeDataModel> noticeList) {
        this.noticeList = noticeList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         TextView headerOfNotice,noticeDate;
         MyViewHolder(View view) {
            super(view);
            noticeDate = (TextView)view.findViewById(R.id.notice_date);
            headerOfNotice = (TextView) view.findViewById(R.id.header_noitce);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_list_notice, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NoticeDataModel notice = noticeList.get(position);
        //String[] output = notice.getDate().split("\\s+");
        //String mAndY = output[1]+" "+output[2];
        holder.noticeDate.setText(notice.getDate());
        holder.headerOfNotice.setText(notice.getNotice());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
}