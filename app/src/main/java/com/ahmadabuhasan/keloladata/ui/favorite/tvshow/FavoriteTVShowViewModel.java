package com.ahmadabuhasan.keloladata.ui.favorite.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;

public class FavoriteTVShowViewModel extends ViewModel {

    private final AppRepository repository;

    public FavoriteTVShowViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<PagedList<TVShowEntity>> getLikes() {
        return repository.getLikedTVShows();
    }

    void setLike(TVShowEntity tvShowEntity) {
        final boolean newState = !tvShowEntity.isLiked();
        repository.setTVShowLike(tvShowEntity, newState);
    }
}