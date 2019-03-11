package com.example.itunesapimusicsearchusingrecyclerviews;

/*Assignment : Homework3
File : Main2Activity.java
Students : Nithin Huliyappa & Amit Pandit */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("iTunes Music Search");

        ImageView imageView = findViewById(R.id.imageView);
        TextView track = findViewById(R.id.m2TrackText);
        TextView genre = findViewById(R.id.m2GenreText);
        TextView artist = findViewById(R.id.m2ArtistText);
        TextView album = findViewById(R.id.m2AlbumText);
        TextView trackPrice = findViewById(R.id.m2TrackPriceText);
        TextView albumPrice = findViewById(R.id.m2AlbumPrice);

        if (getIntent()!=null && getIntent().getExtras()!=null){

            Music selectedmusic = (Music) getIntent().getExtras().get("iTune");
            Picasso.get().load(selectedmusic.imageUrl).into(imageView);
            track.setText("Track :"+selectedmusic.track);
            genre.setText("Genre :"+selectedmusic.genre);
            artist.setText("Artist :"+selectedmusic.artist);
            album.setText("Album :"+selectedmusic.album);
            trackPrice.setText("Track Price :"+selectedmusic.price+" $");
            albumPrice.setText("Album Price :"+selectedmusic.albumPrice+" $");

        }

        findViewById(R.id.m2FinishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
