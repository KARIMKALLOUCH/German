package com.devkallouch.german;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardItem> cardItems;
    private Activity activity;

    public CardAdapter(List<CardItem> cardItems, Activity activity) {
        this.cardItems = cardItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem item = cardItems.get(position);

        holder.cardTitle.setText(item.getTitle());
        holder.cardImage.setImageResource(item.getImageRes());
        if (item.isRead()) {
            holder.statusIcon.setImageResource(R.drawable.baseline_done_all_24);
        } else {
            holder.statusIcon.setImageResource(item.getStatusIconRes());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailActvity.class);
            intent.putExtra("currentPage", item.getCurrentPage()); // استخدم getCurrentPage()
            intent.putExtra("title", item.getTitle());
            intent.putExtra("image", item.getImageRes());
            intent.putExtra("audio", item.getAudioRes());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("share_text", item.getShareText());
            activity.startActivityForResult(intent, item.getRequestCode());
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage, statusIcon;
        TextView cardTitle;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            cardTitle = itemView.findViewById(R.id.cardTitle);
        }
    }
}
