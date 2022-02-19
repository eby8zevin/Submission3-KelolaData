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
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.databinding.ActivityDetailTvshowBinding;
import com.ahmadabuhasan.keloladata.databinding.ContentDetailBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class DetailTVShowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private ActivityDetailTvshowBinding binding;
    private ContentDetailBinding contentDetailBinding;

    DetailTVShowViewModel viewModel;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detail TVShow");
        binding = ActivityDetailTvshowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contentDetailBinding = binding.detailContent;

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory).get(DetailTVShowViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tvShowId = extras.getString(EXTRA_TVSHOW);
            if (tvShowId != null) {
                viewModel.setTVShowId(tvShowId);

                viewModel.tvShowEmbedded.observe(this, tvShowEmbeddedResource -> {
                    if (tvShowEmbeddedResource != null) {
                        switch (tvShowEmbeddedResource.status) {
                            case LOADING:
                                binding.progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                if (tvShowEmbeddedResource.data != null) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.content.setVisibility(View.VISIBLE);
                                    populateTVShow(tvShowEmbeddedResource.data.mTVShows);
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

    private void populateTVShow(TVShowEntity tvShow) {
        contentDetailBinding.tvDate.setText(tvShow.getFirstAirDate());
        contentDetailBinding.tvTitle.setText(tvShow.getTitle());
        contentDetailBinding.ratingBar.setRating(Float.parseFloat(tvShow.getVoteAverage()));
        contentDetailBinding.tvDetail.setText(tvShow.getOverview());

        Glide.with(this)
                .load(tvShow.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(contentDetailBinding.ivPoster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;

        viewModel.tvShowEmbedded.observe(this, tvShowEmbeddedResource -> {
            if (tvShowEmbeddedResource != null) {
                switch (tvShowEmbeddedResource.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        if (tvShowEmbeddedResource.data != null) {
                            binding.progressBar.setVisibility(View.GONE);
                            boolean state = tvShowEmbeddedResource.data.mTVShows.isLiked();
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
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }
}