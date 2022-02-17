package com.ahmadabuhasan.keloladata.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmadabuhasan.keloladata.R;
import com.ahmadabuhasan.keloladata.databinding.ActivityHomeBinding;
import com.ahmadabuhasan.keloladata.ui.favorite.FavoriteActivity;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.movie,
            R.string.tv_show
    };

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_like) {
            startActivity(new Intent(this, FavoriteActivity.class));
            Toast.makeText(getApplicationContext(), "Favorite", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        this.finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    HomeActivity.super.onBackPressed();
                    finishAndRemoveTask();
                }).create().show();

    }
}