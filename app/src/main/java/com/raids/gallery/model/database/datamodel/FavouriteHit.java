package com.raids.gallery.model.database.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favourites")
public class FavouriteHit {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded(prefix = "hit")
    private Hit hit;

    @ColumnInfo(name = "favourite_time")
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hit getHit() {
        return hit;
    }

    public void setHit(Hit hit) {
        this.hit = hit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
