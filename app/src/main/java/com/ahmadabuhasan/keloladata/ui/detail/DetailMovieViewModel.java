package com.ahmadabuhasan.keloladata.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public class DetailMovieViewModel extends ViewModel {

    private final MutableLiveData<String> movieId = new MutableLiveData<>();
    private AppRepository repository;

    public DetailMovieViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<Resource<MovieEmbedded>> movieEmbedded = Transformations.switchMap(movieId,
            mMoviesId -> repository.getDetailMovie(mMoviesId));

    public String getMovieId() {
        return movieId.getValue();
    }

    public void setMovieId(String movieId) {
        this.movieId.setValue(movieId);
    }

    void setLike() {
        Resource<MovieEmbedded> moduleResource = movieEmbedded.getValue();
        if (moduleResource != null) {
            MovieEmbedded movieEmbedded = moduleResource.data;

            if (movieEmbedded != null) {
                MovieEntity movieEntity = movieEmbedded.mMovies;

                final boolean newState = !movieEntity.isLiked();
                repository.setMovieLike(movieEntity, newState);
            }
        }
    }
}