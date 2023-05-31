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
import com.app.model.Comment;
import com.app.model.Story;

import com.app.user.StoryDetail;
import com.app.userdashboard.StoryStatisticalActivity;
import com.app.userdashboard.detail.ListChapterActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;

import java.util.List;


public class StoryListBoardAdapter extends RecyclerView.Adapter<StoryListBoardAdapter.StoryTopicVH> {
    List<Story> stories;
    Context context;

    public StoryListBoardAdapter(List<Story> stories) {
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryListBoardAdapter.StoryTopicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_topic, parent, false);
        return new StoryListBoardAdapter.StoryTopicVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryListBoardAdapter.StoryTopicVH holder, int position) {
        Story story = stories.get(position);
        holder.title.setText(story.getTitle());
        Glide.with(holder.img.getContext())
                .load(story.getImage())
                .transform(new CircleCrop())
                .into(holder.img);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ListChapterActivity.class);
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

    public void addNewData(List<Story> body) {
        this.stories.addAll(body);
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
