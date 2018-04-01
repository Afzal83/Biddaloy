package com.dgpro.biddaloy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.FcmSubmitResponseMedel;
import com.dgpro.biddaloy.Network.Model.LoginDataModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.constants.NetWorkErrorConstant;
import com.dgpro.biddaloy.serviceapi.UserApi;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LandingActivity extends AppCompatActivity {

    BiddaloyApplication biddaloyApplication;
    SharedPreferences sharedPref;
    String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        biddaloyApplication = ((BiddaloyApplication) this.getApplicationContext());
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        Log.e("screen density===>>", "" + getResources().getDisplayMetrics().density);

        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(NetWorkErrorConstant.NETWORK_ERROR)
                    .setMessage(NetWorkErrorConstant.NETWORK_ERROR_MESSAGE)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 110);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            makeDecision();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 110) {
            if (resultCode == RESULT_OK) {
                makeDecision();
            } else {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void makeDecision() {

        LoginModel mModel = new UserApi(this).getLoginDataFromSharedPreference(this);

        Log.e("userName : ",mModel.getUserName());
        Log.e("userPass : ",mModel.getUserPass());
        Log.e("userCatagory : ",mModel.getUserCategory());
        Log.e("baseUrl : ",mModel.getBaseUrl());

        if(mModel.getBaseUrl().isEmpty()){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }

        else if (mModel.getLoggedin()) {
            Log.e("user logged in","logged in");
            checkUserLoginStatus(mModel);
        }
        else {
            Log.e("user is not logged in","not logged in");
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }
    void checkUserLoginStatus(LoginModel model){

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        new UserApi(this).doLogin(model,new UserApi.Callback<LoginDataModel>(){

            @Override
            public void onSuccess(LoginDataModel model) {
                dialog.dismiss();
                startActivity(new Intent(LandingActivity.this,SplashActivity.class));
                LandingActivity.this.finish();
            }
            @Override
            public void onError(String errorMessage) {
                dialog.dismiss();
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                LandingActivity.this.finish();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Error ...")
                .setMessage("Please check Network ... ")
                .show();
    }
}