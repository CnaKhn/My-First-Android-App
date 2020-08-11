package com.cnakhn.faradarscompletion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import android.util.Log;

import com.cnakhn.faradarscompletion.Activities.MainActivity;
import com.cnakhn.faradarscompletion.Activities.RecyclerViewActivity;
import com.cnakhn.faradarscompletion.DataModel.Product.FakeDataGenerator;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductDBHelper;
import com.cnakhn.faradarscompletion.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "com.cnakhn.faradarscompletion";
    private static final String DESTINATION = "destination";
    private static final String DESTINATION_TYPE = "destination_type";

    private static final String DESTINATION_TYPE_URL = "url";
    private static final String DESTINATION_TYPE_ACTIVITY = "activity";

    private static final String ACTIVITY_MAIN = "activity_main";
    private static final String ACTIVITY_RECYCLER_VIEW = "activity_recycler_view";

    private static final String EXTRA_POST_NAME = "post_name";
    private static final String EXTRA_POST_CATEGORY = "post_category";
    private static final String EXTRA_POST_INSTRUCTIONS = "post_instructions";
    private static final String EXTRA_POST_PRICE = "post_price";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            switch (data.get(DESTINATION_TYPE)) {
                case DESTINATION_TYPE_ACTIVITY:
                    String activity = data.get(DESTINATION);
                    switch (activity) {
                        case ACTIVITY_MAIN:
                            Intent launchMainActivity = new Intent(this, MainActivity.class);
                            launchMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sendNotification(remoteMessage.getNotification().getBody(), launchMainActivity);
                            break;
                        case ACTIVITY_RECYCLER_VIEW:
                            FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
                            Intent launchRecyclerViewActivity = new Intent(this, RecyclerViewActivity.class);
                            launchRecyclerViewActivity.putExtra(fakeDataGenerator.getName(), data.get(EXTRA_POST_NAME));
                            launchRecyclerViewActivity.putExtra(fakeDataGenerator.getCategory(), data.get(EXTRA_POST_CATEGORY));
                            launchRecyclerViewActivity.putExtra(fakeDataGenerator.getInstructions(), data.get(EXTRA_POST_INSTRUCTIONS));
                            launchRecyclerViewActivity.putExtra(fakeDataGenerator.getPrice(), data.get(EXTRA_POST_PRICE));
                            launchRecyclerViewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sendNotification(remoteMessage.getNotification().getBody(), launchRecyclerViewActivity);
                    }
                    break;
                case DESTINATION_TYPE_URL:
                    String url = data.get(DESTINATION);
                    Intent showWebPageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    showWebPageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendNotification(remoteMessage.getNotification().getBody(), Intent.createChooser(showWebPageIntent, "Select your Browser"));
                    break;
            }
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(Worker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    private void sendNotification(String messageBody, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = CHANNEL_ID;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_notification)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentTitle("My App")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}