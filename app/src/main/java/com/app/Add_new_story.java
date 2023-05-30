package com.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.textfield.TextInputEditText;

public class Add_new_story extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ImageView imageView;
    private Button selectImageButton;
    TextInputEditText nameStory, introduce;
    TextView nameStoryTV, popupTitleIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_story);
        imageView = findViewById(R.id.imageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        imageView = findViewById(R.id.imageView);

        nameStory = findViewById(R.id.nameStory);
        nameStoryTV = findViewById(R.id.popupTitle);

        nameStory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus || nameStory.length() != 0) {
                    int translationY = -convertDpToPixels(25); // Convert dp to pixels
                    nameStoryTV.animate().translationY(translationY).setDuration(200).start();
                } else {
                    // Handle the case when EditText loses focus (optional)
                    int translationY = 0; // Move back to the original position
                    nameStoryTV.animate().translationY(translationY).setDuration(200).start();
                }
            }
        });

        introduce = findViewById(R.id.introduce);
        popupTitleIntroduce = findViewById(R.id.popupTitleIntroduce);
        introduce.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus || introduce.length() != 0) {
                    int translationY = -convertDpToPixels(25); // Convert dp to pixels
                    popupTitleIntroduce.animate().translationY(translationY).setDuration(200).start();
                } else {
                    // Handle the case when EditText loses focus (optional)
                    int translationY = 0; // Move back to the original position
                    popupTitleIntroduce.animate().translationY(translationY).setDuration(200).start();
                }
            }
        });


        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }
        });
    }

    private int convertDpToPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
            selectImageButton.setVisibility(View.GONE);
        }
    }


}