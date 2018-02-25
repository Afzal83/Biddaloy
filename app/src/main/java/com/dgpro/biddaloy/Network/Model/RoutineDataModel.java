package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/25/2018.
 */

public class RoutineDataModel {

    @SerializedName("url")
    private String url;

    @SerializedName("routine_name")
    private String routine_name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoutine_name() {
        return routine_name;
    }

    public void setRoutine_name(String routine_name) {
        this.routine_name = routine_name;
    }
}
