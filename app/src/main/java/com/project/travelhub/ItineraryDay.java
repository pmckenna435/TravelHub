package com.project.travelhub;



public class ItineraryDay {
    public int day;
    public int month;
    public int year;
    public int dayOfWeek;
    public String activity;

    public ItineraryDay(int day, int month, int year, int dayOfWeek) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfWeek = dayOfWeek;
        this.activity = "No Activity selected for today";
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public ItineraryDay(int day, int month, int year, int dayOfWeek, String activity) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfWeek = dayOfWeek;
        this.activity = activity;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getActivity() {
        return activity;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
