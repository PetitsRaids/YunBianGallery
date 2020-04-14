package com.raids.gallery.model.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raids.gallery.model.database.datamodel.Hit;

import java.util.List;

@Dao
public interface HitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Hit> hits);

    @Query("SELECT * FROM Hit")
    public List<Hit> getAllHits();
}
