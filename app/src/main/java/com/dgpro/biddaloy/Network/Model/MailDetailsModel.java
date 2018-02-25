package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Babu on 1/25/2018.
 */

public class MailDetailsModel {

    @SerializedName("error")
    private String error;

    @SerializedName("error_report")
    private String error_report;

    @SerializedName("mail_id")
    private String mail_id;

    @SerializedName("sender_id")
    private String sender_id;

    @SerializedName("receiver_id")
    private String receiver_id;

    @SerializedName("sender_category")
    private String sender_category;

    @SerializedName("receiver_category")
    private String receiver_category;

    @SerializedName("sender_name")
    private String sender_name;

    @SerializedName("sending_time")
    private String sending_time;

    @SerializedName("mail_read")
    private String mail_read;

    @SerializedName("subject")
    private String subject;

    @SerializedName("message")
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSender_category() {
        return sender_category;
    }

    public void setSender_category(String sender_category) {
        this.sender_category = sender_category;
    }

    public String getReceiver_category() {
        return receiver_category;
    }

    public void setReceiver_category(String receiver_category) {
        this.receiver_category = receiver_category;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSending_time() {
        return sending_time;
    }

    public void setSending_time(String sending_time) {
        this.sending_time = sending_time;
    }

    public String getMail_read() {
        return mail_read;
    }

    public void setMail_read(String mail_read) {
        this.mail_read = mail_read;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
