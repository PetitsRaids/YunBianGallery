package com.raids.gallery.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raids.gallery.model.database.datamodel.FavouriteHit;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    List<FavouriteHit> getAllFavourites();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavourite(FavouriteHit hit);

    @Delete()
    void removeFavourite(FavouriteHit hit);
}
