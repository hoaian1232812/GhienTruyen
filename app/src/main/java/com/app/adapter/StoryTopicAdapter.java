package com.app.adapter;

import android.content.Context;
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
import com.app.model.User;
import com.bumptech.glide.Glide;

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
        CompletableFuture<User> futureName = story.getNamAuthor();
        futureName.thenAccept(user -> {
            holder.timeAuthor.setText(story.getDate() + " " + user.getName());
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
                .load("http://139.180.129.238:8080/Untitled1.jpg")
                .into(holder.img);
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
        TextView title, timeAuthor, numChap, listType;
        ImageView img;

        public StoryTopicVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_story_topic);
            timeAuthor = itemView.findViewById(R.id.time_author_story_topic);
            numChap = itemView.findViewById(R.id.chapter_story_topic);
            listType = itemView.findViewById(R.id.list_type_story_topic);
            img = itemView.findViewById(R.id.img_story_topic);
        }
    }
}
