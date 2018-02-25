package com.dgpro.biddaloy.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.NavigationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager notificationManager;

    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.e("FCM :::::","GOT FCM");
        if(remoteMessage == null)
        {
            return;
        }
        if(remoteMessage.getNotification() != null)
        {
            handleNotification(remoteMessage.getNotification().getTitle());
        }
    }
    public void handleNotification(String message)
    {
        sendNotification(message);
    }

    public void sendNotification(String message)
    {
        Intent intent = new Intent(this, NavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notificaion_moc)
                .setContentTitle("title")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}