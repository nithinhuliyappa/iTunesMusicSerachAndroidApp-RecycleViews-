package com.example.itunesapimusicsearchusingrecyclerviews;

/*Assignment : Homework3
File : Music.java
Students : Nithin Huliyappa & Amit Pandit */

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Music implements Serializable,Comparable<Music>{

    String track, artist, genre, album, imageUrl, stringDate;
    double price, albumPrice;
    Date date;

    @Override
    public int compareTo(Music o) {
        return this.date.compareTo(o.date);

    }
}

class CompareByDate implements Comparator<Music>{

    @Override
    public int compare(Music o1, Music o2) {
        if (o1.price>o2.price){
            return 1;
        }else if (o1.price<o2.price){
            return -1;
        }else{
            return 0;
        }

    }
}