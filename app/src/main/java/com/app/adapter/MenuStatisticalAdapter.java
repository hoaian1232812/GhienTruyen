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

public class MenuStatisticalAdapter extends FragmentStateAdapter {
    Bundle bundle;

    public MenuStatisticalAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new UpdateFragment();
                fragment.setArguments(this.bundle);
                return fragment;
            case 1:
                fragment = new FullFragment();
                fragment.setArguments(this.bundle);
                return fragment;
            case 2:
                fragment = new MostViewFragment();
                fragment.setArguments(this.bundle);
                return fragment;
            case 3:
                fragment = new FavoriteFragment();
                fragment.setArguments(this.bundle);
                return fragment;
            default:
                fragment = new UpdateFragment();
                fragment.setArguments(this.bundle);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
