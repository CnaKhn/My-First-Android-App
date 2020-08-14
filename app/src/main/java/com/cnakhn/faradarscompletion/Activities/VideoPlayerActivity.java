package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayerActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaPlayer player;
    private TextView currentDuration;
    private TextView duration;
    private SeekBar videoBar;
    private Timer timer;
    private ImageButton btnPlay;
    private CoordinatorLayout layoutVideoPlayer;
    private RelativeLayout.LayoutParams portraitLayoutParams;
    private RelativeLayout.LayoutParams landscapeLayoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorGrey));
        layoutVideoPlayer = findViewById(R.id.video_player_layout);
        setupLayoutParams();
        setupVideo();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FrameLayout frameLayout = findViewById(R.id.frame_video);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            frameLayout.setLayoutParams(portraitLayoutParams);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            frameLayout.setLayoutParams(landscapeLayoutParams);
            frameLayout.bringToFront();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
    }

    private void setupVideo() {
        videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse("https://aspb11.cdn.asset.aparat.com/aparat-video/07cce802f73b78499b7ed02fd66a4c0717350860-720p.mp4"));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                initViews();
            }
        });

    }

    private void initViews() {
        btnPlay = findViewById(R.id.btn_video_play);
        ImageButton btnForward = findViewById(R.id.btn_video_forward);
        ImageButton btnBackward = findViewById(R.id.btn_video_backward);
        duration = findViewById(R.id.txt_video_duration);
        currentDuration = findViewById(R.id.txt_video_current_duration);
        videoBar = findViewById(R.id.video_bar);

        videoBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play_white, null));
                } else {
                    videoView.start();
                    btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause_white, null));
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition() + 5000);
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition() - 5000);
            }
        });

        duration.setText(durationFormat(videoView.getDuration()));
        currentDuration.setText(durationFormat(0));

        videoBar.setMax(videoView.getDuration());

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentDuration.setText(durationFormat(videoView.getCurrentPosition()));
                        videoBar.setProgress(videoView.getCurrentPosition());
                        videoBar.setSecondaryProgress((videoView.getBufferPercentage() *  videoView.getDuration()) / 100);
                    }
                });
            }
        }, 0, 1);

    }

    private String durationFormat(long duration) {
        int seconds = (int) (duration / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format(Locale.ENGLISH, "%02d", minutes) + ":" + String.format(Locale.ENGLISH, "%02d", seconds);
    }

    private void setupLayoutParams() {
        View toolbar = findViewById(R.id.video_player_toolbar);
        View layoutVideoController = findViewById(R.id.layout_video_controller);

        landscapeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        portraitLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        portraitLayoutParams.addRule(RelativeLayout.BELOW, toolbar.getId());
        portraitLayoutParams.addRule(RelativeLayout.ABOVE, layoutVideoController.getId());

    }
}