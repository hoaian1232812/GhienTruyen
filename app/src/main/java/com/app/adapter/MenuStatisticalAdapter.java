package com.app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.userdashboard.fragmentReuse.LikeFragment;
import com.app.userdashboard.fragmentReuse.RateFragment;
import com.app.userdashboard.fragmentReuse.ReadFragment;


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
                return new ReadFragment();
            case 2:
                return new RateFragment();
            default:
                return new LikeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
