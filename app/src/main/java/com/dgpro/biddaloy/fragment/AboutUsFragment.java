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
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutUsModel;
import com.dgpro.biddaloy.Network.Model.NoticeModel;
import com.dgpro.biddaloy.Network.Model.ProductModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.WebViewActivity;
import com.dgpro.biddaloy.adapter.BlogAdapter;
import com.dgpro.biddaloy.adapter.OurProductAdapter;
import com.dgpro.biddaloy.constants.NetWorkErrorConstant;
import com.dgpro.biddaloy.serviceapi.DeveloperApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 1/28/2018.
 */

public class AboutUsFragment extends Fragment implements CallFromMessageList {

    DeveloperApi developerApi;

    List<ProductModel> productList;

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_about_us,container,false);
        developerApi = new DeveloperApi(getActivity());

        downLoadAboutUsData();
        return mView;
    }
    void downLoadAboutUsData(){

        developerApi.downLoadAboutUsData(new DeveloperApi.Callback<AboutUsModel>() {
            @Override
            public void onSuccess(AboutUsModel productModels) {
                productList = new ArrayList<>();
                productList.addAll(productModels.getProducts());

                ImageView companyImage = (ImageView)mView.findViewById(R.id.our_logo);
                Picasso.with(getActivity())
                        .load(productModels.getLogo())
                        .placeholder(R.drawable.artifical_soft)
                        .error(R.drawable.artifical_soft)
                        .into(companyImage);

                ((TextView)mView.findViewById(R.id.about_company_name)).setText(productModels.getCompany_name());
                ((TextView)mView.findViewById(R.id.aboutus_text)).setText(productModels.getAbout_us());
                setProductList();
            }

            @Override
            public void onError(String errorMsg) {
                Log.e("Error Happened","Error");
                //show an Error dialog
            }
        });
    }

    void setProductList(){
        OurProductAdapter mAdapter = new OurProductAdapter(getActivity(),productList,this);
        RecyclerView productListView = (RecyclerView)mView. findViewById(R.id.productList_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        productListView.setLayoutManager(mLayoutManager);
        productListView.setItemAnimator(new DefaultItemAnimator());

        productListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        productListView.setAdapter(mAdapter);
    }

    @Override
    public void onMessageListClick(int messageId) {
        String resultUrl =productList.get(messageId).getUrl();
        Log.e("blog url :",resultUrl);

        Intent intent  = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",resultUrl);
        startActivity(intent);
    }
}
