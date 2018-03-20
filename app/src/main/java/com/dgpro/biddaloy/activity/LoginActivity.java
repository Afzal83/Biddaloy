package com.dgpro.biddaloy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.adapter.UserDrpupdownAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.Helper.ImageHelper;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.LoginDataModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.service.DeleteTokenService;
import com.dgpro.biddaloy.serviceapi.UserApi;
import com.dgpro.biddaloy.store.AppSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    UserApi userApi;
    BiddaloyApplication biddaloyApplication;

    String[] SpinnerItem = {"Select User","Student","Guardian","Teacher"};
    List<String> droupdownList;

    private String userCatagory = "";
    private String baseUrl = "";
    private String userName = "";
    private String userPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userApi = new UserApi(this);
        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());

        setContentView(R.layout.activity_login);

        (findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginStuffs();
            }
        });

        droupdownList = Arrays.asList(SpinnerItem);
        Spinner spin = (Spinner) findViewById(R.id.user_spinner);
        //ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,SpinnerItem);
        UserDrpupdownAdapter aa = new UserDrpupdownAdapter(this,R.layout.droupdown_blog_catagory,droupdownList);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("selected user type ", ""+SpinnerItem[i]);
                userCatagory = SpinnerItem[i];
                if(userCatagory.contains("Select")){
                    userCatagory = "";
                    return;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void startLoginStuffs(){

//        Boolean isValidCredential = true;
//        baseUrl = "demo.101bd.com";//((EditText)(findViewById(R.id.base_url_login))).getText().toString();
//        userName = "Najmun";//"Ashraful";//((EditText)(findViewById(R.id.user_name_login))).getText().toString();
//        userPass = "123";//((EditText)(findViewById(R.id.password_login))).getText().toString();

        baseUrl = ((EditText)(findViewById(R.id.base_url_login))).getText().toString();
        userName = ((EditText)(findViewById(R.id.user_name_login))).getText().toString();
        userPass = ((EditText)(findViewById(R.id.password_login))).getText().toString();

        Log.e("user name : ",userName);
        Log.e("user pass : ",userPass);
        Log.e("base url  : ",baseUrl);
        Log.e("user catagory  : ",userCatagory);

        if(baseUrl.isEmpty() || baseUrl == null){
            this.showDialog("","Url Field is Empty");
            return;
        }
        if(userName.isEmpty() ||  userName == null){
            this.showDialog("","User Name Field is Empty");
            return;
        }
        if(userPass.isEmpty() || userPass == null){
            this.showDialog("","Password Field is Empty");
            return;
        }
        if(userCatagory.isEmpty() ||  userCatagory == null){
            this.showDialog("","Please select a catagory");
            return;
        }
        login();
    }
    void login(){

        baseUrl = "http://"+baseUrl+"/";

        LoginModel loginModel = new LoginModel();
        loginModel.setUserName(userName);
        loginModel.setUserPass(userPass);
        loginModel.setUserCategory(userCatagory);
        loginModel.setBaseUrl(baseUrl);

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Logging In..")
                .content("Please Wait")
                .progress(true, 0)
                .show();

        userApi.doLogin(loginModel,new UserApi.Callback<LoginDataModel>(){

            @Override
            public void onSuccess(LoginDataModel model) {
                dialog.dismiss();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        LoginActivity.this.startService(new Intent(LoginActivity.this, DeleteTokenService.class));
                    }
                };
                thread.start();

                LoginModel mModel = new LoginModel();
                mModel.setLoggedin(true);
                mModel.setUserName(userName);
                mModel.setUserPass(userPass);
                mModel.setUserCategory(userCatagory);
                mModel.setUserPhone(model.getMobile());
                mModel.setUserAddress(model.getAddress());
                mModel.setUserImage(model.getImage_url());
                mModel.setBaseUrl(baseUrl);

                userApi.saveLoginDataToSharedPreference(getBaseContext(),mModel);
                saveImage(model.getImage_url());

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginActivity.this,SplashActivity.class));
                        LoginActivity.this.finish();
                    }
                }, 5000);

            }
            @Override
            public void onError(String errorMessage) {
                dialog.dismiss();
                LoginActivity.this.showDialog(errorMessage,"");
            }
        });
    }
    void saveImage(String Url){

        String url = baseUrl+Url;
        Log.e("user_image_url",url+"");
        Glide.with(this).load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e("saving image","saved");
                        ImageHelper imgHelper = new ImageHelper();
                        String uri = imgHelper.saveToInternalStorage(resource, getApplicationContext(), "attila");
                        AppSharedPreferences.saveStringToSharePreference(getBaseContext(),"user_image_uri",uri);
                        biddaloyApplication.usreImageUrl = uri;
                        Log.e("image_uri"," "+uri);
                    }

                });
    }

    void showDialog(String title,String messege){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(messege)
                .setCancelable(true)
                .show();
    }
}