package com.example.android.bakingbuddy.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.android.bakingbuddy.ui.IngredientsFragment;
import com.example.android.bakingbuddy.ui.StepsFragment;

/**
 * Created by pkennedy on 3/25/18.
 */

public class OverviewFragmentPagerAdapter extends FragmentPagerAdapter {

    public OverviewFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new IngredientsFragment();
        } else {
            return new StepsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
