package com.dgpro.biddaloy.serviceapi;

import android.content.Context;
import android.util.Log;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutUsModel;
import com.dgpro.biddaloy.Network.Model.BlogDataModel;
import com.dgpro.biddaloy.Network.Model.BlogListModel;
import com.dgpro.biddaloy.Network.Remot.AnotherRetroService;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 2/18/2018.
 */

public class BlogApi {
    BiddaloyApplication biddaloyApplication;
    Context mContext;
    public BlogApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public void downLoadBlogData(final Callback<List<BlogDataModel>> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getBlog(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
        ).enqueue(new retrofit2.Callback<BlogListModel>() {

            @Override
            public void onResponse(Call<BlogListModel> call, Response<BlogListModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 0){
                        callback.onSuccess(response.body().getBlog_details());
                    }else{
                        callback.onError(response.body().getError_report());
                    }
                }else {
                    int statusCode  = response.code();
                    callback.onError("Error to downLoad Bolog");
                }
            }

            @Override
            public void onFailure(Call<BlogListModel> call, Throwable t) {
               callback.onError("Error");
            }
        });
    }

    public static abstract class Callback<T>
    {
        public abstract void onSuccess(T t);
        public abstract void onError(String errorMsg);
    }

}
