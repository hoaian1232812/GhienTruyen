package com.app.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.userdashboard.fragmentReuse.LikeFragment;
import com.app.userdashboard.fragmentReuse.LikeStoryFragment;
import com.app.userdashboard.fragmentReuse.RateFragment;
import com.app.userdashboard.fragmentReuse.RateStoryFragment;
import com.app.userdashboard.fragmentReuse.ReadFragment;
import com.app.userdashboard.fragmentReuse.ReadStoryFragMent;

public class MenuStoryStatisticalAdapter extends FragmentStateAdapter {

    Bundle bundle;

    public MenuStoryStatisticalAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LikeStoryFragment();
                break;
            case 1:
                fragment = new ReadStoryFragMent();
                break;
            case 2:
                fragment = new RateStoryFragment();
                break;
            default:
                fragment = new LikeStoryFragment();
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
