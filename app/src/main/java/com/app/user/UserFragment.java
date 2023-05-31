package com.app.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.R;
import com.app.login_register.LoginActivity;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.app.userdashboard.ReadLikeStoriesActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {
    RelativeLayout goToLogin;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user, container, false);

        RelativeLayout btnGoToLogin = root.findViewById(R.id.go_to_login);
        btnGoToLogin.setOnClickListener(onGoToLoginClicked());
        setUpRead();
        setUpLike();
        return root;
    }

    private View.OnClickListener onGoToLoginClicked() {
        return view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        };
    }

    private void setUpRead() {
        CardView cardView = root.findViewById(R.id.read_drive);
        cardView.setOnClickListener(view -> {
            List<Story> storyList = new ArrayList<>();
            String deviceId = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            String uniqueName = "user_preferences_" + deviceId;
            SharedPreferences userPreferences = root.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
            Map<String, ?> map = userPreferences.getAll();
            Gson gson = new Gson();
            for (String s : map.keySet()) {
                Object value = map.get(s);
                if (value instanceof String && s.startsWith("story_") && s.endsWith("_read")) {
                    storyList.add(gson.fromJson((String) value, Story.class));
                }
            }
            moveActivity(view, storyList, "Truyện đã đọc");
        });
    }

    private void setUpLike() {
        CardView cardView = root.findViewById(R.id.like_drive);
        cardView.setOnClickListener(view -> {
            List<Story> storyList = new ArrayList<>();
            String deviceId = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            String uniqueName = "user_preferences_" + deviceId;
            SharedPreferences userPreferences = root.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
            Map<String, ?> map = userPreferences.getAll();
            Gson gson = new Gson();
            for (String s : map.keySet()) {
                Object value = map.get(s);
                if (value instanceof String && s.startsWith("story_") && s.endsWith("_favorite")) {
                    storyList.add(gson.fromJson((String) value, Story.class));
                }
            }
            moveActivity(view, storyList, "Danh sách yêu thích");
        });
    }

    private void moveActivity(View view, List<Story> stories, String title) {
        Intent intent = new Intent(view.getContext(), ReadLikeDeviceActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("listData", new Gson().toJson(stories));
        bundle.putString("title", title);
        intent.putExtra("data", bundle);
        view.getContext().startActivity(intent);

    }


}