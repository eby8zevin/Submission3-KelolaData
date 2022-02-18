package com.ahmadabuhasan.keloladata.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public interface AppDataSource {

    // Movie
    LiveData<Resource<PagedList<MovieEntity>>> getAllMovies();

    LiveData<Resource<MovieEmbedded>> getDetailMovie(String movieId);

    LiveData<PagedList<MovieEntity>> getLikedMovies();

    void setMovieLike(MovieEntity movie, boolean state);

    // TV Show
    LiveData<Resource<PagedList<TVShowEntity>>> getAllTVShows();

    //LiveData<Resource<TVShowEmbedded>> getDetailTVShow(String tvShowId);

    //LiveData<PagedList<TVShowEntity>> getLikedTVShows();

    //void setTVShowLike(TVShowEntity tvShow, boolean state);
}