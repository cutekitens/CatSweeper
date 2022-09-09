package com.example.catsweeper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catsweeper.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment implements OnTileClickListener{

    private FragmentSecondBinding binding;
    private RecyclerView catGrid;
    private int NUM_ROWS = 10;
    private int NUM_COLS = 8;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        catGrid = binding.catGrid;
        catGrid.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLS));
        CatSweeperGame game = new CatSweeperGame(NUM_ROWS, NUM_COLS);
        CatGridRecyclerAdapter catGridRecyclerAdapter = new CatGridRecyclerAdapter(game.getCatGrid().getTiles(), this);
        catGrid.setAdapter(catGridRecyclerAdapter);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.restartButton.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onTileClick(Tile tile) {
        Toast.makeText(getActivity(), "Tile clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

/*
    gridRecyclerView = binding.catGrid;
        gridRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 8));
        game = new CatSweeperGame(10, 8);
        catGridRecyclerAdapter = new CatGridRecyclerAdapter(game.getCatGrid().getTiles(), this);
        gridRecyclerView.setAdapter(catGridRecyclerAdapter);
     */