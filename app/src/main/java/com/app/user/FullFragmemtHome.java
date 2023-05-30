package com.app.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullFragmemtHome extends Fragment {
    View root;
    TextView textView;
    int limit = 21;
    int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_full_fragmemt_home, container, false);
        setUpNewUpdateFull();
        setUpAppreciationFull();
        setUpLikeFull();
        setUpViewFull();
        return root;
    }

    public void setUpNewUpdateFull() {
        textView = root.findViewById(R.id.full_new_update);
        textView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryNewUpdateFull(limit, page);
            moveActivity(view, call, 0, "Full - Mới cập nhật");
        });
    }

    public void setUpAppreciationFull() {
        textView = root.findViewById(R.id.full_good_review);
        textView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryAppreciationFull(limit, page);
            moveActivity(view, call, 1 , "Full - Đánh giá cao");
        });
    }

    public void setUpLikeFull() {
        textView = root.findViewById(R.id.full_like);
        textView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryLikedFull(limit, page);
            moveActivity(view, call, 2, "Full - Yêu thích");
        });
    }


    public void setUpViewFull() {
        textView = root.findViewById(R.id.full_most_view);
        textView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getAllStoryViewedFull(limit, page);
            moveActivity(view, call, 3, "Full - Xem nhiều");
        });
    }

    public void moveActivity(View view, Call<List<Story>> call, int position, String title) {
        Intent intent = new Intent(view.getContext(), FullStoryHomeActivity.class);
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("listData", new Gson().toJson(response.body()));
                    bundle.putInt("api", position);
                    bundle.putString("title", title);
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