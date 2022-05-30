package com.podorozhnik.managers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.podorozhnik.R;
import com.podorozhnik.StartMenuActivity;

public class PodorozhnikMessagingService extends FirebaseMessagingService {

    private static int id = -1;

    public void sendNotification(Context context, String messageToSend) {
        Log.e("NOTIFICATION", messageToSend);
        Intent intent = new Intent(context, StartMenuActivity.class);
        PendingIntent intentToActivate = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getString(R.string.notification_topic))
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(messageToSend)
                .setAutoCancel(true)
                .addAction(0, context.getString(R.string.create_confirm_text), intentToActivate)
                .addAction(0, context.getString(R.string.cancel_text), null)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(defaultSoundUri)
                .setContentIntent(intentToActivate);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel(context.getString(R.string.notification_topic),
                context.getString(R.string.notification_topic), NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);

        notificationManager.notify(++id, notificationBuilder.build());
    }
}
