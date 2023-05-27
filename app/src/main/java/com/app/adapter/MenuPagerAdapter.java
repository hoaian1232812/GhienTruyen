package com.app.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.user.TypeStoryTopicFragment;

public class MenuPagerAdapter extends FragmentStateAdapter {
    Bundle bundle;

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TypeStoryTopicFragment();
                this.bundle.putInt("type", 0);
                fragment.setArguments(this.bundle);
                return fragment;
            case 1:
                fragment = new TypeStoryTopicFragment();
                this.bundle.putInt("type", 1);
                fragment.setArguments(this.bundle);
                return fragment;
            case 2:
                fragment = new TypeStoryTopicFragment();
                this.bundle.putInt("type", 2);
                fragment.setArguments(this.bundle);
                return fragment;
            case 3:
                fragment = new TypeStoryTopicFragment();
                this.bundle.putInt("type", 3);
                fragment.setArguments(this.bundle);
                return fragment;
            default:
                fragment = new TypeStoryTopicFragment();
                this.bundle.putInt("type", 0);
                fragment.setArguments(this.bundle);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
