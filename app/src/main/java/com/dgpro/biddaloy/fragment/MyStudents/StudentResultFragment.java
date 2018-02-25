package com.dgpro.biddaloy.fragment.MyStudents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgpro.biddaloy.activity.WebViewActivity;
import com.dgpro.biddaloy.adapter.ResultAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 1/19/2018.
 */

public class StudentResultFragment extends Fragment implements RecycleViewItemClickListener {

    BiddaloyApplication biddaloyApplication;
    ResultAdapter resultAdapter;
    RecyclerView resultListView;

    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        resultAdapter = new ResultAdapter(biddaloyApplication.resultList,this);
        Log.e("reasult fragment","onCrate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_result,container,false);
        setResultListView();
        return mView;
    }
    void setResultListView(){

        resultListView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_result);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        resultListView.setLayoutManager(mLayoutManager);
        resultListView.setItemAnimator(new DefaultItemAnimator());

        resultListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        resultListView.setAdapter(resultAdapter);
    }

    @Override
    public void onItemClick(int position) {
        String resultUrl = biddaloyApplication.baseUrl + biddaloyApplication.resultList.get(position).getUrl();
        Log.e("Result url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
