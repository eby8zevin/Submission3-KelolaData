package com.ahmadabuhasan.keloladata.ui.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ahmadabuhasan.keloladata.R;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.databinding.ActivityDetailMovieBinding;
import com.ahmadabuhasan.keloladata.databinding.ContentDetailBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private ActivityDetailMovieBinding binding;
    private ContentDetailBinding contentDetailBinding;

    DetailMovieViewModel viewModel;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detail Movie");
        binding = ActivityDetailMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contentDetailBinding = binding.detailContent;

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory).get(DetailMovieViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String movieId = extras.getString(EXTRA_MOVIE);
            if (movieId != null) {
                viewModel.setMovieId(movieId);

                viewModel.movieEmbedded.observe(this, movieEmbeddedResource -> {
                    if (movieEmbeddedResource != null) {
                        switch (movieEmbeddedResource.status) {
                            case LOADING:
                                binding.progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                if (movieEmbeddedResource.data != null) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.content.setVisibility(View.VISIBLE);
                                    populateMovie(movieEmbeddedResource.data.mMovies);
                                }
                                break;
                            case ERROR:
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "There is an error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        }
    }

    private void populateMovie(MovieEntity movie) {
        contentDetailBinding.tvDate.setText(movie.getReleaseDate());
        contentDetailBinding.tvTitle.setText(movie.getTitle());
        contentDetailBinding.ratingBar.setRating(Float.parseFloat(movie.getVoteAverage()));
        contentDetailBinding.tvDetail.setText(movie.getOverview());

        Glide.with(this)
                .load(movie.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(contentDetailBinding.ivPoster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;

        viewModel.movieEmbedded.observe(this, movieEmbeddedResource -> {
            if (movieEmbeddedResource != null) {
                switch (movieEmbeddedResource.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        if (movieEmbeddedResource.data != null) {
                            binding.progressBar.setVisibility(View.GONE);
                            boolean state = movieEmbeddedResource.data.mMovies.isLiked();
                            setLikeState(state);
                        }
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "There is an error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_like) {
            viewModel.setLike();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        contentDetailBinding = null;
    }

    private void setLikeState(boolean state) {
        if (menu == null) return;
        MenuItem menuItem = menu.findItem(R.id.action_like);
        if (state) {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            Toast.makeText(getApplicationContext(), "Add to Favorite", Toast.LENGTH_SHORT).show();
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
            Toast.makeText(getApplicationContext(), "Delete from Favorite", Toast.LENGTH_SHORT).show();
        }
    }
}