package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/*
 * Created by Babu on 1/17/2018.
 */

public class StudentDataModel {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("student_class")
    private String student_class;

    @SerializedName("image")
    private String image;

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

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
