package com.ahmadabuhasan.keloladata.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.source.local.LocalDataSource;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.data.source.remote.ApiResponse;
import com.ahmadabuhasan.keloladata.data.source.remote.RemoteDataSource;
import com.ahmadabuhasan.keloladata.data.source.remote.response.MovieResponse;
import com.ahmadabuhasan.keloladata.data.source.remote.response.TVShowResponse;
import com.ahmadabuhasan.keloladata.utils.AppExecutors;
import com.ahmadabuhasan.keloladata.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class AppRepository implements AppDataSource {

    private volatile static AppRepository INSTANCE = null;

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    private AppRepository(@NonNull RemoteDataSource remoteDataSource1, @NonNull LocalDataSource localDataSource1, AppExecutors appExecutors1) {
        this.remoteDataSource = remoteDataSource1;
        this.localDataSource = localDataSource1;
        this.appExecutors = appExecutors1;
    }

    public static AppRepository getInstance(RemoteDataSource remoteDataSource2, LocalDataSource localDataSource2, AppExecutors appExecutors2) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                INSTANCE = new AppRepository(remoteDataSource2, localDataSource2, appExecutors2);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getAllMovies() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public LiveData<PagedList<MovieEntity>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(3)
                        .setPageSize(3)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getAllMovies(), config).build();
            }

            @Override
            public Boolean shouldFetch(PagedList<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            public LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return remoteDataSource.getAllMovies();
            }

            @Override
            public void saveCallResult(List<MovieResponse> movieResponses) {
                ArrayList<MovieEntity> movieList = new ArrayList<>();
                for (MovieResponse response : movieResponses) {
                    MovieEntity movie = new MovieEntity(
                            response.getMovieId(),
                            response.getOverview(),
                            response.getPosterPath(),
                            response.getReleaseDate(),
                            response.getTitle(),
                            response.getVoteAverage(),
                            false);
                    movieList.add(movie);
                }
                localDataSource.insertMovies(movieList);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieEmbedded>> getDetailMovie(String movieId) {
        return new NetworkBoundResource<MovieEmbedded, List<MovieResponse>>(appExecutors) {
            @Override
            protected LiveData<MovieEmbedded> loadFromDB() {
                return localDataSource.getMoviesById(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEmbedded embedded) {
                return (embedded == null);
            }

            @Override
            protected LiveData<ApiResponse<List<MovieResponse>>> createCall() {
                return remoteDataSource.getAllMovies();
            }

            @Override
            protected void saveCallResult(List<MovieResponse> data) {
                ArrayList<MovieEntity> movieList = new ArrayList<>();
                for (MovieResponse response : data) {
                    MovieEntity movie = new MovieEntity(
                            response.getMovieId(),
                            response.getOverview(),
                            response.getPosterPath(),
                            response.getReleaseDate(),
                            response.getTitle(),
                            response.getVoteAverage(),
                            false);
                    movieList.add(movie);
                }
                localDataSource.insertMovies(movieList);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<PagedList<MovieEntity>> getLikedMovies() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(3)
                .setPageSize(3)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getLikedMovies(), config).build();
    }

    @Override
    public void setMovieLike(MovieEntity movie, boolean state) {
        appExecutors.diskIO().execute(() -> localDataSource.setMovieLike(movie, state));
    }

    @Override
    public LiveData<Resource<PagedList<TVShowEntity>>> getAllTVShows() {
        return new NetworkBoundResource<PagedList<TVShowEntity>, List<TVShowResponse>>(appExecutors) {
            public LiveData<PagedList<TVShowEntity>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(3)
                        .setPageSize(3)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getAllTVShows(), config).build();
            }

            @Override
            public Boolean shouldFetch(PagedList<TVShowEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            public LiveData<ApiResponse<List<TVShowResponse>>> createCall() {
                return remoteDataSource.getAllTVShows();
            }

            @Override
            public void saveCallResult(List<TVShowResponse> tvShowResponses) {
                ArrayList<TVShowEntity> tvShowList = new ArrayList<>();
                for (TVShowResponse response : tvShowResponses) {
                    TVShowEntity tvShow = new TVShowEntity(
                            response.getTvShowId(),
                            response.getOverview(),
                            response.getPosterPath(),
                            response.getFirstAirDate(),
                            response.getTitle(),
                            response.getVoteAverage(),
                            false);
                    tvShowList.add(tvShow);
                }
                localDataSource.insertTVShows(tvShowList);
            }
        }.asLiveData();
    }
}