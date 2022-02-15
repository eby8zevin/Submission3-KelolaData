package com.ahmadabuhasan.keloladata.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.data.source.local.room.AppDao;

import java.util.List;

public class LocalDataSource {

    private static LocalDataSource INSTANCE;
    private final AppDao appDao;

    private LocalDataSource(AppDao mAppDao) {
        this.appDao = mAppDao;
    }

    public static LocalDataSource getInstance(AppDao nAppDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(nAppDao);
        }
        return INSTANCE;
    }

    public DataSource.Factory<Integer, MovieEntity> getAllMovies() {
        return appDao.getMovies();
    }

    public LiveData<MovieEmbedded> getMoviesById(String movieId) {
        return appDao.getMovieById(movieId);
    }

    public DataSource.Factory<Integer, MovieEntity> getLikedMovies() {
        return appDao.getLikedMovie();
    }

    public void setMovieLike(MovieEntity movie, boolean newState) {
        movie.setLiked(newState);
        appDao.updateMovie(movie);
    }

    public void insertMovies(List<MovieEntity> movies) {
        appDao.insertMovies(movies);
    }
}