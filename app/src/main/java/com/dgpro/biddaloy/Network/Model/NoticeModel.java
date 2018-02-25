package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/14/2018.
 */

public class NoticeModel {
    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("notice")
    private List<NoticeDataModel> notice = null;

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

    public List<NoticeDataModel> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeDataModel> notice) {
        this.notice = notice;
    }
}
