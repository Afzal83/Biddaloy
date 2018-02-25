package com.dgpro.biddaloy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dgpro.biddaloy.Network.Model.AttendenceDataModel;
import com.dgpro.biddaloy.Network.Model.DiaryDataModel;
import com.dgpro.biddaloy.Network.Model.DueModel;
import com.dgpro.biddaloy.Network.Model.ResultDataModel;
import com.dgpro.biddaloy.Network.Model.RoutineDataModel;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AttendenceModel;
import com.dgpro.biddaloy.Network.Model.DiaryModel;
import com.dgpro.biddaloy.Network.Model.PaymentDataModel;
import com.dgpro.biddaloy.Network.Model.PaymentListModel;
import com.dgpro.biddaloy.Network.Model.ResultListModel;
import com.dgpro.biddaloy.Network.Model.RoutineListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.serviceapi.StudentApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseStudentActivity extends AppCompatActivity {

    StudentApi studentApi;

    ProgressDialog progressDoalog;
    BiddaloyApplication biddaloyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());
        studentApi = new StudentApi(this);
    }

    void downLoadFromNetwork(){
        showProgressDialog();
        downLoadAttendenceDataFromNetwork();
    }
    void downLoadAttendenceDataFromNetwork(){
        studentApi.downLoadAttendenceDataFromNetwork("",""
                , new StudentApi.Callback<List<AttendenceDataModel>>() {
                    @Override
                    public void onSuccess(List<AttendenceDataModel> attendenceDataModels) {
                        biddaloyApplication.attendentList = attendenceDataModels;
                        downLoadStudentResultData();
                    }
                    @Override
                    public void onError(String errorMsg) {
                        networkCallFinished();
                        //ShowAnErrorDialog();
                    }
                });
    }
    void downLoadStudentResultData(){

        studentApi.downLoadStudentResultData(new StudentApi.Callback<List<ResultDataModel>>() {
            @Override
            public void onSuccess(List<ResultDataModel> resultDataModels) {
                progressDoalog.setProgress(20);
                biddaloyApplication.resultList = resultDataModels;
                downLoadRoutine();
            }
            @Override
            public void onError(String errorMsg) {
                networkCallFinished();
                //ShowAnErrorDialog();
            }
        });
    }
    void downLoadRoutine(){
        progressDoalog.setProgress(40);
        studentApi.downLoadRoutine(new StudentApi.Callback<List<RoutineDataModel>>() {
            @Override
            public void onSuccess(List<RoutineDataModel> routineDataModels) {
                biddaloyApplication.routineList =  routineDataModels;
                downLoadDiaryDataFromNetwork();
            }
            @Override
            public void onError(String errorMsg) {
                networkCallFinished();
                //ShowAnErrorDialog();
            }
        });
    }
    void downLoadDiaryDataFromNetwork(){

        studentApi.downLoadDiaryDataFromNetwork(new StudentApi.Callback<List<DiaryDataModel>>() {
            @Override
            public void onSuccess(List<DiaryDataModel> diaryDataModels) {

                progressDoalog.setProgress(70);
                biddaloyApplication.diaryList =  diaryDataModels;
                downLoadPaymentInfo();
            }

            @Override
            public void onError(String errorMsg) {
                networkCallFinished();
                //ShowAnErrorDialog();
            }
        });
    }
    void downLoadPaymentInfo(){

        studentApi.downLoadPaymentInfo(new StudentApi.Callback<List<PaymentDataModel>>() {
            @Override
            public void onSuccess(List<PaymentDataModel> paymentDataModels) {
                progressDoalog.setProgress(90);
                biddaloyApplication.paymentList = paymentDataModels;
                downLoadDueInfo();
            }

            @Override
            public void onError(String errorMsg) {
                networkCallFinished();
                //ShowAnErrorDialog();
            }
        });

    }
    void downLoadDueInfo(){
        progressDoalog.setProgress(100);
        studentApi.downLoadDueInfo(new StudentApi.Callback<DueModel>() {
            @Override
            public void onSuccess(DueModel dueModel) {
                biddaloyApplication.studentDue = dueModel.getDue();
                networkCallFinished();
            }

            @Override
            public void onError(String errorMsg) {
                //show error message
                networkCallFinished();
            }
        });
    }
    void showProgressDialog(){
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        progressDoalog.setTitle("Down Loading Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();
    }
    void dismissProgressDialog(){
        progressDoalog.dismiss();
    }
    public void networkCallFinished(){
        dismissProgressDialog();
    }
    void clearMemory(){
        biddaloyApplication.studentName = "";
        biddaloyApplication.studentRoll = "";
        biddaloyApplication.studentImage = "";
        biddaloyApplication.attendentList.clear();
        biddaloyApplication.diaryList.clear();
        biddaloyApplication.routineList.clear();
        biddaloyApplication.resultList.clear();
        biddaloyApplication.paymentList.clear();
    }
}
