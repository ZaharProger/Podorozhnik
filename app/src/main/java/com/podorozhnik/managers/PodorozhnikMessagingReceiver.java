package com.podorozhnik.managers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.podorozhnik.R;
import com.podorozhnik.StartMenuActivity;
import com.podorozhnik.final_values.APIValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.pushy.sdk.Pushy;

public class PodorozhnikMessagingReceiver extends BroadcastReceiver {
    private static Random codeGenerator = new Random();

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = intent.getStringExtra("title") != null ?
                intent.getStringExtra("title") : context.getString(R.string.app_name);

        String notificationText = intent.getStringExtra("message") != null ?
                intent.getStringExtra("message") : null;

        Intent intentToActivate = new Intent(context, StartMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToActivate,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (notificationText != null && notificationTitle != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_topic))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setLights(Color.GREEN, 1000, 1000)
                    .setVibrate(new long[]{0, 400, 250, 400})
                    .addAction(0, context.getString(R.string.create_confirm_text), pendingIntent)
                    .addAction(0, context.getString(R.string.cancel_text), null)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

            Pushy.setNotificationChannel(builder, context);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(codeGenerator.nextInt(), builder.build());
        }
    }

    public void sendMessage(Context context, String message, String receiverToken){
        JSONObject notificationToPost = new JSONObject();
        try{
            notificationToPost.put(APIValues.NOTIFICATION_RECEIVER, receiverToken);

            JSONObject messageStructure = new JSONObject();
            messageStructure.put(APIValues.NOTIFICATION_TITLE, context.getString(R.string.app_name));
            messageStructure.put(APIValues.NOTIFICATION_MESSAGE, message);

            notificationToPost.put(APIValues.NOTIFICATION_DATA, messageStructure);
        }
        catch (JSONException exception){
            exception.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIValues.NOTIFICATION_URL + APIValues.NOTIFICATION_TOKEN, notificationToPost, null, null){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", APIValues.CONTENT_TYPE);

                return headers;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }
}
