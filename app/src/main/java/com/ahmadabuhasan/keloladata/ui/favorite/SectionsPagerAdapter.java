package com.ahmadabuhasan.keloladata.ui.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ahmadabuhasan.keloladata.ui.favorite.movie.FavoriteMovieFragment;
import com.ahmadabuhasan.keloladata.ui.favorite.tvshow.FavoriteTVShowFragment;
import com.ahmadabuhasan.keloladata.ui.movie.MovieFragment;
import com.ahmadabuhasan.keloladata.ui.tvshow.TVShowFragment;

import java.util.Objects;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;
            case 1:
                fragment = new FavoriteTVShowFragment();
                break;
        }
        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}