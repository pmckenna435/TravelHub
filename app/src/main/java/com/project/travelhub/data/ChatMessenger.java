package com.project.travelhub.data;

import java.util.ArrayList;

public class ChatMessenger {
    public String createdUser;
    public String nonCreatedUser;
    public ArrayList<Message> messages;

    public ChatMessenger() {
    }

    public ChatMessenger(String createdUser, String nonCreatedUser, ArrayList<Message> messages) {
        this.createdUser = createdUser;
        this.nonCreatedUser = nonCreatedUser;
        this.messages = messages;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getNonCreatedUser() {
        return nonCreatedUser;
    }

    public void setNonCreatedUser(String nonCreatedUser) {
        this.nonCreatedUser = nonCreatedUser;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
