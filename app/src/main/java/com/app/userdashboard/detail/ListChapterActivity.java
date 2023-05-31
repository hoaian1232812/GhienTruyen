package com.app.userdashboard.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.Add_new_chapter;
import com.app.Add_new_story;
import com.app.R;
import com.app.adapter.ListChapterAdapter;
import com.app.adapter.StoryListBoardAdapter;
import com.app.model.Chapter;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.app.user.ChapterActivity;
import com.app.user.ChapterDetailActivity;
import com.app.userdashboard.StoryStatisticalActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChapterActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    ListChapterAdapter adapter;
    RecyclerView recyclerView;
    Bundle bundle;
    Intent intent;
    boolean isLoading = false;
    ProgressBar pb_loading;
    private int limit = 15;
    private int page = 1;
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        story = (Story) getIntent().getBundleExtra("data").getSerializable("story");
        setTitle(story.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycle_list_chapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<List<Chapter>> call = ApiClient.getApiService().getAllChapterByStory(story.getId());

        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        adapter = new ListChapterAdapter(response.body());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chapter_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_add:
                //code xử lý khi bấm menu1
                intent = new Intent(this, Add_new_chapter.class);
                bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtra("data", bundle);
                this.startActivity(intent);
                break;
            case R.id.menu_thong_ke:
                //code xử lý khi bấm menu2
                intent = new Intent(this, StoryStatisticalActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("story", story);
                intent.putExtra("data", bundle);
                this.startActivity(intent);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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

}