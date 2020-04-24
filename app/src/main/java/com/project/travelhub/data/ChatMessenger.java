package com.project.travelhub.data;

import java.util.ArrayList;

public class ChatMessenger {
    private String createdUser;
    private String nonCreatedUser;
    private String dCity;
    private boolean ratedUser;
    private ArrayList<Message> messages;

    public ChatMessenger() {
    }

    //public ChatMessenger(String createdUser, String nonCreatedUser, ArrayList<Message> messages) {
      //  this.createdUser = createdUser;
       // this.nonCreatedUser = nonCreatedUser;
       // this.messages = messages;

   // }

    public ChatMessenger(String city, String createdUser, String nonCreatedUser, ArrayList<Message> messages) {
        this.createdUser = createdUser;
        this.nonCreatedUser = nonCreatedUser;
        this.messages = messages;
        this.dCity = city;
        this.ratedUser = false;
    }


    public boolean isRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(boolean ratedUser) {
        this.ratedUser = ratedUser;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdCity() {
        return dCity;
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
