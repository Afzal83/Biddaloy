package com.dgpro.biddaloy.fragment;

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

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.LibraryModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.WebViewActivity;
import com.dgpro.biddaloy.adapter.LibraryAdapter;

import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.serviceapi.LibraryApi;

import java.util.List;

/**
 * Created by Babu on 3/28/2018.
 */

public class LibraryFragment extends Fragment implements RecycleViewItemClickListener{

    LibraryApi libraryApi;
    BiddaloyApplication biddaloyApplication;

    List<LibraryModel> bookList;

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_library,container,false);

        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        libraryApi = new LibraryApi(getActivity());

        downLoatLibraryData();
        return mView;
    }
    void downLoatLibraryData(){

        libraryApi.downLoadLibraryData(new LibraryApi.Callback<List<LibraryModel>>() {
            @Override
            public void onSuccess(List<LibraryModel> libraryModels) {
                bookList = libraryModels;
                setBookList();
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }

    void setBookList(){
        LibraryAdapter mAdapter = new LibraryAdapter(getActivity(),bookList,this);
        RecyclerView libraryListView = (RecyclerView)mView. findViewById(R.id.library_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        libraryListView.setLayoutManager(mLayoutManager);
        libraryListView.setItemAnimator(new DefaultItemAnimator());

        libraryListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        libraryListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        String resultUrl =biddaloyApplication.baseUrl+bookList.get(position).getUrl();
        Log.e("blog url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
