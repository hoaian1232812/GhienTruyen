package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Topic;
import com.app.user.TopicDetailActivity;

import java.util.List;

public class TopicStoryDetailAdapter extends RecyclerView.Adapter<TopicStoryDetailAdapter.TopicStoryDetailVH> {
    List<Topic> topics;
    Context context;

    public TopicStoryDetailAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicStoryDetailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic_story_detail, parent, false);
        return new TopicStoryDetailVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicStoryDetailVH holder, int position) {
        Topic topic = topics.get(position);
        holder.text.setText(topic.getName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), TopicDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("topic", topic);
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    class TopicStoryDetailVH extends RecyclerView.ViewHolder {
        TextView text;

        public TopicStoryDetailVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.topic_story_text);
        }
    }
}
