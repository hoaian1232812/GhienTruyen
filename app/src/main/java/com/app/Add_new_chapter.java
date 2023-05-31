package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.login_register.RegisterActivity;
import com.app.model.Story;
import com.app.service.ApiClient;
import com.app.service.ApiService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_new_chapter extends AppCompatActivity {
    TextInputEditText nameChapter, content;
    TextView nameChapterTitle, contentTitle;
    Button addChapter;
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_chapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameChapter = findViewById(R.id.nameChapter);
        nameChapterTitle = findViewById(R.id.popupTitleChapter);

        content = findViewById(R.id.content);
        contentTitle = findViewById(R.id.popupTitleContent);

        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            story = (Story) bundle.getSerializable("story");
            setTitle(story.getTitle());
        }

        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus || content.length() != 0) {
                    int translationY = -convertDpToPixels(25); // Convert dp to pixels
                    contentTitle.animate().translationY(translationY).setDuration(200).start();
                } else {
                    // Handle the case when EditText loses focus (optional)
                    int translationY = 0; // Move back to the original position
                    contentTitle.animate().translationY(translationY).setDuration(200).start();
                }
            }
        });

        nameChapter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus || nameChapter.length() != 0) {
                    int translationY = -convertDpToPixels(25); // Convert dp to pixels
                    nameChapterTitle.animate().translationY(translationY).setDuration(200).start();
                } else {
                    // Handle the case when EditText loses focus (optional)
                    int translationY = 0; // Move back to the original position
                    nameChapterTitle.animate().translationY(translationY).setDuration(200).start();
                }
            }
        });

        addChapter = findViewById(R.id.addChapter);
        addChapter.setOnClickListener(onClicked(story.getId()));
    }

    private View.OnClickListener onClicked(int id) {
        return view -> {
            String n = nameChapter.getText().toString();
            String c = content.getText().toString();
            if (n.isBlank()) {
                Toast.makeText(this, "Bạn chưa nhập tên Chapter", Toast.LENGTH_SHORT).show();
                return;
            } else if (c.isBlank()) {
                Toast.makeText(this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }
            addNewChapter(story.getId(), nameChapter.getText().toString(), content.getText().toString());
        };
    }

    private void addNewChapter(int idStory, String name, String content) {
        Call<JsonObject> call = ApiClient.getApiService().createChapter(idStory, name, content);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Add_new_chapter.this, response.body().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Add_new_chapter.this, "Không thể thêm Chapter. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int convertDpToPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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