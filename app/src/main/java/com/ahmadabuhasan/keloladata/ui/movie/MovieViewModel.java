package com.ahmadabuhasan.keloladata.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public class MovieViewModel extends ViewModel {

    private final AppRepository repository;

    public MovieViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getMovies() {
        return repository.getAllMovies();
    }
}