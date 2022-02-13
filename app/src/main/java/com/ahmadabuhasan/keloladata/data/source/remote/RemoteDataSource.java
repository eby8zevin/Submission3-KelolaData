package com.ahmadabuhasan.keloladata.data.source.remote;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmadabuhasan.keloladata.data.source.remote.response.MovieResponse;
import com.ahmadabuhasan.keloladata.data.source.remote.response.TVShowResponse;
import com.ahmadabuhasan.keloladata.utils.EspressoIdlingResource;
import com.ahmadabuhasan.keloladata.utils.JsonHelper;

import java.util.List;

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private final JsonHelper jsonHelper;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final long LOADING = 3000;

    private RemoteDataSource(JsonHelper mJsonHelper) {
        this.jsonHelper = mJsonHelper;
    }

    public static RemoteDataSource getInstance(JsonHelper nJsonHelper) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(nJsonHelper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<MovieResponse>>> getAllMovies() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<MovieResponse>>> resultMovie = new MutableLiveData<>();
        handler.postDelayed(() -> {
            resultMovie.setValue(ApiResponse.success(jsonHelper.loadMovies()));
            EspressoIdlingResource.decrement();
        }, LOADING);
        return resultMovie;
    }

    public LiveData<ApiResponse<List<TVShowResponse>>> getAllTVShows() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<TVShowResponse>>> resultTVShow = new MutableLiveData<>();
        handler.postDelayed(() -> {
            resultTVShow.setValue(ApiResponse.success(jsonHelper.loadTVShow()));
        }, LOADING);
        return resultTVShow;
    }
}