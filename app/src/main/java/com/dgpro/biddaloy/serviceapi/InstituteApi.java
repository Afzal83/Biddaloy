package com.dgpro.biddaloy.serviceapi;

import android.content.Context;
import android.util.Log;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Babu on 2/15/2018.
 */

public class InstituteApi {
    Context mContext;
    BiddaloyApplication biddaloyApplication;

    public InstituteApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public  void downLoadInstituteInfo(final Callback<AboutInstituteModel> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.aboutInstitute(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
        ).enqueue(new retrofit2.Callback<AboutInstituteModel>() {

                    @Override
                    public void onResponse(Call<AboutInstituteModel> call, Response<AboutInstituteModel> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getError() == 1){
                                callback.onError(response.body().getError_report());
                            }else{
                                callback.onSuccess(response.body());
                            }
                        }else {
                            int statusCode  = response.code();
                            callback.onError("error to download..");
                        }
                    }

                    @Override
                    public void onFailure(Call<AboutInstituteModel> call, Throwable t) {
                        callback.onError("error to download..");
                    }
                });
    }
    public static abstract class Callback<T>
    {
        public abstract void onSuccess(T t);
        public abstract void onError(String errorMsg);
    }

}
