package com.dgpro.biddaloy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.PaymentSubmitResponseModel;
import com.dgpro.biddaloy.Network.Model.PaymentSystemDataModel;
import com.dgpro.biddaloy.Network.Model.PaymentSystemListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.dialog.TransientDialog;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePayment extends AppCompatActivity {
    TransientDialog transientDialog;
    BiddaloyApplication biddaloyApplication;

    List<String> paymentSystemDroupdownList;
    String selectedPaymentSystem = "";
    String studentId = "";

    EditText amount_et,accountNumber_et,transaction_et,comment_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);
        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());
        transientDialog = new TransientDialog(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.create_payment_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Submit Payment");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bindView();
        downLoadPaymentSystems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    void bindView(){
        amount_et = (EditText)findViewById(R.id.submit_payment_amount);
        accountNumber_et = (EditText)findViewById(R.id.submit_payment_account_no);
        transaction_et = (EditText)findViewById(R.id.submit_payment_transection_id);
        comment_et = (EditText)findViewById(R.id.payment_submit_comment);

        ((Button)findViewById(R.id.submit_payment)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                submitPayment();
            }
        });

    }
    void downLoadPaymentSystems(){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getPaymentSystems("Ashraful","123","guardian").enqueue(new Callback<PaymentSystemListModel>() {

            @Override
            public void onResponse(Call<PaymentSystemListModel> call, Response<PaymentSystemListModel> response) {
                if (response.isSuccessful()) {
                    paymentSystemDroupdownList = new ArrayList<>();
                    for (PaymentSystemDataModel model : response.body().getPayment_info()) {
                        paymentSystemDroupdownList.add(model.getPayment_system());
                    }
                    setPaymentSystemDroupDown();
                } else {
                    int statusCode = response.code();
                    Log.e("fail ", "fail to download by retrofit");
                }
            }
            @Override
            public void onFailure(Call<PaymentSystemListModel> call, Throwable t) {
                Log.e("error ", "error to download by retrofit");
            }
        });
    }
    void setPaymentSystemDroupDown(){

        Spinner mDroupDownSpinner = (Spinner) findViewById(R.id.payment_system_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentSystemDroupdownList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDroupDownSpinner.setAdapter(dataAdapter);
        mDroupDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPaymentSystem =  paymentSystemDroupdownList.get(i);
                Log.e("selected catagory ",selectedPaymentSystem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void submitPayment(){

        String amount = amount_et.getText().toString();
        String accountNumber = accountNumber_et.getText().toString();
        String transactionId = transaction_et.getText().toString();
        String comment = comment_et.getText().toString();
        if(amount.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error.."
                    ,"Enter Payment Amount");
            return;
        }
        if(accountNumber.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error.."
                    ,"Enter Account Number");
            return;
        }
        if(transactionId.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error.."
                    ,"Enter Transaction Id");
            return;
        }

        final android.app.AlertDialog dialog = new SpotsDialog(this);
        dialog.show();

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.submitPayment(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.getUserCatagory()
                ,biddaloyApplication.studentId
                ,amount,selectedPaymentSystem,accountNumber
                ,transactionId,comment).enqueue(new Callback<PaymentSubmitResponseModel>() {

            @Override
            public void onResponse(Call<PaymentSubmitResponseModel> call, Response<PaymentSubmitResponseModel> response) {
                if (response.isSuccessful()) {
                    if(response.body().getError() == 0){
                        dialog.dismiss();
                        transientDialog.showTransientDialogWithOutAction("Success"
                                ,"Payment Submitted Successfully");
                        CreatePayment.this.finish();
                    }else{
                        dialog.dismiss();
                        int statusCode = response.code();
                        transientDialog.showTransientDialogWithOutAction("Error"
                                ,"Payment Submit Error");
                    }

                } else {
                    dialog.dismiss();
                    int statusCode = response.code();
                    transientDialog.showTransientDialogWithOutAction("Error"
                            ,"Payment Submit Error");
                }
            }
            @Override
            public void onFailure(Call<PaymentSubmitResponseModel> call, Throwable t) {
                dialog.dismiss();
                transientDialog.showTransientDialogWithOutAction("Error"
                        ,"Payment Submit Error");
            }
        });

    }
}
