package com.ahmadabuhasan.keloladata.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;

@Database(entities = {MovieEntity.class, TVShowEntity.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "MovieTVShow.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}