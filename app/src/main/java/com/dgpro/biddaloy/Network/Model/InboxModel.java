package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/16/2018.
 */

public class InboxModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("inbox")
    private List<InboxDataModel> inbox = null;

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

    public List<InboxDataModel> getInbox() {
        return inbox;
    }

    public void setInbox(List<InboxDataModel> inbox) {
        this.inbox = inbox;
    }
}
