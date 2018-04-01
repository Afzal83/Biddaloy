package com.dgpro.biddaloy.serviceapi;

import android.content.Context;
import android.util.Log;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AttendenceDataModel;
import com.dgpro.biddaloy.Network.Model.AttendenceModel;
import com.dgpro.biddaloy.Network.Model.DiaryDataModel;
import com.dgpro.biddaloy.Network.Model.DiaryModel;
import com.dgpro.biddaloy.Network.Model.DueModel;
import com.dgpro.biddaloy.Network.Model.NoticeDataModel;
import com.dgpro.biddaloy.Network.Model.NoticeModel;
import com.dgpro.biddaloy.Network.Model.PaymentDataModel;
import com.dgpro.biddaloy.Network.Model.PaymentListModel;
import com.dgpro.biddaloy.Network.Model.ResultDataModel;
import com.dgpro.biddaloy.Network.Model.ResultListModel;
import com.dgpro.biddaloy.Network.Model.RoutineDataModel;
import com.dgpro.biddaloy.Network.Model.RoutineListModel;
import com.dgpro.biddaloy.Network.Model.StudentDataModel;
import com.dgpro.biddaloy.Network.Model.StudentListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.constants.NetWorkErrorConstant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 2/15/2018.
 */

public class StudentApi {
    BiddaloyApplication biddaloyApplication;
    Context mContext;
    public StudentApi(Context mContext){
        this.mContext = mContext;
        biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
    }

    public void downLoadStudentList(final Callback<List<StudentDataModel>> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getMyStudentList(
                biddaloyApplication.userName
                ,biddaloyApplication.password
        ).enqueue(new retrofit2.Callback<StudentListModel>() {

            @Override
            public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 1){
                        Log.e("err","error to download by retrofit");
                        callback.onError(response.body().getError_report());
                    }else {
                        Log.e("err "," etrofit");
                        callback.onSuccess(response.body().getSearch_result());
                    }
                }else {
                    //int statusCode  = response.code();
                    Log.e("fail ","fail to download studentList");
                    callback.onError("Error");
                }
            }
            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
                Log.e("network error ","error to download by retrofit");
                callback.onError("Error");
            }
        });
    }

    public void downLoadAttendenceDataFromNetwork(String aMonth,String aYear,final Callback<List<AttendenceDataModel>> callback){
        String month = aMonth;
        String year = aYear;

        Calendar now = Calendar.getInstance();

        if(aMonth.isEmpty() || aMonth.contentEquals("")){
            month = (now.get(Calendar.MONTH) + 1)+"";
        }
        if(aYear.isEmpty() || aYear.contentEquals("")){
            year = now.get(Calendar.YEAR)+"";
        }
        if(month.length() == 1){
            month = "0"+month;
        }

        Log.e("retrofit","month: "+month);
        Log.e("retrofit","year: "+year);
        Log.e("retrofit","userName: "+biddaloyApplication.userName);
        Log.e("retrofit","userPass: "+biddaloyApplication.password);
        Log.e("retrofit","userCategory: "+biddaloyApplication.userCatagory);
        Log.e("retrofit","studentId: "+biddaloyApplication.studentId);

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getAttendenceData(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
                ,month
                ,year
                ,biddaloyApplication.studentId
                ,"100"
        ).enqueue(new retrofit2.Callback<AttendenceModel>() {

            @Override
            public void onResponse(Call<AttendenceModel> call, Response<AttendenceModel> response) {
                if(response.isSuccessful()) {

                    Log.e("success_attendence",""+response.body().getAttendance().size());
                    biddaloyApplication.studentPresentDay = response.body().getPresent();
                    biddaloyApplication.studentAbsentDay = response.body().getAbsence();
                    callback.onSuccess(response.body().getAttendance());

                }else {
                    Log.e("error_attendenc","Error");
                    int statusCode  = response.code();
                    callback.onError("Error");
                }
            }

            @Override
            public void onFailure(Call<AttendenceModel> call, Throwable t) {
                Log.e("error_attendenc","Network error");
                callback.onError("Error");
            }
        });
    }
    public void downLoadDiaryDataFromNetwork(final Callback<List<DiaryDataModel>> callback){

        Calendar now = Calendar.getInstance();
        String month = (now.get(Calendar.MONTH) + 1)+"";
        String year = now.get(Calendar.YEAR)+"";

        if(month.length() == 1){
            month = "0"+(now.get(Calendar.MONTH) + 1)+"";
        }
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getDiary(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,month
                ,year
                ,biddaloyApplication.studentId
        ).enqueue(new retrofit2.Callback<DiaryModel>() {

            @Override
            public void onResponse(Call<DiaryModel> call, Response<DiaryModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getDiary());
                }else {
                    int statusCode  = response.code();
                    callback.onError(response.body().getError_report());
                }
            }

            @Override
            public void onFailure(Call<DiaryModel> call, Throwable t) {
                callback.onError("Error");
            }
        });
    }

    public void downLoadStudentResultData(final Callback<List<ResultDataModel>> callback){

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getResultList(
                 biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,biddaloyApplication.studentId
        ).enqueue(new retrofit2.Callback<ResultListModel>() {

            @Override
            public void onResponse(Call<ResultListModel> call, Response<ResultListModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getResult());
                }else {
                    int statusCode  = response.code();
                    callback.onError(response.body().getError_report());
                }
            }

            @Override
            public void onFailure(Call<ResultListModel> call, Throwable t) {
                callback.onError("Network Error");
            }
        });
    }

    public void downLoadRoutine(final Callback<List<RoutineDataModel>> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getStudentRoutine(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,biddaloyApplication.studentId
        ).enqueue(new retrofit2.Callback<RoutineListModel>() {

            @Override
            public void onResponse(Call<RoutineListModel> call, Response<RoutineListModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getRoutine_name());
                }else {
                    int statusCode  = response.code();
                    callback.onError(response.body().getError_report());
                }
            }

            @Override
            public void onFailure(Call<RoutineListModel> call, Throwable t) {
                //Log.e("error ","error to download by retrofit");
                callback.onError("Network Error");
            }
        });
    }
    public void downLoadPaymentInfo(final Callback<List<PaymentDataModel>> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getPaymentList(
                 biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,biddaloyApplication.studentId
        ).enqueue(new retrofit2.Callback<PaymentListModel>() {

                    @Override
                    public void onResponse(Call< PaymentListModel> call, Response<PaymentListModel> response) {
                        if(response.isSuccessful()) {
                            callback.onSuccess(response.body().getPayment_list());
                        }else {
                            int statusCode  = response.code();
                            callback.onError(response.body().getError_report());
                        }
                    }
                    @Override
                    public void onFailure(Call<PaymentListModel> call, Throwable t) {
                        //Log.e("error ","error to download by retrofit");
                        callback.onError("Network Error");
                    }
                });

    }

    public void downLoadDueInfo(final Callback<DueModel> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getDue(
                 biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,biddaloyApplication.studentId
        ).enqueue(new retrofit2.Callback<DueModel>() {

            @Override
            public void onResponse(Call< DueModel> call, Response<DueModel> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }else {
                    int statusCode  = response.code();
                    callback.onError(response.body().getError_report());
                }
            }
            @Override
            public void onFailure(Call<DueModel> call, Throwable t) {
                //Log.e("error ","error to download by retrofit");
                callback.onError("Network Error");
            }
        });

    }

    public void downLoadNoticeData(final Callback<List<NoticeDataModel>> callback){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getNotice(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                , "10"
        ).enqueue(new retrofit2.Callback<NoticeModel>() {

            @Override
            public void onResponse(Call<NoticeModel> call, Response<NoticeModel> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() == 1){
                        callback.onError(response.body().getError_report());
                    }else{
                        callback.onSuccess(response.body().getNotice());
                    }
                }else {
                    callback.onError("Error");
                }
            }
            @Override
            public void onFailure(Call<NoticeModel> call, Throwable t) {
                //Log.e("error ","error to download by retrofit");
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
