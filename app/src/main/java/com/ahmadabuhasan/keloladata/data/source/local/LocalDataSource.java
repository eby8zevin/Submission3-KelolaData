package com.ahmadabuhasan.keloladata.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

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

    public LiveData<MovieEntity> getMoviesById(String movieId) {
        return appDao.getMovieById(movieId);
    }

    public void insertMovies(List<MovieEntity> movies) {
        appDao.insertMovies(movies);
    }

    public void updateMovies(MovieEntity movies) {
        appDao.updateMovies(movies);
    }
}