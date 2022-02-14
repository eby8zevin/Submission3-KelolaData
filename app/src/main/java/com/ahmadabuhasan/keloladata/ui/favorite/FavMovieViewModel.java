package com.ahmadabuhasan.keloladata.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;

public class FavMovieViewModel extends ViewModel {

    private AppRepository repository;

    public FavMovieViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<PagedList<MovieEntity>> getLikes() {
        return repository.getLikedMovies();
    }

    void setLike(MovieEntity movieEntity) {
        final boolean newState = !movieEntity.isLiked();
        repository.setMovieLike(movieEntity, newState);
    }
}