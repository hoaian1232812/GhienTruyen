package com.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;

import com.app.model.User;
import com.app.user.HomeFragment;
import com.app.user.SearchFragment;
import com.app.user.TopicFragment;
import com.app.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    User user;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottomNavigationView);
        display(R.id.menuHome);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                display(item.getItemId());
                return false;
            }
        });
    }

    void display(int id) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("" + id);
        if (fragment == null) {
            switch (id) {
                case R.id.menuHome:
                    fragment = new HomeFragment();
                    setTitle("Trang Chủ");
                    break;
                case R.id.topic:
                    fragment = new TopicFragment();
                    setTitle("Thể Loại");
                    break;
                case R.id.search:
                    fragment = new SearchFragment();
                    setTitle("Tìm Truyện");
                    break;
                case R.id.user:
                    user = User.getUserFromSharedPreferences(getSharedPreferences("MyPrefs", Context.MODE_PRIVATE));
                    Log.e("z", "troi oi");
                    if (user != null) {
                        Log.e("z", user.toString());
                        if (user.getId() != -1) {
                            startActivity(new Intent(MainActivity.this, UserDashBoardActivity.class));
                            return;
                        }
                        fragment = new UserFragment();
                        setTitle("Cáa nhân");
                    }
                    fragment = new UserFragment();
                    setTitle("Cáa nhân");

                    break;
            }
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment, "" + id);
        ft.commit();
    }


}