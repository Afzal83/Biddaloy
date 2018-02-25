package com.dgpro.biddaloy.fragment.MyStudents;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dgpro.biddaloy.activity.CreatePayment;
import com.dgpro.biddaloy.activity.WebViewActivity;
import com.dgpro.biddaloy.adapter.PaymentListAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.PaymentDataModel;
import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 1/25/2018.
 */

public class StudentPaymentStatusFragment extends Fragment implements RecycleViewItemClickListener {

    BiddaloyApplication biddaloyApplication;

    PaymentListAdapter paymentListAdapter;
    RecyclerView paymentListView;


    int selectedRowPosition = 0 ;

    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        paymentListAdapter = new PaymentListAdapter(biddaloyApplication.paymentList,this);
        Log.e("payment list fragment","onCrate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_payment,container,false);

        String due = biddaloyApplication.studentDue + " Taka";
        ((TextView)mView.findViewById(R.id.total_due)).setText(due);
        ((Button)mView.findViewById(R.id.create_payment)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(),CreatePayment.class));
                //showPaymentActionDialog();
                startActivity(new Intent(getActivity(),CreatePayment.class));
            }
        });
        setResultListView();
        return mView;
    }
    void setResultListView(){

        paymentListView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_payment);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        paymentListView.setLayoutManager(mLayoutManager);
        paymentListView.setItemAnimator(new DefaultItemAnimator());

        paymentListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        paymentListView.setAdapter(paymentListAdapter);
    }

    @Override
    public void onItemClick(int position) {
        gotoWebView(position);
        //selectedRowPosition = position;
        //showPaymentActionDialog();
    }
    void showPaymentActionDialog() {
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        dialogView.setMinimumWidth((int)(displayRectangle.width() * 0.6f));
        //dialogView.setMinimumHeight((int)(displayRectangle.height() * 0.9f));

        dialogBuilder.setView(dialogView);
        ((TextView)dialogView.findViewById(R.id.create_payment)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                startActivity(new Intent(getActivity(),CreatePayment.class));
            }
        });
        ((TextView)dialogView.findViewById(R.id.view_payment)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoWebView(selectedRowPosition);
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    void gotoWebView(int position){

        String resultUrl =biddaloyApplication.baseUrl + biddaloyApplication.paymentList.get(position).getUrl();
        Log.e("Result url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
