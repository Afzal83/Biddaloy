package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogCatagoryList {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("blog_category")
    private List<BlogCatagoryDataModel> blog_category = null;

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

    public List<BlogCatagoryDataModel> getBlog_category() {
        return blog_category;
    }

    public void setBlog_category(List<BlogCatagoryDataModel> blog_category) {
        this.blog_category = blog_category;
    }
}
