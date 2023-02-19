package com.example.hackathon.core;


import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public abstract class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        try {
            super.setPrimaryItem(container, position, object);
        } catch (Exception e) {
        }
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
//        super.restoreState(state, loader);
    }
}
