package com.dgpro.biddaloy.Model;

import org.parceler.Parcel;

/**
 * Created by Babu on 2/16/2018.
 */

@Parcel
public class NewMessageModel {

    String senderId = "";
    String senderName = "";
    String senderCategory = "";
    String receiverId = "";
    String receiverName = "";
    String receiverCatagory = "";
    String subject = "";
    String messageBody = "";

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderCategory() {
        return senderCategory;
    }

    public void setSenderCategory(String senderCategory) {
        this.senderCategory = senderCategory;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCatagory() {
        return receiverCatagory;
    }

    public void setReceiverCatagory(String receiverCatagory) {
        this.receiverCatagory = receiverCatagory;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
