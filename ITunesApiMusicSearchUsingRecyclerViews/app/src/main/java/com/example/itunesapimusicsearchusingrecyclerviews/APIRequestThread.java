package com.example.itunesapimusicsearchusingrecyclerviews;

/*Assignment : Homework3
File : APIRequestThread.java
Students : Nithin Huliyappa & Amit Pandit */

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

interface DataHandler{
    public void fetchAndDisplayContent(ArrayList<Music> dhMusics);
}

public class APIRequestThread extends AsyncTask<String, Void, ArrayList<Music>> {
    DataHandler dataHandler;
    public APIRequestThread(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    protected ArrayList<Music> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        ArrayList<Music> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        URL url = null;
        try {
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray searchResults = root.getJSONArray("results");
                for (int i=0;i<searchResults.length(); i++){
                    JSONObject musicJson = searchResults.getJSONObject(i);
                    Music music = new Music();
                    music.track = musicJson.getString("trackName");
                    music.artist = musicJson.getString("artistName");
                    music.price = musicJson.getDouble("trackPrice");
                    String date = musicJson.getString("releaseDate");
                    String[] dateArray = date.split("T");
                    Log.d("demo", "date is :"+dateArray[0]);
                    music.stringDate = dateArray[0];
                    music.date = dateFormat.parse(dateArray[0]);
                    Log.d("demo", "formatedDate :" +music.date);
                    music.genre= musicJson.getString("primaryGenreName");
                    music.album= musicJson.getString("collectionName");
                    music.imageUrl= musicJson.getString("artworkUrl100");
                    music.albumPrice= musicJson.getDouble("collectionPrice");
                    result.add(music);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Music> music) {
        super.onPostExecute(music);
        dataHandler.fetchAndDisplayContent(music);
    }
}
