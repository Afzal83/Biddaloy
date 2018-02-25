package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 2/9/2018.
 */

public class AboutInstituteModel {

    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("institute_name")
    private String institute_name;

    @SerializedName("institute_logo")
    private String institute_logo;

    @SerializedName("institute_address")
    private String institute_address;

    @SerializedName("institute_phone")
    private String institute_phone;

    @SerializedName("institute_email")
    private String institute_email;

    @SerializedName("institute_website")
    private String institute_website;

    @SerializedName("head_of_institute")
    private String head_of_institute;

    @SerializedName("head_image")
    private String head_image;

    @SerializedName("about_us")
    private String about_us;

    @SerializedName("established")
    private String established;

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

    public String getInstitute_name() {
        return institute_name;
    }

    public void setInstitute_name(String institute_name) {
        this.institute_name = institute_name;
    }

    public String getInstitute_logo() {
        return institute_logo;
    }

    public void setInstitute_logo(String institute_logo) {
        this.institute_logo = institute_logo;
    }

    public String getInstitute_address() {
        return institute_address;
    }

    public void setInstitute_address(String institute_address) {
        this.institute_address = institute_address;
    }

    public String getInstitute_phone() {
        return institute_phone;
    }

    public void setInstitute_phone(String institute_phone) {
        this.institute_phone = institute_phone;
    }

    public String getInstitute_email() {
        return institute_email;
    }

    public void setInstitute_email(String institute_email) {
        this.institute_email = institute_email;
    }

    public String getInstitute_website() {
        return institute_website;
    }

    public void setInstitute_website(String institute_website) {
        this.institute_website = institute_website;
    }

    public String getHead_of_institute() {
        return head_of_institute;
    }

    public void setHead_of_institute(String head_of_institute) {
        this.head_of_institute = head_of_institute;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }
}
