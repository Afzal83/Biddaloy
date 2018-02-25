package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/30/2018.
 */

public class PaymentSubmitResponseModel {

    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }
}
