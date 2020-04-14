package com.raids.gallery.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.raids.gallery.model.database.dao.FavouriteDao;
import com.raids.gallery.model.database.dao.HitDao;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.database.datamodel.Hit;

@Database(entities = {Hit.class, FavouriteHit.class}, version = 1, exportSchema = false)
@TypeConverters(value = {Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "AppDatabase";
    private static AppDatabase INSTANCE;
    private static final Object LOCK = new Object();
    private static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'hit' ADD COLUMN 'previewHeight' INTEGER NOT NULL DEFAULT 0");
        }
    };

    private static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'hit' ADD COLUMN 'previewWidth' INTEGER NOT NULL DEFAULT 0");
        }
    };

    private static Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE  'favourtie' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'hit')");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
//                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract HitDao getHitDao();

    public abstract FavouriteDao getFavouriteHit();
}
