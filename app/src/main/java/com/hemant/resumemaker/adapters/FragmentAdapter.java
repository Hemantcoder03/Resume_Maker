package com.hemant.resumemaker.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hemant.resumemaker.fragments.AboutFragment;
import com.hemant.resumemaker.fragments.AchievementsFragment;
import com.hemant.resumemaker.fragments.EducationFragment;
import com.hemant.resumemaker.fragments.HobbiesFragment;
import com.hemant.resumemaker.fragments.LanguageFragment;
import com.hemant.resumemaker.fragments.PersonalInfoFragment;
import com.hemant.resumemaker.fragments.ProjectsDetailsFragment;
import com.hemant.resumemaker.fragments.SignatureFragment;
import com.hemant.resumemaker.fragments.SkillFragment;
import com.hemant.resumemaker.fragments.WorkExperienceFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new PersonalInfoFragment();
            case 1:
                return new AboutFragment();
            case 2:
                return new EducationFragment();
            case 3:
                return new SkillFragment();
            case 4:
                return new WorkExperienceFragment();
            case 5:
                return new AchievementsFragment();
            case 6:
                return new ProjectsDetailsFragment();
            case 7:
                return new HobbiesFragment();
            case 8:
                return new LanguageFragment();
            case 9:
                return new SignatureFragment();
            default:
                return new PersonalInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
