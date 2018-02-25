package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/17/2018.
 */

public class StudentListModel {

    @SerializedName("search_found")
    private String search_found;

    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("search_result")
    private List<StudentDataModel> search_result = null;

    public String getSearch_found() {
        return search_found;
    }

    public void setSearch_found(String search_found) {
        this.search_found = search_found;
    }

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

    public List<StudentDataModel> getSearch_result() {
        return search_result;
    }

    public void setSearch_result(List<StudentDataModel> search_result) {
        this.search_result = search_result;
    }
}
