package com.dgpro.biddaloy.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.FcmSubmitResponseMedel;
import com.dgpro.biddaloy.Network.Model.RoutineListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIdService";
    private static final String TOPIC_GLOBAL = "global";
    String baseUrl = "";
 
    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
 
        // now subscribe to `global` topic to receive app wide notifications
        ////FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL);
 
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
 
        sendRegistrationToServer(refreshedToken);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putBoolean("changedFcmToken", true).apply();
        prefs.edit().putString("fcmToken",refreshedToken).apply();
    }
    void sendRegistrationToServer(String token) {


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName  = sharedPref.getString(Constants.USER_NAME, "");
        String userPass =  sharedPref.getString(Constants.USER_PASSWORD, "");
        String userCategory =  sharedPref.getString(Constants.USER_CATAGORY, "");
        String baseUrl =  sharedPref.getString(Constants.BASE_URL, "");

        if(userName.isEmpty() || userName.contentEquals("")
                || userPass.isEmpty() || userPass.contentEquals("")
                || userCategory.isEmpty() || userCategory.contentEquals("")
                || baseUrl.isEmpty() || baseUrl.contentEquals("")){
            return;
        }


        Log.e("fcm _key","user:"+userName);
        Log.e("fcm _key","password:"+userPass);
        Log.e("fcm _key","category:"+userCategory);
        Log.e("fcm baseUrl","baseUrl:"+baseUrl);

        RetroService mService = ApiUtils.getLoginDataService(baseUrl);
        mService.sendPushNotification(
                userName
                , userPass
                , userCategory
                , token
                , ""
        ).enqueue(new Callback<FcmSubmitResponseMedel>() {

            @Override
            public void onResponse(Call<FcmSubmitResponseMedel> call, Response<FcmSubmitResponseMedel> response) {
                if (response.isSuccessful()) {
                    Log.e("service ", "Token send successfull");
                } else {
                    int statusCode = response.code();
                    Log.e("fcm_service ", "token send fail");
                }
            }

            @Override
            public void onFailure(Call<FcmSubmitResponseMedel> call, Throwable t) {
                Log.e("fcm_service ", "token send fail");
            }
        });
    }
}