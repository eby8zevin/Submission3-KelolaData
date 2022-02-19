package com.ahmadabuhasan.keloladata.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.di.Injection;
import com.ahmadabuhasan.keloladata.ui.detail.DetailMovieViewModel;
import com.ahmadabuhasan.keloladata.ui.detail.DetailTVShowViewModel;
import com.ahmadabuhasan.keloladata.ui.favorite.movie.FavoriteMovieViewModel;
import com.ahmadabuhasan.keloladata.ui.favorite.tvshow.FavoriteTVShowViewModel;
import com.ahmadabuhasan.keloladata.ui.movie.MovieViewModel;
import com.ahmadabuhasan.keloladata.ui.tvshow.TVShowViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private final AppRepository repository;

    private ViewModelFactory(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel(repository);
        } else if (modelClass.isAssignableFrom(DetailMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailMovieViewModel(repository);
        } else if (modelClass.isAssignableFrom(FavoriteMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteMovieViewModel(repository);
        } else if (modelClass.isAssignableFrom(TVShowViewModel.class)) {
            //noinspection unchecked
            return (T) new TVShowViewModel(repository);
        } else if (modelClass.isAssignableFrom(DetailTVShowViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailTVShowViewModel(repository);
        } else if (modelClass.isAssignableFrom(FavoriteTVShowViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteTVShowViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}