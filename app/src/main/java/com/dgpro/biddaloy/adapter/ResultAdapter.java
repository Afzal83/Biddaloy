package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.ResultDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/23/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<ResultDataModel> resultList;
    RecycleViewItemClickListener clickListener;

    public ResultAdapter(List<ResultDataModel> resultList,RecycleViewItemClickListener clickListener) {
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
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_result_list, parent, false);
        return new ResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.MyViewHolder holder, final int position) {
        ResultDataModel mModel = resultList.get(position);
        holder.resultOfExam.setText(mModel.getExam_name());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == resultList){return 0 ;}
        return resultList.size();
    }
}