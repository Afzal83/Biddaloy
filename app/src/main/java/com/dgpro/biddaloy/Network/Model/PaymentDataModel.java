package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/28/2018.
 */

public class PaymentDataModel {

    @SerializedName("url")
    private String url;

    @SerializedName("paid_amount")
    private String paid_amount;

    @SerializedName("due_amount")
    private String due_amount;

    @SerializedName("date")
    private String date;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
