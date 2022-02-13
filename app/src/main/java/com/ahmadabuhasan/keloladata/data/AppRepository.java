package com.ahmadabuhasan.keloladata.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.ahmadabuhasan.keloladata.data.source.local.LocalDataSource;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.data.source.remote.ApiResponse;
import com.ahmadabuhasan.keloladata.data.source.remote.RemoteDataSource;
import com.ahmadabuhasan.keloladata.data.source.remote.response.MovieResponse;
import com.ahmadabuhasan.keloladata.utils.AppExecutors;
import com.ahmadabuhasan.keloladata.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class AppRepository implements AppDataSource {

    private volatile static AppRepository INSTANCE = null;

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    private AppRepository(RemoteDataSource remoteDataSource1, LocalDataSource localDataSource1, AppExecutors appExecutors1) {
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
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
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
                            response.getVoteAverage());
                    movieList.add(movie);
                }
                localDataSource.insertMovies(movieList);
            }
        }.asLiveData();
    }
}