package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int NO_WINNER = -1;
    private static final int NOT_PLAYED = 0;
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;
    private int currentPlayer = PLAYER_X;
    private int winner = NO_WINNER;
    private int[] status = {NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED, NOT_PLAYED};
    private int[][] winLocations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private ImageView btnReset;
    private RelativeLayout winnerCard, layoutTicTacToe;
    private TextView winnerMsg;
    private MaterialButton btnCardReset;
    private AlphaAnimation alphaAnimation;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initViews();
    }

    private void initViews() {
        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(this);
        winnerCard = findViewById(R.id.winner_card);
        winnerMsg = findViewById(R.id.winner_msg);
        btnCardReset = findViewById(R.id.btn_card_reset);
        btnCardReset.setOnClickListener(this);
        layoutTicTacToe = findViewById(R.id.layout_tic_tac_toe);
    }

    public void dropIn(View view) {
        int tag = Integer.parseInt((String) view.getTag());
        if (winner != NO_WINNER || status[tag] != NOT_PLAYED) return;

        ImageView img = (ImageView) view;
        if (currentPlayer == PLAYER_X) {
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(100);
            alphaAnimation.setInterpolator(new AccelerateInterpolator());
            img.startAnimation(alphaAnimation);

            img.setImageResource(R.drawable.ic_x);
            status[tag] = PLAYER_X;
            currentPlayer = PLAYER_O;
        } else if (currentPlayer == PLAYER_O) {
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(100);
            alphaAnimation.setInterpolator(new AccelerateInterpolator());
            img.startAnimation(alphaAnimation);

            img.setImageResource(R.drawable.ic_o);
            status[tag] = PLAYER_O;
            currentPlayer = PLAYER_X;
        }

        prepareWinnerCard();
    }

    public int checkWinner() {
        for (int[] positions : winLocations) {
            if (status[positions[0]] == status[positions[1]] && status[positions[1]] == status[positions[2]] && status[positions[0]] != NOT_PLAYED) {
                return status[positions[0]];
            }
        }
        return NO_WINNER;
    }

    public boolean filled() {
        for (int i = 0; i < status.length; i++) {
            if (status[i] == NOT_PLAYED) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = PLAYER_X;
        winner = NO_WINNER;
        Arrays.fill(status, NOT_PLAYED);
        if (valueAnimator != null) valueAnimator.removeAllUpdateListeners();
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        layoutTicTacToe.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(100);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        if (winnerCard.getVisibility() == View.VISIBLE) {
            winnerCard.startAnimation(alphaAnimation);
            winnerCard.setVisibility(View.GONE);
        }
        GridLayout pgLayout = findViewById(R.id.pg_layout);
        for (int i = 0; i < pgLayout.getChildCount(); i++) {
            ImageView imageView = (pgLayout.getChildAt(i) instanceof ImageView) ? (ImageView) pgLayout.getChildAt(i) : null;
            if (imageView == null) return;
            imageView.setImageResource(0);
        }
    }

    private void prepareWinnerCard() {
        winner = checkWinner();
        if (winner != NO_WINNER || filled()) {
            Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorOrangeDark));
            showValueAnimator();
            String msg = (winner == NO_WINNER) ? "Draw =)" : (winner == PLAYER_X) ? "'X' Winner!" : "'O' Winner!";
            winnerMsg.setText(msg);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(100);
            alphaAnimation.setInterpolator(new AccelerateInterpolator());
            winnerCard.startAnimation(alphaAnimation);
            winnerCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnReset) || v.equals(btnCardReset)) {
            resetGame();
        }
    }

    private void showValueAnimator() {

        valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                ContextCompat.getColor(this, R.color.colorOrange), ContextCompat.getColor(this, R.color.colorOrangeDark));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutTicTacToe.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }
}