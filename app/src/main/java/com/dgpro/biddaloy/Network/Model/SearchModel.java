package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/14/2018.
 */

public class SearchModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("search_found")
    private String search_found;

    @SerializedName("search_result")
    private List<SearchDataModel> search_result = null;

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

    public String getSearch_found() {
        return search_found;
    }

    public void setSearch_found(String search_found) {
        this.search_found = search_found;
    }

    public List<SearchDataModel> getSearch_result() {
        return search_result;
    }

    public void setSearch_result(List<SearchDataModel> search_result) {
        this.search_result = search_result;
    }
}
