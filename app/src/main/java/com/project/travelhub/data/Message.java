package com.project.travelhub.data;

public class Message {
    public String sender;
    public String text;

    public Message() {
        this.sender = "No sender";
        this.text = "No Message";
    }

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
