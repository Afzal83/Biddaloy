package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 3/28/2018.
 */

public class LibraryModel {

    @SerializedName("url")
    private String url;

    @SerializedName("book_name")
    private String book_name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }
}
