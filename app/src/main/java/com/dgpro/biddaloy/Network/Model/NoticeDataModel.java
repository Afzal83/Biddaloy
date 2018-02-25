package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/*
 *
 *
 *
 * Created by Babu on 1/14/2018.
 *
 *
 */

public class NoticeDataModel {

    @SerializedName("notice_id")
    private String notice_id;

    @SerializedName("student_id")
    private String student_id;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("notice")
    private String notice;

    @SerializedName("writer")
    private String writer;

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
