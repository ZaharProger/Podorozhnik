package com.podorozhnik.managers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.podorozhnik.R;
import com.podorozhnik.StartActivity;
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
        String notificationTitle = intent.getStringExtra(APIValues.NOTIFICATION_TITLE) != null ?
                intent.getStringExtra(APIValues.NOTIFICATION_TITLE) : context.getString(R.string.app_name);

        String notificationText = intent.getStringExtra(APIValues.NOTIFICATION_MESSAGE) != null ?
                intent.getStringExtra(APIValues.NOTIFICATION_MESSAGE) : null;

        String requestDeparturePoint = intent.getStringExtra(APIValues.REQUEST_FROM) != null ?
                intent.getStringExtra(APIValues.REQUEST_FROM) : null;

        String requestDestinationPoint = intent.getStringExtra(APIValues.REQUEST_TO) != null ?
                intent.getStringExtra(APIValues.REQUEST_TO) : null;

        Intent intentToActivate = new Intent(context, StartMenuActivity.class)
                .putExtra(APIValues.REQUEST_FROM, requestDeparturePoint)
                .putExtra(APIValues.REQUEST_TO, requestDestinationPoint);

        PendingIntent activationPending = PendingIntent.getActivity(context, codeGenerator.nextInt(),
                intentToActivate, PendingIntent.FLAG_IMMUTABLE);

        if (notificationText != null && notificationTitle != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_topic))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(activationPending);

            Pushy.setNotificationChannel(builder, context);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(codeGenerator.nextInt(), builder.build());
        }
    }

    public void sendMessage(Context context, String message, com.podorozhnik.entities.Request userRequest){
        JSONObject notificationToPost = new JSONObject();
        try{
            notificationToPost.put(APIValues.NOTIFICATION_RECEIVER, userRequest.getUserDeviceToken());

            Gson gsonConverter = new Gson();

            JSONObject messageStructure = new JSONObject();
            messageStructure.put(APIValues.NOTIFICATION_TITLE, context.getString(R.string.app_name));
            messageStructure.put(APIValues.NOTIFICATION_MESSAGE, message);
            messageStructure.put(APIValues.REQUEST_FROM, gsonConverter.toJson(userRequest.getDeparturePoint()));
            messageStructure.put(APIValues.REQUEST_TO, gsonConverter.toJson(userRequest.getDestinationPoint()));

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
