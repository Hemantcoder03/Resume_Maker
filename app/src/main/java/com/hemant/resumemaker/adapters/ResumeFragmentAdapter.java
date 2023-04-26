package com.hemant.resumemaker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hemant.resumemaker.fragments.ResumeLayoutFragment;

public class ResumeFragmentAdapter extends FragmentStateAdapter {
    public ResumeFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ResumeLayoutFragment();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
