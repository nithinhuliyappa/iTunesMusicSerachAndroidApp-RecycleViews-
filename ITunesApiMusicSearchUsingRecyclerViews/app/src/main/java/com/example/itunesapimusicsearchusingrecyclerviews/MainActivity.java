package com.example.itunesapimusicsearchusingrecyclerviews;

/*Assignment : Homework3
File : MainActivity.java
Students : Nithin Huliyappa & Amit Pandit */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DataHandler {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<Music> musics = new ArrayList<>();

    ToggleButton toggleButton;

    int searchLimit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Music Search");
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        //SeekBar progress
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                searchLimit = progress+10;
                TextView textView = findViewById(R.id.limitText);
                textView.setText("Limit :"+searchLimit);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){
                    EditText searchBar = findViewById(R.id.searchBar);
                    String serachKeys = searchBar.getText().toString();
                    String urlStr = "https://itunes.apple.com/search?term="+serachKeys+"&limit="+searchLimit;
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    toggleButton.setChecked(false);
                    new APIRequestThread(MainActivity.this).execute(urlStr);
                }else{
                    Toast.makeText(MainActivity.this, "Internet not present", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.searchBar);
                SeekBar seekBar = findViewById(R.id.seekBar);

                textView.setText("");
                seekBar.setProgress(0);
                toggleButton.setChecked(false);

            }
        });

        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (musics.size()!=0){
                    if (isChecked){
                        CompareByDate compareByDate = new CompareByDate();
                        Collections.sort(musics,compareByDate);
                        mAdapter.notifyDataSetChanged();
                        Log.d("demo","Sorted Based on Price");
                    }else{
                        Log.d("demo","Sorted Based on Date");
                        Collections.sort(musics);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No Results Available! Search Music First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void fetchAndDisplayContent(final ArrayList<Music> dhMusics) {

        //Re-Cycler-View Setup
        musics = dhMusics;
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Collections.sort(musics);
        mAdapter = new MusicAdapter(musics);
        recyclerView.setAdapter(mAdapter);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

    }
}
