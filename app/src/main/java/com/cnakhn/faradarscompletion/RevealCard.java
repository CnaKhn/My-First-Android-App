package com.cnakhn.faradarscompletion;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.button.MaterialButton;

import java.util.jar.Attributes;

public class RevealCard extends RelativeLayout implements View.OnClickListener {
    public static final long REVEAL_SHOW_DURATION = 700;
    public static final long REVEAL_HIDE_DURATION = 350;
    private View view;
    private ImageView bgCover, imageSocial;
    private CardView btnProfile, btnShare;
    private RelativeLayout layoutReveal;
    private LinearLayout layoutButtons;
    private MaterialButton btnSndMsgReveal;
    private Button btnProfileReveal, btnShareReveal;

    public RevealCard(Context context) {
        super(context);
        initViews(context);
    }

    public RevealCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        view = inflate(context, R.layout.layout_circular_reveal, this);
        bgCover = view.findViewById(R.id.background_cover);
        btnProfile = view.findViewById(R.id.btn_profile);
        btnShare = view.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(this);
        layoutReveal = view.findViewById(R.id.layout_reveal);
        layoutButtons = view.findViewById(R.id.layout_buttons);
        btnProfileReveal = view.findViewById(R.id.btn_profile_reveal);
        btnSndMsgReveal = view.findViewById(R.id.btn_sendmsg_reveal);
        btnShareReveal = view.findViewById(R.id.btn_share_reveal);
        imageSocial = view.findViewById(R.id.image_social);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnShare)) {
            int centerX = (btnShare.getRight() + btnShare.getLeft()) / 2;
            int centerY = (btnShare.getBottom() + btnShare.getTop()) / 2;
            float radius = (float) Math.hypot(bgCover.getWidth(), bgCover.getHeight());
            if (layoutReveal.getVisibility() == VISIBLE) {
                hide(centerX, centerY, radius);
            } else {
                show(centerX, centerY, radius);
            }
        }


    }

    private void show(int centerX, int centerY, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, 0, radius);
            animator.setDuration(REVEAL_SHOW_DURATION);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    layoutReveal.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Animation fadeIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
                    layoutButtons.startAnimation(fadeIn);
                    layoutButtons.setVisibility(VISIBLE);
                    imageSocial.setImageResource(R.drawable.ic_exit);
                    imageSocial.startAnimation(fadeIn);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    private void hide(int centerX, int centerY, float radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, radius, 0);
            animator.setDuration(REVEAL_HIDE_DURATION);
            final Animation fadeOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    layoutButtons.startAnimation(fadeOut);
                    layoutButtons.setVisibility(GONE);
                    imageSocial.setImageResource(R.drawable.ic_cursor);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    layoutReveal.setVisibility(GONE);
                    imageSocial.startAnimation(fadeOut);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

}
