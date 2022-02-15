package com.ahmadabuhasan.keloladata.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM movieTable")
    DataSource.Factory<Integer, MovieEntity> getMovies();

    @Query("SELECT * FROM movieTable WHERE liked = 1")
    DataSource.Factory<Integer, MovieEntity> getLikedMovie();

    @Transaction
    @Query("SELECT * FROM movieTable WHERE movieId = :id")
    LiveData<MovieEmbedded> getMovieById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Update
    void updateMovie(MovieEntity movies);
}