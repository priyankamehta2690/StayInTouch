package com.priyankamehta.hw8;

/**
 * Created by ADMIN on 4/17/2016.
 */
public class Message {

    String Sender,Receiver,message_text, message_read, TimeStamp;

    Message(){

    }
    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getText() {
        return message_text;
    }

    public void setText(String text) {
        this.message_text = text;
    }

    public String getRead() {
        return message_read;
    }

    public void setRead(String read) {
        this.message_read = read;
    }

    public String getTimestamp() {
        return TimeStamp;
    }

    public void setTimestamp(String timestamp) {
        this.TimeStamp = timestamp;
    }
}
