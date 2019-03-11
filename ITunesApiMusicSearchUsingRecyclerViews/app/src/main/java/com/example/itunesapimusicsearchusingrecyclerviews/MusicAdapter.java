package com.example.itunesapimusicsearchusingrecyclerviews;

/*Assignment : Homework3
File : MusicAdapter.java
Students : Nithin Huliyappa & Amit Pandit */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    ArrayList<Music> mData;

    public MusicAdapter(ArrayList<Music> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.music_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Music music = mData.get(i);
        viewHolder.textViewTrack.setText("Track :"+music.track);
        viewHolder.textViewArtist.setText("Artist :"+music.artist);
        viewHolder.textViewPrice.setText("Price :"+String.valueOf(music.price)+" $");
        viewHolder.textViewDate.setText("Date :"+ new SimpleDateFormat("MM-dd-yyyy").format(music.date));
        viewHolder.music = music;
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewArtist, textViewTrack, textViewPrice, textViewDate;
        Music music;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewArtist = itemView.findViewById(R.id.artistText);
            textViewTrack = itemView.findViewById(R.id.trackText);
            textViewPrice = itemView.findViewById(R.id.priceText);
            textViewDate = itemView.findViewById(R.id.dateText);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context= itemView.getContext();
                    Intent i = new Intent(context,Main2Activity.class);
                    i.putExtra("iTune",  music);
                    context.startActivity(i);
                    Log.d("demo", "Clicked on "+music.track);
                }
            });
        }
    }


}

