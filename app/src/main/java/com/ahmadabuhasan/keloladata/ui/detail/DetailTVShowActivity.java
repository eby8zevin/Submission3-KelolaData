package com.ahmadabuhasan.keloladata.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ahmadabuhasan.keloladata.R;

public class DetailTVShowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);
    }
}