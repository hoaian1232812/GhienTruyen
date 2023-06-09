package com.app.userdashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.StoryAdapter;
import com.app.login_register.LoginActivity;
import com.app.model.Story;
import com.app.model.User;
import com.app.service.ApiClient;
import com.app.user.TrendingStoryHomeActivity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFeatureDashBoardFragment extends Fragment {
    CardView btnLogout;
    View view;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_feature, container, false);

        user = User.getUserFromSharedPreferences(getActivity());
        Log.e("d", user.toString());
        setUpLikeUser();
        setUpReadUser();
        btnLogout = view.findViewById(R.id.btn_log_out);
        btnLogout.setOnClickListener(logOut());

        return view;
    }

    private View.OnClickListener logOut() {
        return view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Bạn có muốn đăng xuất không")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearUserFromSharedPreferences();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                            Toast.makeText(getContext(), "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        };
    }

    private void clearUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void setUpReadUser() {
        CardView cardView = view.findViewById(R.id.go_to_list_read_story);
        cardView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getStoriesUserRead(user.getId(), 15, 1);
            moveActivity(view, call, 0, "Truyện đã xem");
        });
    }

    private void setUpLikeUser() {
        CardView cardView = view.findViewById(R.id.go_to_favorite);
        cardView.setOnClickListener(view -> {
            Call<List<Story>> call = ApiClient.getApiService().getStoriesUserLiked(user.getId(), 15, 1);
            moveActivity(view, call, 1, "Danh sách yêu thích");
        });
    }

    private void moveActivity(View view, Call<List<Story>> call, int position, String title) {
        Intent intent = new Intent(view.getContext(), ReadLikeStoriesActivity.class);
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
                Toast.makeText(view.getContext(), "Không có truyện nào", Toast.LENGTH_SHORT).show();
            }
        });
    }


}