package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Custom adapter that uses custom data-type Earthquake
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.getDefault());
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return timeFormat.format(dateObject);
    }

    private String formatDecimal(double decimal){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(decimal);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get the Earthquake object
        Earthquake earthquake = getItem(position);
        //magnitude
        TextView magnitudeView = (TextView)listItemView.findViewById(R.id.magnitude);
        if(magnitudeView!=null) {
            magnitudeView.setText(formatDecimal(earthquake.getMagnitude()));
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);
        }
        //Divide the place into two parts
        String originalLocation = earthquake.getPlace();
        String locationOffset, primaryLocation;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        TextView locationOffsetView = (TextView)listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);
        TextView locationView = (TextView)listItemView.findViewById(R.id.primary_location);
        locationView.setText(primaryLocation);

        Date dateObject = new Date(earthquake.getTimeInMilliSeconds());

        //date
        TextView date = (TextView)listItemView.findViewById(R.id.date);
        if(date!=null)
            date.setText(formatDate(dateObject));

        //time
        TextView time = (TextView)listItemView.findViewById(R.id.time);
        if(time!=null){
            time.setText(formatTime(dateObject));
        }

        return listItemView;
    }
}
