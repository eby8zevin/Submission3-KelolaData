package com.ahmadabuhasan.keloladata.ui.favorite;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmadabuhasan.keloladata.R;
import com.ahmadabuhasan.keloladata.databinding.ActivityFavoriteBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.movie,
            R.string.tv_show
    };

    private ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Favorite");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        binding.viewPager2.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}