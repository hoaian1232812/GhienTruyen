package com.app.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.user.FavoriteFragment;
import com.app.user.FullFragment;
import com.app.user.MostViewFragment;
import com.app.user.UpdateFragment;
import com.app.userdashboard.LikeFragment;

public class MenuStatisticalAdapter extends FragmentStateAdapter {

    public MenuStatisticalAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                return new LikeFragment();
            case 1:
                return new Fragment();
            case 2:
                return new Fragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
