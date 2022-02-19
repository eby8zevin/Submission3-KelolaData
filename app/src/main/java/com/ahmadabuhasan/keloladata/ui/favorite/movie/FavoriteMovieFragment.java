package com.ahmadabuhasan.keloladata.ui.favorite.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadabuhasan.keloladata.R;
import com.ahmadabuhasan.keloladata.data.source.local.entity.MovieEntity;
import com.ahmadabuhasan.keloladata.databinding.FragmentFavoriteMovieBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class FavoriteMovieFragment extends Fragment {

    private FragmentFavoriteMovieBinding binding;
    private FavoriteMovieAdapter adapter;
    private FavoriteMovieViewModel viewModel;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteMovieBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteMovie);

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            viewModel = new ViewModelProvider(this, factory).get(FavoriteMovieViewModel.class);

            adapter = new FavoriteMovieAdapter();
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getLikes().observe(this, movies -> {
                binding.progressBar.setVisibility(View.GONE);
                adapter.submitList(movies);
            });

            binding.rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavoriteMovie.setHasFixedSize(true);
            binding.rvFavoriteMovie.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                int swipedPosition = viewHolder.getAdapterPosition();
                MovieEntity movieEntity = adapter.getSwipedData(swipedPosition);
                viewModel.setLike(movieEntity);
                Snackbar snackbar = Snackbar.make(getView(), R.string.message_undo, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.message_ok, v -> viewModel.setLike(movieEntity));
                snackbar.show();
            }
        }
    });
}