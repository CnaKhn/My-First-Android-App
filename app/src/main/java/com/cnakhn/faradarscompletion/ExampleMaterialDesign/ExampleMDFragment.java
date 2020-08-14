package com.cnakhn.faradarscompletion.ExampleMaterialDesign;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ExampleMDFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example_md, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = view.findViewById(R.id.toolbar_example_md);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout_example_md);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, 0, 0);

        drawerLayout.addDrawerListener(drawerToggle);

        MaterialCardView cardView = view.findViewById(R.id.card_products);

        // Animations here...
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        /*alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatMode(Animation.REVERSE);*/

        ScaleAnimation scaleAnimation =
                new ScaleAnimation(1, 0.5F, 1, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        //scaleAnimation.setRepeatCount(Animation.INFINITE);                settled with Animation Set
        //scaleAnimation.setDuration(1250);                                 settled with Animation Set
        //scaleAnimation.setRepeatMode(Animation.REVERSE);                  settled with Animation Set
        //scaleAnimation.setInterpolator(new AccelerateInterpolator());     settled with Animation Set

        //TranslateAnimation translateAnimation = new TranslateAnimation(0, -200, 0, -500);

        //RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);

        // Animation Set
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setDuration(1250);

        cardView.startAnimation(animationSet);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_example_mdContainer, new ExampleMDDetailFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        FloatingActionButton button = view.findViewById(R.id.fbtn_example_md);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "Floating Action Button Clicked!", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }

}
