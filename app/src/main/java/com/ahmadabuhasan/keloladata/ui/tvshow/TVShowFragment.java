package com.ahmadabuhasan.keloladata.ui.tvshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.keloladata.databinding.FragmentTvshowBinding;
import com.ahmadabuhasan.keloladata.viewmodel.ViewModelFactory;

public class TVShowFragment extends Fragment {

    private FragmentTvshowBinding binding;

    public TVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTvshowBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            TVShowViewModel viewModel = new ViewModelProvider(this, factory).get(TVShowViewModel.class);

            TVShowAdapter adapter = new TVShowAdapter();
            viewModel.getTVShows().observe(this, tvShows -> {
                if (tvShows != null) {
                    switch (tvShows.status) {
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            binding.progressBar.setVisibility(View.GONE);
                            adapter.submitList(tvShows.data);
                            break;
                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "There is an error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            binding.rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvTvShow.setHasFixedSize(true);
            binding.rvTvShow.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}