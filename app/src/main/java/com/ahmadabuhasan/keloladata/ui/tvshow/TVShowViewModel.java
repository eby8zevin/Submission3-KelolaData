package com.ahmadabuhasan.keloladata.ui.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public class TVShowViewModel extends ViewModel {

    private final AppRepository repository;

    public TVShowViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<Resource<PagedList<TVShowEntity>>> getTVShows() {
        return repository.getAllTVShows();
    }
}