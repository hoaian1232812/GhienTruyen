package com.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;

import com.app.user.HomeFragment;
import com.app.user.SearchFragment;
import com.app.user.TopicFragment;
import com.app.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    Fragment currentFragment = null;

    private SparseArray<Fragment> fragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.bottomNavigationView);
        fragmentSparseArray = new SparseArray<>();
        display(R.id.menuHome);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                display(item.getItemId());
                return false;
            }
        });
    }

    void display(int id) {
        Fragment fragment = fragmentSparseArray.get(id);
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
                    fragment = new UserFragment();
                    setTitle("Cá nhân");
                    break;
            }
            fragmentSparseArray.put(id, fragment);
        }

        if (currentFragment != fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                ft.show(fragment);

            } else {
                ft.add(R.id.content, fragment, String.valueOf(id));
            }
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            currentFragment = fragment;
            ft.commit();
        }
    }

}