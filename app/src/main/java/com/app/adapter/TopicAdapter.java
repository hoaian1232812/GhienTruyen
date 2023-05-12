package com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.model.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicVH> {
    List<Topic> topics;
    Context context;

    public TopicAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new TopicVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicVH holder, int position) {
        Topic topic = topics.get(position);
        holder.text.setText(topic.getName());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    class TopicVH extends RecyclerView.ViewHolder {
        TextView text;

        public TopicVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.topic_text);
        }
    }
}
