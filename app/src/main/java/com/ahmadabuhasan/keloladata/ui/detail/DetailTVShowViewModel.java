package com.ahmadabuhasan.keloladata.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ahmadabuhasan.keloladata.data.AppRepository;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEmbedded;
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.vo.Resource;

public class DetailTVShowViewModel extends ViewModel {

    private final MutableLiveData<String> tvShowId = new MutableLiveData<>();
    private AppRepository repository;

    public DetailTVShowViewModel(AppRepository mRepository) {
        this.repository = mRepository;
    }

    public LiveData<Resource<TVShowEmbedded>> tvShowEmbedded = Transformations.switchMap(tvShowId,
            mTVShowId -> repository.getDetailTVShow(mTVShowId));

    public String getTVShowId() {
        return tvShowId.getValue();
    }

    public void setTVShowId(String tvShowId) {
        this.tvShowId.setValue(tvShowId);
    }

    void setLike() {
        Resource<TVShowEmbedded> moduleResource = tvShowEmbedded.getValue();
        if (moduleResource != null) {
            TVShowEmbedded tvShowEmbedded = moduleResource.data;

            if (tvShowEmbedded != null) {
                TVShowEntity tvShowEntity = tvShowEmbedded.mTVShows;

                final boolean newState = !tvShowEntity.isLiked();
                repository.setTVShowLike(tvShowEntity, newState);
            }
        }
    }
}