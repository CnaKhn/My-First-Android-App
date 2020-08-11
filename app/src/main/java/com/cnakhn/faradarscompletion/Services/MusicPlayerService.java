package com.cnakhn.faradarscompletion.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.renderscript.RenderScript;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.cnakhn.faradarscompletion.Activities.MusicPlayerActivity;
import com.cnakhn.faradarscompletion.R;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MusicPlayerService extends Service {
    private static final String ACTION_PLAY = "com.cnakhn.faradarscompletion.playMusic";
    private static final String ACTION_FORWARD = "com.cnakhn.faradarscompletion.forwardMusic";
    private static final String ACTION_BACKWARD = "com.cnakhn.faradarscompletion.backwardMusic";
    private static final String CHANNEL_ID = "com.cnakhn.faradarscompletion";
    File music;

    private MediaPlayer player;
    private MusicPlayerBinder musicPlayerBinder = new MusicPlayerBinder();

    @Override
    public IBinder onBind(Intent intent) {
        setupMediaPlayer();
        return musicPlayerBinder;
    }

    public MediaPlayer getMediaPlayer() {
        return this.player;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) {
            intent.setAction("");
        }
        switch (intent.getAction()) {
            case ACTION_PLAY:
                if (player.isPlaying()) {
                    player.pause();
                } else {
                    player.start();
                }
                break;
            case ACTION_FORWARD:
                player.seekTo(player.getCurrentPosition() + 5000);
                break;
            case ACTION_BACKWARD:
                player.seekTo(player.getCurrentPosition() - 5000);
                break;
            default:
                Intent launchMusicPlayer = new Intent(MusicPlayerService.this, MusicPlayerActivity.class);
                launchMusicPlayer.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                Intent playIntent = new Intent(this, MusicPlayerService.class);
                playIntent.setAction(ACTION_PLAY);
                PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, 0);

                Intent forwardIntent = new Intent(this, MusicPlayerService.class);
                forwardIntent.setAction(ACTION_FORWARD);
                PendingIntent forwardPendingIntent = PendingIntent.getService(this, 0, forwardIntent, 0);
                Intent backwardIntent = new Intent(this, MusicPlayerService.class);
                backwardIntent.setAction(ACTION_BACKWARD);
                PendingIntent backwardPendingIntent = PendingIntent.getService(this, 0, backwardIntent, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel(CHANNEL_ID, "My Background Service");
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_music_white).setContentTitle(music.getName())
                            .setContentIntent(PendingIntent.getActivity(this, 654, launchMusicPlayer, 0))
                            .addAction(R.drawable.ic_backward_black, "Rewind", backwardPendingIntent)
                            .addAction(R.drawable.ic_play_black, "play", playPendingIntent)
                            .addAction(R.drawable.ic_forward_black, "forward", forwardPendingIntent)
                            .build();
                    startForeground(201, notification);
                } else {
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
                    Notification notification = notificationBuilder.setSmallIcon(R.drawable.ic_music_white)
                            .setContentTitle("My Music Player").setContentText(music.getName()).setOngoing(true)
                            .setContentIntent(PendingIntent.getActivity(this, 654, launchMusicPlayer, 0))
                            .addAction(R.drawable.ic_backward_black, "Rewind", backwardPendingIntent)
                            .addAction(R.drawable.ic_play_black, "play", playPendingIntent)
                            .addAction(R.drawable.ic_forward_black, "forward", forwardPendingIntent).build();
                    startForeground(201, notification);
                }


                break;
        }
        return START_STICKY;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();

        }
    }

    private void setupMediaPlayer() {
        File musicFile = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        music = new File(musicFile, "demo_audio.mp3");
        if (music.exists()) {

            player = new MediaPlayer();
            try {
                player.setDataSource(this, Uri.fromFile(music));
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                    }
                });
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Music not found...", Toast.LENGTH_LONG).show();
        }
    }

    public class MusicPlayerBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }
}
