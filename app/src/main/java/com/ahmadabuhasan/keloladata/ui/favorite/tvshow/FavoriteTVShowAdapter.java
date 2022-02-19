package com.ahmadabuhasan.keloladata.ui.favorite.tvshow;

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
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.databinding.ItemListBinding;
import com.ahmadabuhasan.keloladata.ui.detail.DetailTVShowActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteTVShowAdapter extends PagedListAdapter<TVShowEntity, FavoriteTVShowAdapter.FavoriteViewHolder> {

    FavoriteTVShowAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TVShowEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TVShowEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull TVShowEntity oldItem, @NonNull TVShowEntity newItem) {
                    return oldItem.getTvShowId().equals(newItem.getTvShowId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TVShowEntity oldItem, @NonNull TVShowEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public FavoriteTVShowAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTVShowAdapter.FavoriteViewHolder holder, int position) {
        TVShowEntity tvShow = getItem(position);
        if (tvShow != null) {
            holder.bind(tvShow);
        }
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final ItemListBinding binding;

        FavoriteViewHolder(ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TVShowEntity tvShow) {
            Glide.with(itemView.getContext())
                    .load(tvShow.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
            binding.tvTitle.setText(tvShow.getTitle());
            binding.tvDate.setText(tvShow.getFirstAirDate());

            itemView.setOnClickListener(view -> {
                Intent i = new Intent(itemView.getContext(), DetailTVShowActivity.class);
                i.putExtra(DetailTVShowActivity.EXTRA_TVSHOW, tvShow.getTvShowId());
                Toast.makeText(itemView.getContext(), "You Choose: " + tvShow.getTitle(), Toast.LENGTH_SHORT).show();
                itemView.getContext().startActivity(i);
            });
        }
    }

    public TVShowEntity getSwipedData(int swipedPosition) {
        return getItem(swipedPosition);
    }
}