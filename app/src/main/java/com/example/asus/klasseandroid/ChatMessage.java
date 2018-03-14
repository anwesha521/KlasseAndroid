package com.example.asus.klasseandroid;

import android.util.Log;

import java.util.Date;

/**
 * Created by ASUS on 17-02-2018.
 */

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String messageType;
    private String question;
    private boolean verified;

    public ChatMessage(String messageText, String messageUser,String type) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
        messageType=type;



    }
    public boolean getVerified()
    {
        return verified;
    }
    public void setVerified()
    {
        Log.i("anwesha","verifying");
        verified=true;
    }

    public ChatMessage(){

    }
    public String getMessageType()
    {
        return messageType;
    }

    public String getMessageText() {
        return messageText;
    }
    public String getQuestion()
    {
        return this.question;
    }

    public void setQuestion(String q)
    {
        question=q+": ";
        Log.i("anwesha",question);
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;

    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}