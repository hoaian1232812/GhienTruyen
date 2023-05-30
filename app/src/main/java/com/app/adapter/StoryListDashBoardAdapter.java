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
import com.app.model.Story;
import com.bumptech.glide.Glide;

import java.util.List;

public class StoryListDashBoardAdapter extends RecyclerView.Adapter<StoryListDashBoardAdapter.StoryVH> {

    List<Story> stories;
    Context context;

    public StoryListDashBoardAdapter(List<Story> stories) {
        this.stories = stories;
    }


    @NonNull
    @Override
    public StoryListDashBoardAdapter.StoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_story_dash_board, parent, false);
        return new StoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryListDashBoardAdapter.StoryVH holder, int position) {
        Story story = stories.get(position);
        holder.textView.setText(story.getTitle());
        Glide.with(holder.imageView.getContext()).load("http://139.180.129.238:8080/Untitled1.jpg").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public StoryVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_truyen);
            textView = itemView.findViewById(R.id.title);
        }
    }
}
