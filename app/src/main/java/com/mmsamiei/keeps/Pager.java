package com.mmsamiei.keeps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Win2 on 5/26/2016.
 */
public class Pager extends Activity {
    ViewPager mpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
       mpager = (ViewPager) findViewById(R.id.pager);
    }

    private class MYAdapter extends FragmentStatePagerAdapter{

        public MYAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 1:
                    return "tab 1 ";
                case 2:
                    return "tab 2";
                case 3:
                    return "tab 3";
                default:
                    return "null";
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
