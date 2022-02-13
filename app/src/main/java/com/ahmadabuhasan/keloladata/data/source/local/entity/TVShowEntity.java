package com.ahmadabuhasan.keloladata.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tvShowTable")
public class TVShowEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    private String tvShowId;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    @ColumnInfo(name = "firstAirDate")
    private String firstAirDate;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "voteAverage")
    private String voteAverage;

    public TVShowEntity(@NonNull String tvShowId, String overview, String posterPath, String firstAirDate, String title, String voteAverage) {
        this.tvShowId = tvShowId;
        this.overview = overview;
        this.posterPath = posterPath;
        this.firstAirDate = firstAirDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    @NonNull
    public String getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(@NonNull String tvShowId) {
        this.tvShowId = tvShowId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}