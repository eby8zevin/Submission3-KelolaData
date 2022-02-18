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
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;

import java.util.List;

@Dao
public interface AppDao {

    // Movie
    @Query("SELECT * FROM movieTable")
    DataSource.Factory<Integer, MovieEntity> getMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Transaction
    @Query("SELECT * FROM movieTable WHERE movieId = :id")
    LiveData<MovieEmbedded> getMovieById(String id);

    @Query("SELECT * FROM movieTable WHERE liked = 1")
    DataSource.Factory<Integer, MovieEntity> getLikedMovie();

    @Update
    void updateMovie(MovieEntity movies);

    // TV Show
    @Query("SELECT * FROM tvShowTable")
    DataSource.Factory<Integer, TVShowEntity> getTVShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTVShows(List<TVShowEntity> tvShows);

    @Transaction
    @Query("SELECT * FROM tvShowTable WHERE tvShowId = :id")
    LiveData<TVShowEmbedded> getTVShowById(String id);

    @Query("SELECT * FROM tvShowTable WHERE liked = 1")
    DataSource.Factory<Integer, TVShowEntity> getLikedTVShow();

    @Update
    void updateTVShow(TVShowEntity tvShows);
}