package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/30/2018.
 */

public class PaymentSystemDataModel {

    @SerializedName("Payment_system")
    private String Payment_system;

    @SerializedName("Account")
    private String Account;

    @SerializedName("Company")
    private String Company;

    @SerializedName("Account_Holder")
    private String Account_Holder;

    public String getPayment_system() {
        return Payment_system;
    }

    public void setPayment_system(String payment_system) {
        Payment_system = payment_system;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAccount_Holder() {
        return Account_Holder;
    }

    public void setAccount_Holder(String account_Holder) {
        Account_Holder = account_Holder;
    }
}
