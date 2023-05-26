package com.app.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;

public class TrendingFragmemt extends Fragment {
    View root;
    CardView appreciation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_trending_fragmemt, container, false);
        setUpAppreciation();
        setUpLike();
        setUpView();
        return root;
    }

    public void setUpAppreciation() {
        appreciation = root.findViewById(R.id.appreciation);
        appreciation.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ReviewStoryHomeActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    public void setUpLike() {
        appreciation = root.findViewById(R.id.like);
        appreciation.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MostLikeHomeActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    public void setUpView() {
        appreciation = root.findViewById(R.id.view);
        appreciation.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MostViewHomeActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}