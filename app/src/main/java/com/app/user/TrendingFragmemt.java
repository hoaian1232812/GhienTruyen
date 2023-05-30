package com.app.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.R;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragmemt extends Fragment {
    View root;
    CardView cardView;

    int limit = 21;
    int page = 1;

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
        cardView = root.findViewById(R.id.appreciation);
        cardView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryAppreciation(limit, page);
            moveActivity(view, call, 0, "Đánh giá");
        });
    }

    public void setUpLike() {
        cardView = root.findViewById(R.id.like);
        cardView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryLiked(limit, page);
            moveActivity(view, call, 1, "Yêu thích");
        });
    }


    public void setUpView() {
        cardView = root.findViewById(R.id.view);
        cardView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryViewed(limit, page);
            moveActivity(view, call, 2, "Xem nhiều");
        });
    }

    public void moveActivity(View view, Call<List<Story>> call, int position, String title) {
        Intent intent = new Intent(view.getContext(), TrendingStoryHomeActivity.class);
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("listData", new Gson().toJson(response.body()));
                    bundle.putString("title", title);
                    bundle.putInt("api", position);
                    intent.putExtra("data", bundle);
                    view.getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                Toast.makeText(root.getContext(), "không thể truy cập dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}