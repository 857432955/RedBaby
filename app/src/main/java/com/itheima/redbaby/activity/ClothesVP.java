package com.itheima.redbaby.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.itheima.redbaby.fragment.ClothesFragment;

/**
 * Created by Administrator on 2016/12/5.
 */
public class ClothesVP extends FragmentStatePagerAdapter {

    public ClothesVP(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ClothesFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
