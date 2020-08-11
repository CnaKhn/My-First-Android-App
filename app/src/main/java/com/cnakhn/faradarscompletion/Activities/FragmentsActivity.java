package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.cnakhn.faradarscompletion.Fragments.HomeFragment;
import com.cnakhn.faradarscompletion.Fragments.ProfileFragment;
import com.cnakhn.faradarscompletion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentsActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        navigationView = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_container, new HomeFragment()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_user_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity_container, selectedFragment).commit();
                return true;
            }
        });
    }
}