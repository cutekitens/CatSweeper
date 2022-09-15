package com.example.catsweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatGridRecyclerAdapter extends RecyclerView.Adapter<CatGridRecyclerAdapter.CatTileViewHolder> {
    private List<Tile> tiles;
    private OnTileClickListener listener;

    public CatGridRecyclerAdapter(List<Tile> tiles, OnTileClickListener listener) {
        this.tiles = tiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, parent, false);
        return new CatTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatTileViewHolder holder, int position) {
        holder.bind(tiles.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
        notifyDataSetChanged();
    }

    class CatTileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public CatTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.item_tile_value);
        }

        public void bind(final Tile tile) {
            itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.tile_background));
            itemView.setOnClickListener(view -> listener.onTileClick(tile));
            if (tile.isRevealed()){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.tile_color));
                if (tile.getValue() == Tile.SLEEPING_CAT) {
                    valueTextView.setText(R.string.sleeping_cat);
                } else if (tile.getValue() == Tile.BLANK) {
                    valueTextView.setText("");
                } else {
                    valueTextView.setText(String.valueOf(tile.getValue()));
                    if (tile.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                    } else if (tile.getValue() == 2) {
                        valueTextView.setTextColor(Color.GREEN);
                    } else if (tile.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED);
                    }
                    else if (tile.getValue() == 4){
                        valueTextView.setTextColor(Color.rgb(102, 51, 153));
                    }
                    else if (tile.getValue() == 5){
                        valueTextView.setTextColor(Color.rgb(128, 0, 0));
                    }
                    else if (tile.getValue() == 6){
                        valueTextView.setTextColor(Color.rgb(32, 178, 170));
                    }
                    else if (tile.getValue() == 7){
                        valueTextView.setTextColor(Color.rgb(0, 0, 0));
                    }
                    else if (tile.getValue() == 8){
                        valueTextView.setTextColor(Color.rgb(169, 169, 169));
                    }
                }
            } else if (tile.isStarred()) {
                itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.tile_starred));
            }
        }
    }
}

