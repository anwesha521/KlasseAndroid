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
    private int room_id;
    private  String id;

    public ChatMessage(String messageText, String messageUser,String type, int id, String i)
    {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
        messageType=type;
        room_id=id;
        this.id=i;
    }
    public ChatMessage()
    {

    }
    public void set_id(int i)
    {
        room_id=i;
    }
    public int get_id()
    {
        return room_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}