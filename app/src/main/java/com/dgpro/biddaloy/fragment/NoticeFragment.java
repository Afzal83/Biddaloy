package com.dgpro.biddaloy.fragment;

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

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.adapter.NoticeAdapter;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.NoticeDataModel;
import com.dgpro.biddaloy.Network.Model.NoticeModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.constants.NetWorkErrorConstant;
import com.dgpro.biddaloy.serviceapi.StudentApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 12/27/2017.
 */

public class NoticeFragment extends BaseFragment {

    BiddaloyApplication biddaloyApplication;
    StudentApi studentApi;

    View mView ;
    RecyclerView recyclerView;
    NoticeAdapter mAdapter;
    List<NoticeDataModel> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notice,container,false);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        studentApi = new StudentApi(getActivity());

        downLoadNoticeData();
        return mView;
    }
    void downLoadNoticeData(){

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        studentApi.downLoadNoticeData(new StudentApi.Callback<List<NoticeDataModel>>() {
            @Override
            public void onSuccess(List<NoticeDataModel> noticeDataModels) {
                dialog.dismiss();
                mList = new ArrayList<>();
                mList = noticeDataModels;
                setAttendenceListView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
            }
        });
    }
    void setAttendenceListView(){

        recyclerView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_notice);
        mAdapter = new NoticeAdapter(mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

    }
}
