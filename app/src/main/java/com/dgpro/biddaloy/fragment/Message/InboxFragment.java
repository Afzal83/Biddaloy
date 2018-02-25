package com.dgpro.biddaloy.fragment.Message;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.activity.ComposeMessage;
import com.dgpro.biddaloy.adapter.InboxAdapter;
import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.activity.MailDetailsActivity;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.constants.EmailConstants;
import com.dgpro.biddaloy.serviceapi.MessageApi;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by Babu on 1/16/2018.
 */

public class InboxFragment extends Fragment implements CallFromMessageList{
    MessageApi messageApi;

    View mView ;
    RecyclerView recyclerViewFormessage;
    InboxAdapter mAdapter;
    List<InboxDataModel> mList;

    BiddaloyApplication biddaloyApplication;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_inbox,container,false);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        messageApi = new MessageApi(getActivity());

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab_compose_mail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ComposeMessage.class));
            }
        });

        downLoadInboxMail();
        return mView;
    }

    void downLoadInboxMail(){

        final AlertDialog dialog = new SpotsDialog(getActivity());
        dialog.show();
        messageApi.getInboxMailFromNetwork(new MessageApi.Callback<List<InboxDataModel>>() {
            @Override
            public void onSuccess(List<InboxDataModel> inboxDataModels) {
                dialog.dismiss();
                Log.e("success","got inbox data");
                mList = new ArrayList<>();
                mList = inboxDataModels;
                setInboxListView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                Log.e("error","did not got inbox data");
            }
        });
    }
    void setInboxListView(){

        recyclerViewFormessage = (RecyclerView)mView. findViewById(R.id.recycler_view_for_inbox);
        mAdapter = new InboxAdapter(getActivity(),mList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFormessage.setLayoutManager(mLayoutManager);
        recyclerViewFormessage.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFormessage.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewFormessage.setAdapter(mAdapter);
    }

    @Override
    public void onMessageListClick(int messageId) {

       // Log.e("inbox fragment","call from message list");
        Bundle bundle = new Bundle();
        bundle.putParcelable(EmailConstants.MESSAGE_CATAGORY_INBOX,Parcels.wrap(mList.get(messageId)));
        Intent intent = new Intent(getActivity(), MailDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
