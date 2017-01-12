/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    /** USGC URL for making request */
    private static final String USGC_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        new EarthquakeAsyncClass().execute(USGC_URL);
    }

    private void notifyLoad(){
        Toast notify = Toast.makeText(this, "Fetching from USGC...", Toast.LENGTH_SHORT);
        notify.show();
    }

    private void updateUI(ArrayList<Earthquake> earthquakes){

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

        if(earthquakeListView!=null) {
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake currentEarthquake = adapter.getItem(position);
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                    Intent urlOpener = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                    startActivity(urlOpener);
                }
            });
        }
    }

    private class EarthquakeAsyncClass extends AsyncTask<String, Void, ArrayList<Earthquake>>{

        @Override
        protected void onPreExecute() {
            notifyLoad();
        }

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            //Create a list of earthquake data.
            return  QueryUtils.extractEarthquakes(urls[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> list) {
            updateUI(list);
        }
    }
}
