package com.dgpro.biddaloy.serviceapi;

import android.content.Context;
import android.util.Log;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutUsModel;
import com.dgpro.biddaloy.Network.Model.ProductModel;
import com.dgpro.biddaloy.Network.Remot.AnotherRetroService;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 2/16/2018.
 */

public class DeveloperApi {

    BiddaloyApplication biddaloyApplication;
    Context mContext;
    public DeveloperApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public void downLoadAboutUsData(final Callback<AboutUsModel> callback){

        AnotherRetroService mService = ApiUtils.getAboutUsService();
        mService.aboutUs(
                biddaloyApplication.userCatagory
        )//biddaloyApplication.getUserCatagory())
                .enqueue(new retrofit2.Callback<AboutUsModel>() {

            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    Log.e("downLoadAboutUsData","success");
                }else {
                    int statusCode  = response.code();
                    Log.e("downLoadAboutUsData","error");
                    callback.onError(response.body().getError_report());
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                callback.onError("Error");
                Log.e("downLoadAboutUsData","network error");
            }
        });
    }

    public static abstract class Callback<T>
    {
        public abstract void onSuccess(T t);
        public abstract void onError(String errorMsg);
    }

}
