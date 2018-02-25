package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.RoutineDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/25/2018.
 */

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.MyViewHolder> {

    private List<RoutineDataModel> resultList;
    RecycleViewItemClickListener clickListener;

    public RoutineAdapter(List<RoutineDataModel> resultList,RecycleViewItemClickListener clickListener) {
        this.resultList = resultList;
        this.clickListener = clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView resultOfExam;

        MyViewHolder(View view) {
            super(view);
            resultOfExam = (TextView) view.findViewById(R.id.result_of_exam);
        }
    }

    @Override
    public RoutineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_result_list, parent, false);
        return new RoutineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoutineAdapter.MyViewHolder holder, final int position) {
        RoutineDataModel mModel = resultList.get(position);
        holder.resultOfExam.setText(mModel.getRoutine_name());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}