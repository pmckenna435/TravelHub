package com.project.travelhub;



import com.project.travelhub.data.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {
    String tripName;
    List cities;
    List users = new ArrayList();
    String startDate;
    String endDate;
    List<ItineraryDay> itinerary = new ArrayList<ItineraryDay>();
    public ArrayList<Message> messages = new ArrayList<Message>();

    public Trip(String tripName, List cities, List users, String startDate, String endDate, List<ItineraryDay> itinerary) {
        this.tripName = tripName;
        this.cities = cities;
        this.users = users;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itinerary = itinerary;
        Message test = new Message();
        this.messages.add(test);
    }


    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public List getCities() {
        return cities;
    }

    public void setCities(List cities) {
        this.cities = cities;
    }

    public List getUsers() {
        return users;
    }

    public void setUsers(List users) {
        this.users = users;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<ItineraryDay> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<ItineraryDay> itinerary) {
        this.itinerary = itinerary;
    }
}
