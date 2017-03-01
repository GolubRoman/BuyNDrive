package com.golubroman.golub.warehouse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by User on 24.02.2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    /* Adapter for storing the photos on ViewPager
        */

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragment.onInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
