package com.cnakhn.faradarscompletion.Activities;

import android.os.Bundle;

import com.cnakhn.faradarscompletion.Fragments.SlideFragment;
import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MultiTabActivity extends AppCompatActivity {
    ViewPager viewPager;
    SlidePagerAdapter pagerAdapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_tab);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        iniViewPager();
    }

    private void initSlides() {
        int[] imageIDs = new int[]{R.drawable.ic_user, R.drawable.ic_skills, R.drawable.ic_heart, R.drawable.ic_cursor};
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] contents = getResources().getStringArray(R.array.contents);

        for (int i = 0; i < titles.length; i++) {
            pagerAdapter.addFragment(SlideFragment.newSlide(imageIDs[i], titles[i], contents[i]), titles[i]);

        }
    }

    private void iniViewPager() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < tabs.getChildCount(); i++) {
            ViewGroup tab = (ViewGroup) tabs.getChildAt(i);
            for (int j = 0; j < tab.getChildCount(); j++) {

            }
        }
        initSlides();
    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String> tabTitles;

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            tabTitles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public void addFragment(Fragment fragment, String tabTitle) {
            fragments.add(fragment);
            tabTitles.add(tabTitle);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}