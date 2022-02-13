package com.ahmadabuhasan.keloladata.di;

import android.content.Context;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.LocalDataSource;
import com.ahmadabuhasan.keloladata.data.source.local.room.AppDatabase;
import com.ahmadabuhasan.keloladata.data.source.remote.RemoteDataSource;
import com.ahmadabuhasan.keloladata.utils.AppExecutors;
import com.ahmadabuhasan.keloladata.utils.JsonHelper;

public class Injection {

    public static AppRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.appDao());
        AppExecutors appExecutors = new AppExecutors();

        return AppRepository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }
}