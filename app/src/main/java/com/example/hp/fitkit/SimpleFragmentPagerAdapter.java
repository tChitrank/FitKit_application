package com.example.hp.fitkit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext=context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BmiFragment();
        } else if (position == 1){
            return new MealsFragment();
        } else  {
            return new WorkoutFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) {
            return mContext.getString(R.string.category_bmi);
        }
        else if(position==1) {
            return mContext.getString(R.string.category_Meal);
        }
        else {
            return mContext.getString(R.string.category_Workout);
        }
    }
}
