package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogDataModel {

    @SerializedName("url")
    private String url;

    @SerializedName("blog_details")
    private String blog_details;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBlog_details() {
        return blog_details;
    }

    public void setBlog_details(String blog_details) {
        this.blog_details = blog_details;
    }
}
