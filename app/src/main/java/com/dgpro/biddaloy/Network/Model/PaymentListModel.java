package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/28/2018.
 */

public class PaymentListModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("student_name")
    private String student_name;

    @SerializedName("payment_list")
    private List<PaymentDataModel> payment_list = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public List<PaymentDataModel> getPayment_list() {
        return payment_list;
    }

    public void setPayment_list(List<PaymentDataModel> payment_list) {
        this.payment_list = payment_list;
    }
}