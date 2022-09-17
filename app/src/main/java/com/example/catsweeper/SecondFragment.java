package com.example.catsweeper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private final int NUM_ROWS = 10;
    private final int NUM_COLS = 8;
    private final int NUM_CATS = 10;
    private CatGridRecyclerAdapter catGridRecyclerAdapter;
    private CatSweeperGame game;
    private TextView starsLeftText;
    private ImageButton star_button;

    /*
    To work do:
    - Potential bug with cats being miscounted when moving cat on first click
    - Change main menu pic
    - Change app icon
    - Need to get sleeping cat icon
    - Explanation of game (explain cat concept)
    - MORE TESTING
     */

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        RecyclerView catGrid = binding.catGrid;
        star_button = binding.starButton;
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
        binding.restartButton.setOnClickListener(this::restartGame);
        star_button.setOnClickListener(this::starOffAndOn);
        binding.mainMenuButton.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onTileClick(Tile tile) {
        game.tileClicked(tile);
        starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()-game.getNumStars()));
        if (game.isGameOver()) {
            Toast.makeText(getActivity(), "You woke up a cat! That's game over for you!", Toast.LENGTH_SHORT).show();
            if (!game.getCatGrid().areCatsRevealed()){
                game.getCatGrid().revealAllCats(); // don't need to reveal cats more than once
            }
        }
        if (game.isGameWon()){
            Toast.makeText(getActivity(), "You won! The cats are sleeping peacefully!", Toast.LENGTH_SHORT).show();
        }
        catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
    }

    public void restartGame(View view){
        star_button.setImageResource(android.R.drawable.btn_star_big_off);
        starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()));
        game = new CatSweeperGame(NUM_ROWS, NUM_COLS, NUM_CATS);
        catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
    }

    public void starOffAndOn(View view){
        if (game.isGameWon() || game.isGameOver()){
            return;
        }
        if (!game.isStarMode()){
            star_button.setImageResource(android.R.drawable.btn_star_big_on);
            game.setStarMode(true);
        }
        else {
            star_button.setImageResource(android.R.drawable.btn_star_big_off);
            game.setStarMode(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
