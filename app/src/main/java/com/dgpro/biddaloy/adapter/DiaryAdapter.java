package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.Network.Model.DiaryDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/17/2018.
 */


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

private List<DiaryDataModel> messageList;

public DiaryAdapter(List<DiaryDataModel> messageList) {
        this.messageList = messageList;
        //this.mCallfromMessageList = mCallfromMessageList;
        }

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView noticeContent, noticeSubject, noticeTeacher,noticeDate;

    MyViewHolder(View view) {
        super(view);
        noticeContent = (TextView) view.findViewById(R.id.notice_content);
        noticeSubject = (TextView) view.findViewById(R.id.notice_subject);
        noticeTeacher = (TextView) view.findViewById(R.id.notice_teacher);
        noticeDate = (TextView) view.findViewById(R.id.notice_date);
    }
}

    @Override
    public DiaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_diary_listview, parent, false);
        return new DiaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiaryAdapter.MyViewHolder holder, final int position) {
        DiaryDataModel mModel = messageList.get(position);

        String diaryTeacher = "Teacher : "+mModel.getTeacher();
        String diarySubject = "Subject : "+mModel.getSubject();
        String diaryDate = "Date : "+mModel.getDay();

        holder.noticeContent.setText(mModel.getTopics());
        holder.noticeTeacher.setText(diaryTeacher);
        holder.noticeSubject.setText(diarySubject);
        holder.noticeDate.setText(diaryDate);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
