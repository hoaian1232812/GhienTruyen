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
import com.app.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicFragment extends Fragment {
    TopicAdapter adapter;
    GridLayoutManager layout;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_topic, container, false);
        setUp(root);
        return root;
    }

    public void setUp(View root) {
        layout = new GridLayoutManager(root.getContext(), 2);
        recyclerView = root.findViewById(R.id.topic_list);
        recyclerView.setLayoutManager(layout);
        Call<List<Topic>> call = ApiClient.getApiService().getAllTopic();
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if(response.isSuccessful()){
                    adapter = new TopicAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {

            }
        });

    }
}