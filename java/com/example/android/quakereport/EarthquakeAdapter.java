package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
        TextView magnitude = (TextView)listItemView.findViewById(R.id.magnitude);
        if(magnitude!=null)
            magnitude.setText(earthquake.getMagnitude());

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
        TextView locationOffsetView = (TextView)listItemView.findViewById(R.id.locationOffset);
        locationOffsetView.setText(locationOffset);
        TextView locationView = (TextView)listItemView.findViewById(R.id.location);
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
