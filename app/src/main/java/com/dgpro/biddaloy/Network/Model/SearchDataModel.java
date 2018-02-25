package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Babu on 1/14/2018.
 */

@Parcel
public class SearchDataModel {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("student_class")
    private String student_class;

    @SerializedName("session_year")
    private String session_year;

    @SerializedName("department")
    private String department;

    @SerializedName("image")
    private String image;

    @SerializedName("category")
    private String category;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSession_year() {
        return session_year;
    }

    public void setSession_year(String session_year) {
        this.session_year = session_year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
