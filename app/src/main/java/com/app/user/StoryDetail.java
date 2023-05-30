package com.app.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.R;
import com.app.adapter.TopicStoryDetailAdapter;
import com.app.model.Chapter;
import com.app.model.Story;
import com.app.model.TimeStory;
import com.app.model.User;
import com.app.service.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryDetail extends AppCompatActivity {
    Story story;
    String name;
    TextView title, author, status, like, chapter, view, time, intro;
    ImageView img;
    RecyclerView recyclerView;
    TopicStoryDetailAdapter adapter;
    LinearLayoutManager layout;
    User user;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Chi Tiết Truyện");
        setContentView(R.layout.activity_story_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        story = (Story) getIntent().getBundleExtra("data").get("story");
        setUpView();
        setUpRecyclerView();
        setClickAuthor();
        setComment();
        setChap();
        setLike();
    }

    private void setUpRecyclerView() {
        layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.topic);
        recyclerView.setLayoutManager(layout);
        adapter = new TopicStoryDetailAdapter(story.getTopics());
        recyclerView.setAdapter(adapter);
    }

    private void setUpView() {
        title = findViewById(R.id.title);
        title.setText(story.getTitle());
        author = findViewById(R.id.author);
        CompletableFuture<User> futureName = story.getNameAuthor();
        futureName.thenAccept(user -> {
            name = user.getName();
            author.setText(user.getName());
        }).exceptionally(e -> {
            return null;
        });
        time = findViewById(R.id.time);
        CompletableFuture<TimeStory> futureTime = story.getTime();
        futureTime.thenAccept(timeStory -> {
            time.setText(timeStory.showDateTime());
        }).exceptionally(e -> {
            return null;
        });
        chapter = findViewById(R.id.chapter);
        CompletableFuture<List<Chapter>> future = story.getAllChapter();
        future.thenAccept(chapters -> {
            chapter.setText(chapters.size() + " Chapter");
        }).exceptionally(e -> {
            return null;
        });
        status = findViewById(R.id.status);
        status.setText(story.getStatus());
        like = findViewById(R.id.like);
        like.setText("" + story.getLikes());
        view = findViewById(R.id.eye);
        view.setText("" + story.getViews());
        img = findViewById(R.id.img_story);
        Glide.with(img.getContext())
                .load("http://139.180.129.238:8080/Untitled1.jpg")
                .transform(new CircleCrop())
                .into(img);
        intro = findViewById(R.id.introduce);
        intro.setText(story.getIntroduce());
    }

    public void setClickAuthor() {
        author.setOnClickListener(view -> {
            Intent intent = new Intent(this, StoryAuthorActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", story.getAuthor_id());
            bundle.putString("name", name);
            intent.putExtra("data", bundle);
            startActivity(intent);
        });
    }

    public void setChap() {
        LinearLayout chap = findViewById(R.id.chaps);
        chap.setOnClickListener(view -> {
            CompletableFuture<List<Chapter>> futureChapter = story.getAllChapter();
            Intent intent = new Intent(view.getContext(), ChapterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("story", story);
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });
    }

    public void setComment() {
        LinearLayout comment = findViewById(R.id.com);
        comment.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idStory", story.getId());
            bundle.putString("title", story.getTitle());
            intent.putExtra("data", bundle);
            view.getContext().startActivity(intent);
        });

    }

    public void setLike() {
        imageView = findViewById(R.id.favorite);
        LinearLayout like = findViewById(R.id.favorite_layout);
        setUpFavorite();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    Call<JsonObject> call = ApiClient.getApiService().updateLike(user.getId(), story.getId());
                    call.enqueue(new Callback<JsonObject>() {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(StoryDetail.this, response.body().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                if (response.body().get("status").getAsInt() == 0) {
                                    imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24));
                                } else {
                                    imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                } else {
                    String deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    String uniqueName = "user_preferences_" + deviceId;
                    SharedPreferences userPreferences = view.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
                    Map<String, ?> map = userPreferences.getAll();
                    SharedPreferences.Editor editor = userPreferences.edit();
                    Gson gson = new Gson();
                    for (String s : map.keySet()) {
                        Object value = map.get(s);
                        if (value instanceof String && s.startsWith("story_") && s.endsWith("_favorite")) {
                            if (story.getId() == (gson.fromJson((String) value, Story.class).getId())) {
                                editor.remove(s);
                                imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24));
                                Toast.makeText(StoryDetail.this, "Bỏ thích thành công", Toast.LENGTH_SHORT).show();
                                editor.apply();
                                return;
                            }
                        }
                    }
                    editor.putString("story_" + story.getId() + "_favorite", gson.toJson(story));
                    imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24));
                    Toast.makeText(StoryDetail.this, "Thích thành công", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });
    }

    private void setUpFavorite() {
        if ((user = User.getUserFromSharedPreferences(StoryDetail.this)) != null) {

            Call<List<Story>> call = ApiClient.getApiService().getStoryLikeByUser(user.getId());

            call.enqueue(new Callback<List<Story>>() {
                @Override
                public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                    if (response.isSuccessful()) {
                        checkExistLike(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Story>> call, Throwable t) {

                }
            });
        } else {
            String deviceId = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            String uniqueName = "user_preferences_" + deviceId;
            SharedPreferences userPreferences = view.getContext().getSharedPreferences(uniqueName, Context.MODE_PRIVATE);
            Map<String, ?> map = userPreferences.getAll();
            List<Story> storyList = new ArrayList<>();
            Gson gson = new Gson();
            for (String s : map.keySet()) {
                Object value = map.get(s);
                if (value instanceof String && s.startsWith("story_") && s.endsWith("_favorite")) {
                    storyList.add(gson.fromJson((String) value, Story.class));
                }
            }
            checkExistLike(storyList);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void checkExistLike(List<Story> list) {
        for (Story s : list) {
            if (s.getId() == story.getId()) {
                imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24));
                return;
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24));
                return;
            }

        }
    }
}