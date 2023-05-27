package com.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Chapter;
import com.app.model.Story;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StoryGridAdapter extends RecyclerView.Adapter<StoryGridAdapter.StoryGridAdapterVH> {
    List<Story> stories;
    Context context;

    public StoryGridAdapter(List<Story> storys) {
        this.stories = storys;
    }

    public void addNewData(List<Story> newDataList) {
        this.stories.addAll(newDataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryGridAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_grid, parent, false);
        return new StoryGridAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryGridAdapterVH holder, int position) {
        Story story = stories.get(position);
        Glide.with(holder.img.getContext())
                .load("http://139.180.129.238:8080/Untitled1.jpg")
                .into(holder.img);
        holder.title.setText(story.getTitle());
        CompletableFuture<List<Chapter>> futureChapter = story.getAllChapter();
        futureChapter.thenAccept(chapters -> {
            holder.chapter.setText(chapters.size() + "");
        });
        CompletableFuture<Double> futureRating = story.getRating();
        futureRating.thenAccept(rate -> {
            holder.rate.setText(rate + "");
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryGridAdapterVH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, chapter, rate;

        public StoryGridAdapterVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_grid);
            title = itemView.findViewById(R.id.title_gird);
            chapter = itemView.findViewById(R.id.chapter_grid);
            rate = itemView.findViewById(R.id.rate_grid);
        }
    }
}
