package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogCatagoryDataModel {

    @SerializedName("blog_category")
    private String blog_category;

    public String getBlog_category() {
        return blog_category;
    }

    public void setBlog_category(String blog_category) {
        this.blog_category = blog_category;
    }
}
