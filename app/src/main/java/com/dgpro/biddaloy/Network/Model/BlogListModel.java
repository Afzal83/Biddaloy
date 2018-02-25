package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogListModel {

    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("blog_details")
    private List<BlogDataModel> blog_details = null;

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

    public List<BlogDataModel> getBlog_details() {
        return blog_details;
    }

    public void setBlog_details(List<BlogDataModel> blog_details) {
        this.blog_details = blog_details;
    }
}
