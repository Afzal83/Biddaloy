package com.dgpro.biddaloy.application;

import android.app.Application;

import com.dgpro.biddaloy.Network.Model.AttendenceDataModel;
import com.dgpro.biddaloy.Network.Model.BlogDataModel;
import com.dgpro.biddaloy.Network.Model.DiaryDataModel;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.Network.Model.PaymentDataModel;
import com.dgpro.biddaloy.Network.Model.ResultDataModel;
import com.dgpro.biddaloy.Network.Model.RoutineDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Babu on 1/2/2018.
 */

public class BiddaloyApplication extends Application {

        public  boolean isUserLoggedIn ;

        public String baseUrl = "";
        public String schoolName = "";
        public String userName = "";

        public String password = "";
        public String userCatagory = "" ;

        public String name = "";
        public String userPhone = "";
        public String userEmail = "";
        public String dateOfbirth  = "";
        public String bloodGroup  = "";
        public String usreImageUrl = "" ;
        public String userAddress = "";

        public List<AttendenceDataModel> attendentList = new ArrayList<>();
        public List<RoutineDataModel> routineList = new ArrayList<>();
        public List<ResultDataModel> resultList = new ArrayList<>();
        public List<DiaryDataModel> diaryList = new ArrayList<>();
//        public List<InboxDataModel> inboxList = new ArrayList<>();
//        public List<OutboxDataModel> outBoxList = new ArrayList<>();
        public List<BlogDataModel> blogList = new ArrayList<>();
        public List<PaymentDataModel> paymentList = new ArrayList<>() ;

        public String studentName = "";
        public String studentRoll = "";
        public String stuedentClass = "";
        public String studentImage = "";
        public String studentId = "";
        public String studentDue = "";
        public String studentPresentDay = "";
        public String studentAbsentDay = "";

        public boolean isUserLoggedIn() {
                return isUserLoggedIn;
        }

        public void setUserLoggedIn(boolean userLoggedIn) {
                isUserLoggedIn = userLoggedIn;
        }

        public String getBaseUrl() {
                return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getUserCatagory() {
                return userCatagory;
        }

        public void setUserCatagory(String userCatagory) {
                this.userCatagory = userCatagory;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getUserPhone() {
                return userPhone;
        }

        public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
        }

        public String getUserEmail() {
                return userEmail;
        }

        public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
        }

        public String getDateOfbirth() {
                return dateOfbirth;
        }

        public void setDateOfbirth(String dateOfbirth) {
                this.dateOfbirth = dateOfbirth;
        }

        public String getBloodGroup() {
                return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
                this.bloodGroup = bloodGroup;
        }

        public String getUsreImageUrl() {
                return usreImageUrl;
        }

        public void setUsreImageUrl(String usreImageUrl) {
                this.usreImageUrl = usreImageUrl;
        }

        public String getUserAddress() {
                return userAddress;
        }

        public void setUserAddress(String userAddress) {
                this.userAddress = userAddress;
        }
}
