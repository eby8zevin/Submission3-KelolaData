package com.ahmadabuhasan.keloladata.ui.favorite.tvshow;

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
import com.ahmadabuhasan.keloladata.data.source.local.entity.TVShowEntity;
import com.ahmadabuhasan.keloladata.databinding.FragmentFavoriteTvshowBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class FavoriteTVShowFragment extends Fragment {

    private FragmentFavoriteTvshowBinding binding;
    private FavoriteTVShowAdapter adapter;
    private FavoriteTVShowViewModel viewModel;

    public FavoriteTVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteTvshowBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteTvShow);

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            viewModel = new ViewModelProvider(this, factory).get(FavoriteTVShowViewModel.class);

            adapter = new FavoriteTVShowAdapter();
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getLikes().observe(this, tvShows -> {
                binding.progressBar.setVisibility(View.GONE);
                adapter.submitList(tvShows);
            });

            binding.rvFavoriteTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavoriteTvShow.setHasFixedSize(true);
            binding.rvFavoriteTvShow.setAdapter(adapter);
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
                TVShowEntity tvShowEntity = adapter.getSwipedData(swipedPosition);
                viewModel.setLike(tvShowEntity);
                Snackbar snackbar = Snackbar.make(getView(), R.string.message_undo, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.message_ok, v -> viewModel.setLike(tvShowEntity));
                snackbar.show();
            }
        }
    });
}