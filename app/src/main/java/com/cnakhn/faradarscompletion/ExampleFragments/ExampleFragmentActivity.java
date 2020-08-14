package com.cnakhn.faradarscompletion.ExampleFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExampleFragmentActivity extends AppCompatActivity implements ExampleDialogFragment.ExampleDialogCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_fragment);
        ViewPager2 viewPager2 = findViewById(R.id.example_viewpager);
        TabLayout tabLayout = findViewById(R.id.tabLayout_example_fragment);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("A");
                        break;
                    case 1:
                        tab.setText("B");
                        break;
                    case 2:
                        tab.setText("C");
                        break;
                    case 3:
                        tab.setText("D");
                        break;
                    case 4:
                        tab.setText("E");
                        break;
                    default:
                        tab.setText("");
                        break;
                }
            }
        });

        SlideAdapter slideAdapter = new SlideAdapter(this);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setCurrentItem(3);
        viewPager2.setAdapter(slideAdapter);
        mediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Example Dialog Fragment").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ExampleDialogFragment dialogFragment = new ExampleDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), null);
                return false;
            }
        });

        menu.add("Example Bottom Sheet Dialog Fragment").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ExampleBottomSheetDialogFragment bottomSheetDialogFragment = new ExampleBottomSheetDialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), null);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSubmit(String data) {
        TextView textView = findViewById(R.id.txt_example_fragment);
        textView.setText(data);
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
    }
}