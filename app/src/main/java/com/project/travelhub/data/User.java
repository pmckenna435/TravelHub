package com.project.travelhub.data;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String email;
    public String username;
    public List cities_visited = new ArrayList();
    public List user_chats = new ArrayList();
    public  List user_trips = new ArrayList();
    public String rating_total;
    public String number_of_ratings;

    public String getRating_total() {
        return rating_total;
    }

    public String getNumber_of_ratings() {
        return number_of_ratings;
    }

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
        rating_total = "0";
        number_of_ratings = "0";


    }

    public User(String email, List cities_visited, String username , String rating_total, String number_of_ratings){
        this.username = username;
        this.email = email;
        this.cities_visited = cities_visited;
        user_chats.add("User has no Chats");
        user_trips.add("user has no trips");
        this.rating_total = rating_total;
        this.number_of_ratings = number_of_ratings;


    }


}
