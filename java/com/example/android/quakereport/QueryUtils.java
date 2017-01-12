package com.example.android.quakereport;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
    */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(QueryUtils.class.getSimpleName(), "Error with creating URL ", e);
        }
        return url;
    }

    /**
     *
     * @param url of the desired web api
     * @return  json response from the web api
     * @throws IOException
     * This function is used to connect to the api and make a GET request
     */
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url==null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //CONNECTION made
            //Check response code
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(QueryUtils.class.getSimpleName(), "Error response code: " + urlConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e(QueryUtils.class.getSimpleName(), "Problem reading data");
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }

        return jsonResponse;
    }

    /**
     *
     * @param inputStream from the @makeHttpRequest method
     * @return json response
     * @throws IOException
     * This function parses the input line by line and builds a json response
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Earthquake> extractEarthquakes(String requestUrl) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        //GET THE JSON RESPONSE
        URL r_url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(r_url);
        }catch (IOException e){
            Log.e(QueryUtils.class.getSimpleName(),"Error closing input stream");
        }

        try {

            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            for(int i=0;i<features.length();i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                Double mag = properties.optDouble("mag");
                String place = properties.optString("place");
                Long time = properties.optLong("time");
                String url = properties.optString("url");
                Earthquake earthquakeObj = new Earthquake(mag, place, time, url);
                earthquakes.add(earthquakeObj);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}