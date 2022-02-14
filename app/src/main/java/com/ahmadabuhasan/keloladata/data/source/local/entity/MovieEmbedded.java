package com.ahmadabuhasan.keloladata.data.source.local.entity;

import androidx.room.Embedded;

public class MovieEmbedded {

    @Embedded
    public MovieEntity mMovies;
}