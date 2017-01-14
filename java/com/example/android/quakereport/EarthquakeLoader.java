package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import java.util.ArrayList;

/**
 * EarthquakeLoader class is a custom Loader class for fetching earthquake details into our view.
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private String url;
    public EarthquakeLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if(url==null)
            return null;
        //Create a list of earthquake data.
        return QueryUtils.extractEarthquakes(url);
    }
}
