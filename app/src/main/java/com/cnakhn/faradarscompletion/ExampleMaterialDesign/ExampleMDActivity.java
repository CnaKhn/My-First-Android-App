package com.cnakhn.faradarscompletion.ExampleMaterialDesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.cnakhn.faradarscompletion.Fragments.HomeFragment;
import com.cnakhn.faradarscompletion.Fragments.ProfileFragment;
import com.cnakhn.faradarscompletion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExampleMDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_md);

        final FragmentTransaction mainTransaction = getSupportFragmentManager().beginTransaction();
        mainTransaction.add(R.id.frame_example_mdContainer, new ExampleMDFragment());
        mainTransaction.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_example_md);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.menu_bottom_nav_home_example_md:
                        transaction.replace(R.id.frame_example_mdContainer, new ExampleMDFragment());
                        /*
                        * for main fragments in activity that has no fragment behind it better to don't call "addToBackStack"
                        * cuz it will make the client tap the back button twice and that's kinda bad UX...*/
                        //transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.menu_bottom_nav_share_example_md:
                        transaction.replace(R.id.frame_example_mdContainer, new FragmentShare());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.menu_bottom_nav_profile_example_md:
                        transaction.replace(R.id.frame_example_mdContainer, new ProfileFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_home_example_md);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

    }
}