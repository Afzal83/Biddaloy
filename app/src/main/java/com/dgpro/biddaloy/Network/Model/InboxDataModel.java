package com.dgpro.biddaloy.Network.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Babu on 1/16/2018.
 */

@Parcel
public class InboxDataModel  {

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

    @SerializedName("subject")
    private String subject;

    @SerializedName("sender_name")
    private String sender_name;

    @SerializedName("sender_image")
    private String sender_image;

    @SerializedName("time")
    private String time;

    @SerializedName("read")
    private String read;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }
}
