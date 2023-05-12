package com.app.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.adapter.TopicAdapter;
import com.app.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicFragment extends Fragment {
    TopicAdapter adapter;
    List<Topic> topics;
    GridLayoutManager layout;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_topic, container, false);
        setUp(root);
        return root;
    }

    public void setUp(View root) {
        layout = new GridLayoutManager(root.getContext(), 2);
        recyclerView = root.findViewById(R.id.topic_list);
        recyclerView.setLayoutManager(layout);
        topics = new ArrayList<>();
        topics.add(new Topic(1, "Ngôn tình"));
        topics.add(new Topic(2, "Ngôn tình"));
        topics.add(new Topic(3, "Ngôn tình"));
        topics.add(new Topic(4, "Ngôn tình"));
        topics.add(new Topic(5, "Ngôn tình"));
        topics.add(new Topic(6, "Ngôn tình"));
        adapter = new TopicAdapter(topics);
        recyclerView.setAdapter(adapter);
    }
}