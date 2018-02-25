package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Babu on 1/5/2018.
 */

@Parcel
public class LoginDataModel {
    @SerializedName("error")
    private int error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("user_catagory")
    private String user_catagory;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("address")
    private String address;

    @SerializedName("image_url")
    private String image_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_catagory() {
        return user_catagory;
    }

    public void setUser_catagory(String user_catagory) {
        this.user_catagory = user_catagory;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
}
