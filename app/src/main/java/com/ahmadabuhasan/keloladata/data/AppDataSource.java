package com.ahmadabuhasan.keloladata.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public interface AppDataSource {

    LiveData<Resource<PagedList<MovieEntity>>> getAllMovies();

    LiveData<Resource<MovieEmbedded>> getDetailMovie(String movieId);
}