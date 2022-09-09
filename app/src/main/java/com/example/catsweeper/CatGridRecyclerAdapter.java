package com.example.catsweeper;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTileClick(tile);
                }
            });

            /*if (Tile.isRevealed()) {
                if (Tile.getValue() == Tile.SLEEPING_CAT) {
                    valueTextView.setText(R.string.bomb);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    if (cell.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                    } else if (cell.getValue() == 2) {
                        valueTextView.setTextColor(Color.GREEN);
                    } else if (cell.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED);
                    }
                }
            } else if (cell.isFlagged()) {
                valueTextView.setText(R.string.flag);
            }*/
        }
    }
}

