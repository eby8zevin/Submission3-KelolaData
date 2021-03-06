package com.ahmadabuhasan.keloladata.ui.favorite.movie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadabuhasan.keloladata.R;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.databinding.ItemListBinding;
import com.ahmadabuhasan.keloladata.ui.detail.DetailMovieActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteMovieAdapter extends PagedListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteViewHolder> {

    FavoriteMovieAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.getMovieId().equals(newItem.getMovieId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public FavoriteMovieAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.FavoriteViewHolder holder, int position) {
        MovieEntity movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final ItemListBinding binding;

        FavoriteViewHolder(ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieEntity movie) {
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
            binding.tvTitle.setText(movie.getTitle());
            binding.tvDate.setText(movie.getReleaseDate());

            itemView.setOnClickListener(view -> {
                Intent i = new Intent(itemView.getContext(), DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.getMovieId());
                Toast.makeText(itemView.getContext(), "You Choose: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                itemView.getContext().startActivity(i);
            });
        }
    }

    public MovieEntity getSwipedData(int swipedPosition) {
        return getItem(swipedPosition);
    }
}