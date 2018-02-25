package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/30/2018.
 */

public class PaymentSystemListModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("payment_info")
    private List<PaymentSystemDataModel> payment_info = null;

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

    public List<PaymentSystemDataModel> getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(List<PaymentSystemDataModel> payment_info) {
        this.payment_info = payment_info;
    }
}
