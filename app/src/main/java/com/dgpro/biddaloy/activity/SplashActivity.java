package com.dgpro.biddaloy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.serviceapi.InstituteApi;
import com.dgpro.biddaloy.serviceapi.UserApi;
import com.dgpro.biddaloy.store.AppSharedPreferences;

import dmax.dialog.SpotsDialog;

public class SplashActivity extends AppCompatActivity {

    InstituteApi instituteApi;
    UserApi userApi;
    BiddaloyApplication biddaloyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());
        instituteApi = new InstituteApi(this);
        userApi = new UserApi(this);

        readDataFromSharedPreference();

        downLoadSchoolInfo();

    }
    void readDataFromSharedPreference(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        biddaloyApplication.isUserLoggedIn = (sharedPref.getBoolean(Constants.USER_IS_LOGGEDIN, false));
        biddaloyApplication.userName = (sharedPref.getString(Constants.USER_NAME, ""));
        biddaloyApplication.password = (sharedPref.getString(Constants.USER_PASSWORD, ""));
        biddaloyApplication.userCatagory = (sharedPref.getString(Constants.USER_CATAGORY, ""));
        biddaloyApplication.baseUrl = (sharedPref.getString(Constants.BASE_URL, ""));
        biddaloyApplication.usreImageUrl = (sharedPref.getString("user_image_uri",""));

        Log.e("sp_username : "," "+biddaloyApplication.userName);
        Log.e("sp_usepass : "," "+biddaloyApplication.password);
        Log.e("uaerCategory : "," "+biddaloyApplication.userCatagory);
        Log.e("sp_baseUrl : "," "+biddaloyApplication.baseUrl);
        Log.e("sp_userImageUrl : "," "+biddaloyApplication.usreImageUrl);

    }
    void downLoadSchoolInfo(){

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        instituteApi.downLoadInstituteInfo(new InstituteApi.Callback<AboutInstituteModel>() {
            @Override
            public void onSuccess(AboutInstituteModel aboutInstituteModel) {
                AppSharedPreferences.saveStringToSharePreference(getBaseContext()
                        ,"school",aboutInstituteModel.getInstitute_name());
                biddaloyApplication.schoolName = AppSharedPreferences.readStringFromSharedPreference(getBaseContext(),"school");
                sendFcmKey();
                dialog.dismiss();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                SplashActivity.this.showDialog(errorMsg,"");
            }
        });

    }
    void sendFcmKey(){
        final SharedPreferences mSharedPreference = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        Boolean isKeyChanged = mSharedPreference.getBoolean("changedFcmToken",false);
        if(isKeyChanged) {
            String token = mSharedPreference.getString("fcmToken", "");
            Log.e("token to send", "Refreshed token: " + token);
            if (!token.equals("") || token.isEmpty()) {
                Log.e("FCM", "Refreshed token uploading ");

                final android.app.AlertDialog dialog = new SpotsDialog(this);
                dialog.show();
                userApi.sendFcmKey(token,new UserApi.Callback<String>(){
                    @Override
                    public void onSuccess(String s) {
                        dialog.dismiss();
                        Log.e("splash", "Token send successfull");
                        mSharedPreference.edit().putBoolean("changedFcmToken", false).apply();
                        gotoNavigationActivity();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        dialog.dismiss();
                        gotoNavigationActivity();
                    }
                });
            }else{
                Log.e("fcm_splash","token is empty");
                gotoNavigationActivity();
            }
        }else {
            Log.e("fcm_splash","key is not changed");
            gotoNavigationActivity();
        }
    }
    void showDialog(String title,String messege){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(messege)
                .setCancelable(true)
                .show();
    }
    void gotoNavigationActivity(){
        startActivity(new Intent(SplashActivity.this,NavigationActivity.class));
        SplashActivity.this.finish();
    }
}
