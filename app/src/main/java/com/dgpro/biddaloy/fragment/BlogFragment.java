package com.dgpro.biddaloy.fragment;

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
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Network.Model.BlogDataModel;
import com.dgpro.biddaloy.activity.ComposeBlogActivity;
import com.dgpro.biddaloy.activity.WebViewActivity;
import com.dgpro.biddaloy.adapter.BlogAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.BlogListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.dialog.TransientDialog;
import com.dgpro.biddaloy.serviceapi.BlogApi;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogFragment extends  Fragment implements RecycleViewItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    BlogApi blogApi;
    TransientDialog tDialog ;

    BiddaloyApplication biddaloyApplication;
    BlogAdapter blogAdapter;
    RecyclerView blogListView;

    String blogUrl = "";

    View mView;

    SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        blogApi = new BlogApi(getActivity());
        tDialog = new TransientDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_blog,container,false);

        swipeLayout = (SwipeRefreshLayout)mView.findViewById(R.id.blog_swipe_container);
        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);


        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab_create_blog);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                startActivity(new Intent(getActivity(), ComposeBlogActivity.class));
            }
        });

        getBlogData();
        return mView;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                getBlogData();
            }
        }, 2000);
    }
    void getBlogData(){

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        blogApi.downLoadBlogData(new BlogApi.Callback<List<BlogDataModel>>() {
            @Override
            public void onSuccess(List<BlogDataModel> blogDataModels) {
                dialog.dismiss();
                biddaloyApplication.blogList = blogDataModels;
                setBlogListView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
            }
        });
    }
    void setBlogListView(){
        blogAdapter = new BlogAdapter(biddaloyApplication.blogList,this);
        blogListView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_blog);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        blogListView.setLayoutManager(mLayoutManager);
        blogListView.setItemAnimator(new DefaultItemAnimator());

        blogListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        blogListView.setAdapter(blogAdapter);
    }

    @Override
    public void onItemClick(int position) {

        String resultUrl = biddaloyApplication.baseUrl+biddaloyApplication.blogList.get(position).getUrl();
        Log.e("blog url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
