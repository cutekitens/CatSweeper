package com.example.catsweeper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catsweeper.databinding.FragmentSecondBinding;

import java.util.Locale;

public class SecondFragment extends Fragment implements OnTileClickListener{

    private FragmentSecondBinding binding;
    private RecyclerView catGrid;
    private int NUM_ROWS = 10;
    private int NUM_COLS = 8;
    private int NUM_CATS = 10;
    private CatGridRecyclerAdapter catGridRecyclerAdapter;
    private CatSweeperGame game;
    private TextView starsLeftText;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        catGrid = binding.catGrid;
        catGrid.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLS));
        game = new CatSweeperGame(NUM_ROWS, NUM_COLS, NUM_CATS);
        catGridRecyclerAdapter = new CatGridRecyclerAdapter(game.getCatGrid().getTiles(), this);
        catGrid.setAdapter(catGridRecyclerAdapter);
        starsLeftText = binding.numStarsLeft;
        starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()));
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.restartButton.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onTileClick(Tile tile) {
        game.tileClicked(tile);
        if (game.isGameOver()){
            Toast.makeText(getActivity(), "You woke up a cat! That's game over for you!", Toast.LENGTH_SHORT).show();
            game.getCatGrid().revealAllCats();
        }
        catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
