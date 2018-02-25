package com.dgpro.biddaloy.Model;

/**
 * Created by Babu on 2/14/2018.
 */

public class LoginModel {
    Boolean isLoggedin = false;
    String userName = "";
    String userPass = "";
    String userCategory = "";
    String userImage = "";
    String baseUrl = "";
    String userPhone = "";
    String userAddress = "";

    public Boolean getLoggedin() {
        return isLoggedin;
    }

    public void setLoggedin(Boolean loggedin) {
        isLoggedin = loggedin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
