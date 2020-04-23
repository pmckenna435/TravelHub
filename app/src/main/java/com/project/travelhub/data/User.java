package com.project.travelhub.data;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String email;
    public String username;
    public List cities_visited = new ArrayList();
    public List user_chats = new ArrayList();
    public  List user_trips = new ArrayList();
    public double rating_total;
    public int number_of_ratings;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCities_visited(List cities_visited) {
        this.cities_visited = cities_visited;
    }

    public String getEmail() {
        return email;
    }

    public List getCities_visited() {
        return cities_visited;
    }

    public User(){

    }

    public User(String email, List cities_visited, String username){
        this.username = username;
        this.email = email;
        this.cities_visited = cities_visited;
        user_chats.add("User has no Chats");
        user_trips.add("user has no trips");
        rating_total = 0;
        number_of_ratings = 0;


    }


}
