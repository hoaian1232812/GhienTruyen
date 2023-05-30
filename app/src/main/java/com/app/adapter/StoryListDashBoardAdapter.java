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

public class StoryListDashBoardAdapter extends RecyclerView.Adapter<StoryListDashBoardAdapter.StoryTopicVH> {

    List<Story> stories;
    Context context;

    public StoryListDashBoardAdapter(List<Story> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryListDashBoardAdapter.StoryTopicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_topic, parent, false);
        return new StoryListDashBoardAdapter.StoryTopicVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryListDashBoardAdapter.StoryTopicVH holder, int position) {
        Story story = stories.get(position);
        holder.title.setText(story.getTitle());

    }

    @Override
    public int getItemCount() {
        return stories.size();
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
