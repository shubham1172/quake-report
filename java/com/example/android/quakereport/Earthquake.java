package com.example.android.quakereport;


/**
 * Custom data type for earthquake activities
 */
public class Earthquake {
    private double magnitude; //Eg 5.5 or 7.1
    private String place; //Eg New Delhi or Mumbai
    private long timeInMilliSeconds; //Eg Jan 10, 2017

    public Earthquake(double magnitude, String place, long timeInMilliSeconds){
        this.magnitude = magnitude;
        this.place = place;
        this.timeInMilliSeconds = timeInMilliSeconds;
    }

    public String getMagnitude(){
        return Double.toString(magnitude);
    }
    public String getPlace(){
        return place;
    }
    public long getTimeInMilliSeconds(){
        return timeInMilliSeconds;
    }
}
