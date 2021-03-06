package com.ahmadabuhasan.keloladata.ui.favorite.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;

public class FavoriteMovieViewModel extends ViewModel {

    private final AppRepository repository;

    public FavoriteMovieViewModel(AppRepository mRepository) {
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