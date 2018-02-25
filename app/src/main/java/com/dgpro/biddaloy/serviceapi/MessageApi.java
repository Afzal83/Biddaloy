package com.dgpro.biddaloy.serviceapi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Model.NewMessageModel;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.Network.Model.InboxModel;
import com.dgpro.biddaloy.Network.Model.MailDetailsModel;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.Network.Model.OutboxModel;
import com.dgpro.biddaloy.Network.Model.SentMailResponseModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 2/15/2018.
 */

public class MessageApi {

    Context mContext;
    BiddaloyApplication biddaloyApplication ;
    public MessageApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public void getInboxMailFromNetwork(final Callback<List<InboxDataModel>> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getInboxMail(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                ,"50").enqueue(new retrofit2.Callback<InboxModel>() {

            @Override
            public void onResponse(Call<InboxModel> call, Response<InboxModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getInbox());
                }else {
                    int statusCode  = response.code();
                    callback.onError("Error");
                }
            }

            @Override
            public void onFailure(Call<InboxModel> call, Throwable t) {
                callback.onError("Error");
            }
        });
    }
    public void getOutboxMailFromNetwork(final Callback<List<OutboxDataModel>> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getOutboxMail(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                ,"200"
        ).enqueue(new retrofit2.Callback<OutboxModel>() {
            @Override
            public void onResponse(Call<OutboxModel> call, Response<OutboxModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getOutbox());
                }else {
                    int statusCode  = response.code();
                    callback.onError(response.body().getError_report());
                }
            }
            @Override
            public void onFailure(Call<OutboxModel> call, Throwable t) {
                //Log.e("error ","error to download by retrofit");
                callback.onError("Error");
            }
        });
    }
    public void downLoadDetailMail(String messageId,final Callback<MailDetailsModel> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getDetailsMail(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                , messageId
        ).enqueue(new retrofit2.Callback<MailDetailsModel>() {

            @Override
            public void onResponse(Call<MailDetailsModel> call, Response<MailDetailsModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(response.body().getError_report());
                }
            }
            @Override
            public void onFailure(Call<MailDetailsModel> call, Throwable t) {
                callback.onError("download error .. ");
            }
        });
    }
    public void submitMail(NewMessageModel newMessageModel,final Callback<String> callback){

        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.userName);
        RequestBody userPass = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.password);
        RequestBody userCategory = RequestBody.create(MediaType.parse("text/plain"),biddaloyApplication.userCatagory);

        Log.e("receiver Id :", newMessageModel.getReceiverId()+"");
        Log.e("receiver cat:", newMessageModel.getReceiverCatagory()+"");
        Log.e("message Sub :", newMessageModel.getSubject()+"");
        Log.e("body :", newMessageModel.getMessageBody()+"");






        RequestBody receiverId = RequestBody.create(MediaType.parse("text/plain"),newMessageModel.getReceiverId());
        RequestBody receiverCatagory = RequestBody.create(MediaType.parse("text/plain"),newMessageModel.getReceiverCatagory());
        RequestBody subject = RequestBody.create(MediaType.parse("text/plain"),newMessageModel.getSubject());
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"),newMessageModel.getMessageBody());

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        Call<SentMailResponseModel> call = mService.sendMail( userName, userPass, userCategory,
                receiverId,receiverCatagory,subject,message);
        call.enqueue(new retrofit2.Callback<SentMailResponseModel>() {
            @Override
            public void onResponse(Call<SentMailResponseModel> call, Response<SentMailResponseModel> response) {
                if(response.isSuccessful()){
                    Log.e("message send","Successful");
                    callback.onSuccess("Succss");

                }else{
                    Log.e("message send","Successful");
                    callback.onError("Error to send Mail");
                }
            }
            @Override
            public void onFailure(Call<SentMailResponseModel> call, Throwable t) {
                Log.e("message send","network error");
                callback.onError("Network Error");
            }
        });
    }

    public static abstract class Callback<T>
    {
        public abstract void onSuccess(T t);
        public abstract void onError(String errorMsg);
    }
}
