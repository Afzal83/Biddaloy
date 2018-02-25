package com.dgpro.biddaloy.fragment.MyStudents;

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

import com.dgpro.biddaloy.adapter.DiaryAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 1/19/2018.
 */

public class StudentDiaryFragment extends Fragment {

    View mView;
    RecyclerView diaryListView;
    DiaryAdapter diaryAdapter;

    BiddaloyApplication biddaloyApplication;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        diaryAdapter = new DiaryAdapter(biddaloyApplication.diaryList);
        Log.e("attendence fragment","onCrate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_diary,container,false);
        setDiaryListView();
        return mView;
    }

    void setDiaryListView(){
        diaryListView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_diary);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        diaryListView.setLayoutManager(mLayoutManager);
        diaryListView.setItemAnimator(new DefaultItemAnimator());

        diaryListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        diaryListView.setAdapter(diaryAdapter);
    }

}
