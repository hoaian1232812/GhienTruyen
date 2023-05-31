package com.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;

import com.app.admins.ManageAccountFragment;
import com.app.admins.NotificationsAdminFragment;
import com.app.admins.StoryAdminFragment;
import com.app.admins.UserAdminFragment;
import com.app.model.User;
import com.app.user.HomeFragment;
import com.app.user.SearchFragment;
import com.app.user.TopicFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    Fragment currentFragment = null;
    User user;

    SparseArray<Fragment> fragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bnv = findViewById(R.id.bottomNavigationView);
        fragmentSparseArray = new SparseArray<>();
        display(R.id.stories);
        bnv.setOnItemSelectedListener(item -> {
            item.setChecked(true);
            display(item.getItemId());
            return false;
        });
    }

    void display(int id) {
        Fragment fragment = fragmentSparseArray.get(id);
        if (fragment == null) {
            switch (id) {
                case R.id.stories:
                    fragment = new StoryAdminFragment();
                    setTitle("Quản lí truyện");
                    break;
                case R.id.ql_user:
                    fragment = new ManageAccountFragment();
                    setTitle("Quản lí tài khoản");
                    break;
                case R.id.notifications:
                    fragment = new NotificationsAdminFragment();
                    setTitle("Thông báo");
                    break;
                case R.id.manage_account:
                    fragment = new UserAdminFragment();
                    setTitle("Cá nhân");
                    break;
            }
            fragmentSparseArray.put(id, fragment);
        }

        if (currentFragment != fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                ft.show(fragment);
                switch (id) {
                    case R.id.stories:
                        setTitle("Quản lí truyện");
                        break;
                    case R.id.ql_user:
                        setTitle("Quản lí tài khoản");
                        break;
                    case R.id.notifications:
                        setTitle("Thông báo");
                        break;
                    case R.id.manage_account:
                        setTitle("Cá nhân");
                        break;
                }
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