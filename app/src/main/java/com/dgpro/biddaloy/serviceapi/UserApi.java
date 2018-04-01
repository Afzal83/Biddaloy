package com.dgpro.biddaloy.serviceapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.FcmSubmitResponseMedel;
import com.dgpro.biddaloy.Network.Model.LoginDataModel;
import com.dgpro.biddaloy.Network.Model.PasswordModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.activity.LandingActivity;
import com.dgpro.biddaloy.activity.LoginActivity;
import com.dgpro.biddaloy.activity.SplashActivity;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 2/14/2018.
 */

public class UserApi {

    private BiddaloyApplication biddaloyApplication;
    private Context mContext;
    public UserApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public void doLogin(final LoginModel loginInPut,final Callback<LoginDataModel> callback){


        Log.e("test","I am working ");
        Log.e("login:userName: ",loginInPut.getUserName());
        Log.e("login:userPass: ",loginInPut.getUserPass());
        Log.e("login:userCat: ",loginInPut.getUserCategory());
        Log.e("base Url ",loginInPut.getBaseUrl());


        RetroService mService = ApiUtils.getLoginDataService(loginInPut.getBaseUrl());
        mService.getLoginData(
                loginInPut.getUserName()
                ,loginInPut.getUserPass()
                ,loginInPut.getUserCategory()
        ).enqueue(new retrofit2.Callback<LoginDataModel>() {
            @Override
            public void onResponse(Call<LoginDataModel> call, Response<LoginDataModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 1){
                        Log.e("login","network error = 1");
                        callback.onError(response.body().getError_report());
                    }else{
                        Log.e("login:user: ",response.body().getName());
                        Log.e("login:user: ",response.body().getImage_url());

                        callback.onSuccess(response.body());
                    }
                }else {
                    int statusCode  = response.code();
                    Log.e("login","error code : "+statusCode);
                    callback.onError("Error to Log in...");
                }
            }
            @Override
            public void onFailure(Call<LoginDataModel> call, Throwable t) {
                Log.e("login","network error");
                callback.onError("Error");
            }
        });
    }
    public void saveLoginDataToSharedPreference(Context mContext,LoginModel mModel){
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(Constants.USER_IS_LOGGEDIN,mModel.getLoggedin());

        editor.putString(Constants.BASE_URL,mModel.getBaseUrl());

        editor.putString(Constants.USER_NAME,mModel.getUserName());
        editor.putString(Constants.USER_PASSWORD,mModel.getUserPass());
        editor.putString(Constants.USER_CATAGORY,mModel.getUserCategory());
        editor.putString(Constants.USER_PHONE,mModel.getUserPhone());
        editor.putString(Constants.USER_ADDRESS,mModel.getUserAddress());
        editor.putString(Constants.USER_IMAGE_URL,mModel.getUserImage());
        editor.apply();

    }
    public void insertLoginModelToSharePreference(Context mContext,LoginModel mModel){
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(Constants.USER_IS_LOGGEDIN,mModel.getLoggedin());
        editor.putString(Constants.USER_NAME,mModel.getUserName());
        editor.putString(Constants.USER_PASSWORD,mModel.getUserPass());
        editor.putString(Constants.USER_CATAGORY,mModel.getUserCategory());
        editor.putString(Constants.USER_PHONE,mModel.getUserPhone());
        editor.putString(Constants.USER_ADDRESS,mModel.getUserAddress());
        editor.putString(Constants.USER_IMAGE_URL,mModel.getUserImage());
        editor.apply();

    }
    public LoginModel getLoginDataFromSharedPreference(Context mContext){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        LoginModel mModel = new LoginModel();

        mModel.setLoggedin((sharedPref.getBoolean(Constants.USER_IS_LOGGEDIN, false)));
        mModel.setUserName(sharedPref.getString(Constants.USER_NAME, ""));
        mModel.setUserPass(sharedPref.getString(Constants.USER_PASSWORD, ""));
        mModel.setUserCategory(sharedPref.getString(Constants.USER_CATAGORY, ""));
        mModel.setBaseUrl(sharedPref.getString(Constants.BASE_URL, ""));
        mModel.setUserImage(sharedPref.getString(Constants.USER_IMAGE_URL, ""));

        return mModel;
    }

    public void sendFcmKey(final String token,final Callback<String> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.sendPushNotification(
                 biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                , token
                ,""
        ).enqueue(new retrofit2.Callback<FcmSubmitResponseMedel>() {

            @Override
            public void onResponse(Call<FcmSubmitResponseMedel> call, Response<FcmSubmitResponseMedel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess("Success");
                }else {
                    callback.onError("error");
                }
            }
            @Override
            public void onFailure(Call<FcmSubmitResponseMedel> call, Throwable t) {
                callback.onError("error");
            }
        });
    }
    public void changeUserPassword(String newPass, String confirmNewPass, final Callback<String> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.changePassword(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                ,biddaloyApplication.password
                ,newPass
                ,confirmNewPass
        ).enqueue(new retrofit2.Callback<PasswordModel>() {

            @Override
            public void onResponse(Call<PasswordModel> call, Response<PasswordModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 0){
                        callback.onSuccess(response.body().getError_report());
                    }else{
                        callback.onError(response.body().getError_report());
                    }
                }else {
                    callback.onError(response.body().getError_report());
                }
            }
            @Override
            public void onFailure(Call<PasswordModel> call, Throwable t) {
                callback.onError("Network error");
            }
        });
    }

    public static abstract class Callback<T>
    {
        public abstract void onSuccess(T t);
        public abstract void onError(String errorMsg);
    }

}
