package com.app.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.R;
import com.app.adapter.TruyenAdapter;
import com.app.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TruyenAdapter truyenAdapter;
    List<Truyen> truyenList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = root.findViewById(R.id.recyleView);
        recyclerView.setLayoutManager(linearLayoutManager);
        truyenList = new ArrayList<>();
        truyenList.add(new Truyen("","title truyen1"));
        truyenList.add(new Truyen("","title truyen2"));
        truyenList.add(new Truyen("","title truyen3"));
        truyenList.add(new Truyen("","title truyen4"));
        truyenAdapter = new TruyenAdapter(truyenList);
        recyclerView.setAdapter(truyenAdapter);
        return root;
    }
}