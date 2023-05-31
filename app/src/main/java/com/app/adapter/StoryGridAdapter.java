package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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
import com.app.user.StoryDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;

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
                .load(story.getImage())
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                String uniqueName = "user_preferences_" + deviceId;
                SharedPreferences userPreferences = view.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userPreferences.edit();
                Gson gson = new Gson();
                editor.putString("story_" + story.getId() + "_read", gson.toJson(story));
                editor.apply();
                Intent intent = new Intent(view.getContext(), StoryDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtra("data", bundle);
                view.getContext().startActivity(intent);
            }
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
