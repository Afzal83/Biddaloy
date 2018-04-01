package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 3/28/2018.
 */

public class LibraryListModel {

    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("book_found")
    private int book_found;

    @SerializedName("book_name")
    private List<LibraryModel> book_name = null;

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

    public int getBook_found() {
        return book_found;
    }

    public void setBook_found(int book_found) {
        this.book_found = book_found;
    }

    public List<LibraryModel> getBook_name() {
        return book_name;
    }

    public void setBook_name(List<LibraryModel> book_name) {
        this.book_name = book_name;
    }
}
