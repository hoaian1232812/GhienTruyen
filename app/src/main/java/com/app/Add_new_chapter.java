package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.model.Story;
import com.google.android.material.textfield.TextInputEditText;

public class Add_new_chapter extends AppCompatActivity {
    TextInputEditText nameChapter, content;
    TextView nameChapterTitle, contentTitle;

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
            Story story = (Story) bundle.getSerializable("story");
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