package com.example.catsweeper;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catsweeper.databinding.FragmentThirdBinding;

import java.util.Locale;

public class ThirdFragment extends Fragment{

    private FragmentThirdBinding binding;
    private CatSweeperGame game;
    private TextView starsLeftText;
    private TextView tutorialText;
    private int pageNum = 1;
    private String[] tutorial;
    private CatGridRecyclerAdapter2 catGridRecyclerAdapter;
    private ImageButton sampleStarButton;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        RecyclerView catGrid = binding.sampleCatGrid;
        catGrid.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        game = new CatSweeperGame(5, 5, 3, true);
        catGridRecyclerAdapter = new CatGridRecyclerAdapter2(game.getCatGrid().getTiles());
        catGrid.setAdapter(catGridRecyclerAdapter);
        starsLeftText = binding.sampleNumStarsLeft;
        tutorialText = binding.tutorialText;
        tutorial = getResources().getStringArray(R.array.tutorialPages);
        tutorialText.setText(tutorial[0]);
        starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()));
        sampleStarButton = binding.sampleStarButton;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.returnToMenuButton.setOnClickListener(this::goThroughTutorial);
    }

    public void goThroughTutorial(View view){
        if (pageNum > 7){
            NavHostFragment.findNavController(ThirdFragment.this)
                    .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            return;
        }
        else if (pageNum == 1){
            game.tileClicked(game.getCatGrid().tileAt(0, 0));
            catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
        }
        else if (pageNum == 2){
            starsLeftText.setBackgroundColor(Color.RED);
        }
        else if (pageNum == 3){
            starsLeftText.setBackgroundColor(Color.TRANSPARENT);
            game.tileClicked(game.getCatGrid().tileAt(0, 1));
            game.getCatGrid().revealAllCats();
            catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
        }
        else if (pageNum == 4){
            game = new CatSweeperGame(5, 5, 3, true);
            game.tileClicked(game.getCatGrid().tileAt(3, 0));
            catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
        }
        else if (pageNum == 5){
            sampleStarButton.setImageResource(android.R.drawable.btn_star_big_on);
            game.setStarMode(true);
        }
        else if (pageNum == 6){
            game.tileClicked(game.getCatGrid().tileAt(0, 1));
            catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
            starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()-game.getNumStars()));
        }
        else if (pageNum == 7){
            game.tileClicked(game.getCatGrid().tileAt(4,4));
            game.tileClicked(game.getCatGrid().tileAt(2,3));
            sampleStarButton.setImageResource(android.R.drawable.btn_star_big_off);
            game.setStarMode(false);
            for (Tile t: game.getCatGrid().getTiles()){
                if (!t.isStarred()){
                    game.tileClicked(t);
                }
            }
            catGridRecyclerAdapter.setTiles(game.getCatGrid().getTiles());
            starsLeftText.setText(String.format(Locale.US, "%03d", game.getNumCats()-game.getNumStars()));
            binding.returnToMenuButton.setText(getString(R.string.mainMenuButton_text));
        }
        tutorialText.setText(tutorial[pageNum]);
        pageNum++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
