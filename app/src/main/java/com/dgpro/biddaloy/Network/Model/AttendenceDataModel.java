package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/13/2018.
 */

public class AttendenceDataModel {

    @SerializedName("intime")
    private String intime;

    @SerializedName("outtime")
    private String outtime;

    @SerializedName("comment")
    private String comment;

    @SerializedName("date")
    private String date;

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
