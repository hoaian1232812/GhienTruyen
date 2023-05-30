package com.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    // Lấy đường dẫn tệp tin ảnh từ Uri
    public String getPathFromUri(Context context, Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;

    private int convertDpToPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Glide.with(this)
                    .asBitmap()
                    .load(selectedImageUri)
                    .apply(RequestOptions.overrideOf(800, 800)) // Kích thước tối đa sau khi nén
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            // Nén lại ảnh
                            Bitmap compressedBitmap = compressBitmap(bitmap, 1); // 80 là chất lượng nén (từ 0 đến 100)

                            // Chuyển đổi Bitmap thành byte array
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream);
                            byte[] imageBytes = byteArrayOutputStream.toByteArray();

                            // Gửi byte array đến API
                            Call<JsonObject> call = ApiClient.getApiService().uploadImg(imageBytes, "image.jpg");
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(Add_new_story.this, response.body().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(Add_new_story.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
            imageView.setImageURI(selectedImageUri);
            selectImageButton.setVisibility(View.GONE);
        }
    }
    private Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        float maxDimen = Math.max(imageWidth, imageHeight);
        float scale = maxDimen / 800; // Kích thước tối đa sau khi nén
        options.inJustDecodeBounds = false;
        options.inSampleSize = Math.round(scale);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
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