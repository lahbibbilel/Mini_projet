package com.example.mini_projet;

public class MessagesModel {


    private String MsgId;
    private String senderId;
    private String message;

    public MessagesModel(String msgId, String senderId, String message) {
        MsgId = msgId;
        this.senderId = senderId;
        this.message = message;
    }

    public MessagesModel() {
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}