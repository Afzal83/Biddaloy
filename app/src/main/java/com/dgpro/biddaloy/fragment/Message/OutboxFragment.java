package com.dgpro.biddaloy.fragment.Message;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.activity.ComposeMessage;
import com.dgpro.biddaloy.adapter.OutboxAdapter;
import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.activity.MailDetailsActivity;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.Network.Model.OutboxModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.constants.EmailConstants;
import com.dgpro.biddaloy.serviceapi.MessageApi;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 1/16/2018.
 */

public class OutboxFragment extends Fragment implements CallFromMessageList,SwipeRefreshLayout.OnRefreshListener{

    MessageApi messageApi;

    View mView ;
    RecyclerView recyclerViewFormessage;
    OutboxAdapter mAdapter;
    List<OutboxDataModel> mList;

    SwipeRefreshLayout swipeLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_outbox,container,false);
        messageApi = new MessageApi(getActivity());

        swipeLayout = (SwipeRefreshLayout)mView.findViewById(R.id.outbox_swipe_container);
        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);


        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab_compose_mail_outbox);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ComposeMessage.class));
            }
        });

        downLoadOutboxData();
        return mView;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                downLoadOutboxData();
            }
        }, 2000);
    }

    void downLoadOutboxData(){

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        messageApi.getOutboxMailFromNetwork(new MessageApi.Callback<List<OutboxDataModel>>() {
            @Override
            public void onSuccess(List<OutboxDataModel> outboxDataModels) {
                dialog.dismiss();
                mList = new ArrayList<>();
                mList = outboxDataModels;
                setOutBoxListView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
            }
        });
    }

    void setOutBoxListView(){
        Log.e("creating","list");
        recyclerViewFormessage = (RecyclerView)mView. findViewById(R.id.recycler_view_for_outbox);
        mAdapter = new OutboxAdapter(mList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFormessage.setLayoutManager(mLayoutManager);
        recyclerViewFormessage.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFormessage.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewFormessage.setAdapter(mAdapter);
    }

    @Override
    public void onMessageListClick(int pos) {
        Log.e("0nbox fragment##","pos:"+pos);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EmailConstants.MESSAGE_CATAGORY_OUTBOX, Parcels.wrap(mList.get(pos)));
        Intent intent = new Intent(getActivity(), MailDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
