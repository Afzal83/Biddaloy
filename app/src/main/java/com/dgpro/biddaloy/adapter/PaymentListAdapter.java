package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.PaymentDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/28/2018.
 */

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder> {

    private List<PaymentDataModel> paymentList;
    private RecycleViewItemClickListener mCallfromMessageList ;

    public PaymentListAdapter(List<PaymentDataModel> paymentList ,RecycleViewItemClickListener mCallfromMessageList) {
        this.paymentList = paymentList;
        this.mCallfromMessageList = mCallfromMessageList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView paidAmount, date, dueAmount;

        MyViewHolder(View view) {
            super(view);
            paidAmount = (TextView) view.findViewById(R.id.paid_amount);
            date = (TextView) view.findViewById(R.id.payment_date);
            dueAmount = (TextView) view.findViewById(R.id.due_amount);
        }
    }

    @Override
    public PaymentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_payment_list, parent, false);
        return new PaymentListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentListAdapter.MyViewHolder holder, final int position) {
        PaymentDataModel mModel = paymentList.get(position);

        String due = mModel.getDue_amount()+ " Taka";
        String payment = mModel.getPaid_amount() + " Taka";

        holder.date.setText(mModel.getDate());
        holder.paidAmount.setText(due);
        holder.dueAmount.setText(payment);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallfromMessageList.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
