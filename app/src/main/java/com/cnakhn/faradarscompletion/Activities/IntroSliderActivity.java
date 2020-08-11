package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.IntroSliderPrefManager;
import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.button.MaterialButton;

public class IntroSliderActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private LinearLayout layoutDots;
    private MaterialButton btnSkip, btnNext;
    private int[] layoutIDs = {R.layout.layout_intro_slider_1, R.layout.layout_intro_slider_2, R.layout.layout_intro_slider_3, R.layout.layout_intro_slider_4};
    private SliderPagerAdapter pagerAdapter;
    private IntroSliderPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        initViews();

    }

    private void initViews() {
        viewPager = findViewById(R.id.intro_slider_view_pager);
        layoutDots = findViewById(R.id.layout_dots);
        btnSkip = findViewById(R.id.btn_intro_slider_skip);
        btnSkip.setOnClickListener(this);
        btnNext = findViewById(R.id.btn_intro_slider_next);
        btnNext.setOnClickListener(this);
        pagerAdapter = new SliderPagerAdapter();
        showDots(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showDots(position);
                if (position == viewPager.getAdapter().getCount() - 1) {
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnNext.setText("Got It!");
                } else {
                    btnSkip.setVisibility(View.VISIBLE);
                    btnNext.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
    }

    private void showDots(int pageNumber) {
        TextView[] tvDots = new TextView[layoutIDs.length];
        layoutDots.removeAllViews();
        for (int i = 0; i < tvDots.length; i++) {
            tvDots[i] = new TextView(this);
            tvDots[i].setText(Html.fromHtml("&#8226;"));
            tvDots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
            tvDots[i].setTextColor(ContextCompat.getColor(this, (i == pageNumber ? R.color.colorEnableDot : R.color.colorDisableDot)));
            layoutDots.addView(tvDots[i]);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSkip)) {
            finish();
        }

        if (v.equals(btnNext)) {
            int currentPage = viewPager.getCurrentItem();
            int lastPage = viewPager.getAdapter().getCount() - 1;
            if (currentPage == lastPage) {
                finish();
            } else {
                viewPager.setCurrentItem(currentPage + 1);
            }
        }
    }

    public class SliderPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(IntroSliderActivity.this).inflate(layoutIDs[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layoutIDs.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}