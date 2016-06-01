package com.mmsamiei.keeps;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Win2 on 5/26/2016.
 */
public class Pager extends FragmentActivity {
    ViewPager mpager;
    private TabLayout tabLayout;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        mpager=(ViewPager)findViewById(R.id.pager);
        MYAdapter myAdapter = new MYAdapter(getSupportFragmentManager());
        mpager.setAdapter(myAdapter);
        tabLayout = (TabLayout) findViewById(R.id.page_tab);
        tabLayout.setupWithViewPager(mpager);
    }

    private class MYAdapter extends FragmentStatePagerAdapter{

        public MYAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "tab 1 ";
                case 1:
                    return "tab 2";
                case 2:
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
