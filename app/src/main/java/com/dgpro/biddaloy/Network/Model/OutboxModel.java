package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/16/2018.
 */

public class OutboxModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("outbox")
    private List<OutboxDataModel> outbox = null;

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

    public List<OutboxDataModel> getOutbox() {
        return outbox;
    }

    public void setOutbox(List<OutboxDataModel> outbox) {
        this.outbox = outbox;
    }
}
