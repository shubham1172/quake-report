package com.example.android.quakereport;


/**
 * Custom data type for earthquake activities
 */
public class Earthquake {
    private double magnitude; //Eg 5.5 or 7.1
    private String place; //Eg New Delhi or Mumbai
    private long timeInMilliSeconds; //Eg Jan 10, 2017
    private String url;

    public Earthquake(double magnitude, String place, long timeInMilliSeconds, String url){
        this.magnitude = magnitude;
        this.place = place;
        this.timeInMilliSeconds = timeInMilliSeconds;
        this.url = url;
    }

    public double getMagnitude(){
        return magnitude;
    }
    public String getPlace(){
        return place;
    }
    public long getTimeInMilliSeconds(){
        return timeInMilliSeconds;
    }
    public String getUrl() { return url; }
}
