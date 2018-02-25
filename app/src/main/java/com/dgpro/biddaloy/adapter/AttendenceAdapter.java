package com.dgpro.biddaloy.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.Network.Model.AttendenceDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/2/2018.
 */

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder> {

    private List<AttendenceDataModel> attendenceList;

    public AttendenceAdapter(List<AttendenceDataModel> attendenceList) {
        this.attendenceList = attendenceList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, inTime, outTime , attendence;

        MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.attendence_date);
            inTime = (TextView) view.findViewById(R.id.attendence_intime);
            outTime = (TextView) view.findViewById(R.id.attendence_out_time);
            attendence = (TextView) view.findViewById(R.id.attendece_status);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_attendence_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AttendenceDataModel mModel = attendenceList.get(position);
        holder.date.setText(mModel.getDate());
        holder.inTime.setText(mModel.getIntime());
        holder.outTime.setText(mModel.getOuttime());
        holder.attendence.setText(mModel.getComment());

        if(mModel.getComment().contains("bse")){
            holder.attendence.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return attendenceList.size();
    }
}