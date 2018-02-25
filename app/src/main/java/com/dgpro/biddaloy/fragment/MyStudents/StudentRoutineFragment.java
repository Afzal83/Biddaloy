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
import com.dgpro.biddaloy.adapter.RoutineAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 1/23/2018.
 */

public class StudentRoutineFragment extends Fragment implements RecycleViewItemClickListener {

    BiddaloyApplication biddaloyApplication;
    RoutineAdapter routineAdapter;
    RecyclerView routineRecycleView;

    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        routineAdapter = new RoutineAdapter(biddaloyApplication.routineList,this);
        Log.e("Routine fragment","onCrate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_routine,container,false);
        setRoutineListView();
        //bindView();
        return mView;
    }
    void setRoutineListView(){
        routineRecycleView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_routine);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        routineRecycleView.setLayoutManager(mLayoutManager);
        routineRecycleView.setItemAnimator(new DefaultItemAnimator());

        routineRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        routineRecycleView.setAdapter(routineAdapter);
    }

    @Override
    public void onItemClick(int position) {
        String resultUrl =biddaloyApplication.baseUrl + biddaloyApplication.routineList.get(position).getUrl();
        Log.e("Routine url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
