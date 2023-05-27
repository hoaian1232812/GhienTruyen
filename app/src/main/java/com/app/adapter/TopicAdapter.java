package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.app.MainActivity;
import com.app.R;
import com.app.model.Topic;
import com.app.user.TopicDetailActivity;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TopicDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic", topic);
                intent.putExtra("data", bundle);
                view.getContext().startActivity(intent);
            }
        });
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
