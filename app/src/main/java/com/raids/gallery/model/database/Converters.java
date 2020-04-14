package com.raids.gallery.model.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Date long2Date(long dataTimes) {
        return new Date(dataTimes);
    }

    @TypeConverter
    public long date2Long(Date date) {
        return date.getTime();
    }
}
