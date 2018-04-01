package com.dgpro.biddaloy.serviceapi;

import android.content.Context;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.Network.Model.LibraryListModel;
import com.dgpro.biddaloy.Network.Model.LibraryModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Babu on 3/28/2018.
 */

public class LibraryApi {
    Context mContext;
    BiddaloyApplication biddaloyApplication;

    public LibraryApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public  void downLoadLibraryData(final Callback<List<LibraryModel>> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.downLoadLibrary(
                 biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,"100"
        ).enqueue(new retrofit2.Callback<LibraryListModel>() {

            @Override
            public void onResponse(Call<LibraryListModel> call, Response<LibraryListModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 1){
                        callback.onError(response.body().getError_report());
                    }else{
                        callback.onSuccess(response.body().getBook_name());
                    }
                }else {
                    int statusCode  = response.code();
                    callback.onError("error to download..");
                }
            }

            @Override
            public void onFailure(Call<LibraryListModel> call, Throwable t) {
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
