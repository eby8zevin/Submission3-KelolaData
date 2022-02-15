package com.ahmadabuhasan.keloladata.ui.favorite.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.keloladata.databinding.FragmentFavoriteMovieBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;

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

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            viewModel = new ViewModelProvider(this, factory).get(FavoriteMovieViewModel.class);

            adapter = new FavoriteMovieAdapter();
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getLikes().observe(this, courses -> {
                binding.progressBar.setVisibility(View.GONE);
                adapter.submitList(courses);
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
}