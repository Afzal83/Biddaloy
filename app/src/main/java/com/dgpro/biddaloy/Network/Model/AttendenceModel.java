package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Babu on 1/13/2018.
 */

public class AttendenceModel {

    @SerializedName("student_id")
    private String student_id;

    @SerializedName("student_name")
    private String student_name;

    @SerializedName("absence")
    private String absence;

    @SerializedName("present")
    private String present;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("attendance")
    private List<AttendenceDataModel> attendance = null;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }

    public List<AttendenceDataModel> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AttendenceDataModel> attendance) {
        this.attendance = attendance;
    }
}
