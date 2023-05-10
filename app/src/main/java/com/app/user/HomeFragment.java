package com.app.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.adapter.TruyenAdapter;
import com.app.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TruyenAdapter truyenAdapter;
    List<Truyen> truyenListNewStory, truyenListNewUpdate;
    RecyclerView recyclerViewNewStory;

    RecyclerView recyclerViewNewUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
       setUpNewStory(root);
       setUpNewUpdate(root);
        return root;
    }

    public void setUpNewStory(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewStory = root.findViewById(R.id.recyleViewNewStory);
        recyclerViewNewStory.setLayoutManager(linearLayoutManager);
        truyenListNewStory = new ArrayList<>();
        truyenListNewStory.add(new Truyen("","title truyen1"));
        truyenListNewStory.add(new Truyen("","title truyen2"));
        truyenListNewStory.add(new Truyen("","title truyen3"));
        truyenListNewStory.add(new Truyen("","title truyen4"));
        truyenAdapter = new TruyenAdapter(truyenListNewStory);
        recyclerViewNewStory.setAdapter(truyenAdapter);
    }

    public void setUpNewUpdate(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewUpdate = root.findViewById(R.id.recyleViewNewUpdate);
        recyclerViewNewUpdate.setLayoutManager(linearLayoutManager);
        truyenListNewUpdate = new ArrayList<>();
        truyenListNewUpdate.add(new Truyen("","title truyen1"));
        truyenListNewUpdate.add(new Truyen("","title truyen2"));
        truyenListNewUpdate.add(new Truyen("","title truyen3"));
        truyenListNewUpdate.add(new Truyen("","title truyen4"));
        truyenListNewUpdate.add(new Truyen("","title truyen5"));
        truyenListNewUpdate.add(new Truyen("","title truyen6"));
        truyenAdapter = new TruyenAdapter(truyenListNewUpdate);
        recyclerViewNewUpdate.setAdapter(truyenAdapter);
    }
}