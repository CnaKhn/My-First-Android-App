package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Services.MusicPlayerService;
import com.cnakhn.faradarscompletion.Utils;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends AppCompatActivity implements ServiceConnection {
    private MediaPlayer player;
    private TextView currentDuration;
    private TextView duration;
    private SeekBar musicBar;
    private Timer timer;
    private ImageButton btnPlay;
    private CoordinatorLayout layoutMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorGrey));
        layoutMusicPlayer = findViewById(R.id.music_player_layout);
        bindService(new Intent(this, MusicPlayerService.class), this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
    }

    private void initViews() {
        btnPlay = findViewById(R.id.btn_play);
        currentDuration = findViewById(R.id.txt_music_current_duration);
        duration = findViewById(R.id.txt_music_duration);
        ImageButton btnForward = findViewById(R.id.btn_forward);
        ImageButton btnBackward = findViewById(R.id.btn_backward);
        musicBar = findViewById(R.id.music_bar);
        if (player != null) {
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startService(new Intent(MusicPlayerActivity.this, MusicPlayerService.class));
                    if (player.isPlaying()) {
                        player.pause();
                        btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play_white, null));
                    } else {
                        player.start();
                        btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause_white, null));
                    }
                }
            });

            duration.setText(durationFormat(player.getDuration()));
            currentDuration.setText(durationFormat(0));


            btnForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.seekTo(player.getCurrentPosition() + 5000);
                }
            });

            btnBackward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.seekTo(player.getCurrentPosition() - 5000);
                }
            });

            musicBar.setMax(player.getDuration());
            musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        player.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentDuration.setText(durationFormat(player.getCurrentPosition()));
                            musicBar.setProgress(player.getCurrentPosition());
                        }
                    });
                }
            }, 0, 1);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicPlayerService.MusicPlayerBinder musicPlayerBinder = (MusicPlayerService.MusicPlayerBinder) service;
        MusicPlayerService musicPlayerService = musicPlayerBinder.getService();
        player = musicPlayerService.getMediaPlayer();
        initViews();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private String durationFormat(long duration) {
        int seconds = (int) (duration / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format(Locale.ENGLISH, "%02d", minutes) + ":" + String.format(Locale.ENGLISH, "%02d", seconds);
    }
}