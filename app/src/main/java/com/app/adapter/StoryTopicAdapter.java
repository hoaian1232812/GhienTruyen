package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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
import com.app.model.TimeStory;
import com.app.model.User;
import com.app.user.StoryDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StoryTopicAdapter extends RecyclerView.Adapter<StoryTopicAdapter.StoryTopicVH> {
    List<Story> stories;
    Context context;

    public StoryTopicAdapter(List<Story> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryTopicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_topic, parent, false);
        return new StoryTopicVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryTopicVH holder, int position) {
        Story story = stories.get(position);
        holder.title.setText(story.getTitle());
        CompletableFuture<TimeStory> futureTime = story.getTime();
        futureTime.thenAccept(timeStory -> {
            holder.time.setText(timeStory.showDateTime());
        }).exceptionally(e -> {
            return null;
        });
        CompletableFuture<User> futureName = story.getNameAuthor();
        futureName.thenAccept(user -> {
            holder.author.setText(" • "+user.getName());
        }).exceptionally(e -> {
            return null;
        });
        CompletableFuture<List<Chapter>> future = story.getAllChapter();
        future.thenAccept(chapters -> {
            holder.numChap.setText(chapters.size() + " Chapter");
        }).exceptionally(e -> {
            return null;
        });

        holder.listType.setText(story.getListNameTopic());
        Glide.with(holder.img.getContext())
                .load(story.getImage())
                .transform(new CircleCrop())
                .into(holder.img);
        holder.itemView.setOnClickListener(view -> {
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
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void addNewData(List<Story> newDataList) {
        this.stories.addAll(newDataList);
        notifyDataSetChanged();
    }

    class StoryTopicVH extends RecyclerView.ViewHolder {
        TextView title, time, numChap, listType, author;
        ImageView img;

        public StoryTopicVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_story_topic);
            time = itemView.findViewById(R.id.time_story_topic);
            author = itemView.findViewById(R.id.author_story_topic);
            numChap = itemView.findViewById(R.id.chapter_story_topic);
            listType = itemView.findViewById(R.id.list_type_story_topic);
            img = itemView.findViewById(R.id.img_story_topic);
        }
    }
}
