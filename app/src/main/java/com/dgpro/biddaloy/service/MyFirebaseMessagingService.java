package com.dgpro.biddaloy.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.NavigationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM:::";

    String messageTitle = "0";
    String messageBody = "0";
    String image = "0";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "fcm....");
        String title = "";
        String body =  "";
        String onclick =  "";

        if (remoteMessage.getNotification() != null) {

            Log.e(TAG, "fcm....notification data");
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
           // onclick = remoteMessage.getNotification().getClickAction();
            onclick = "NavigationActivity";
        }

        else if (remoteMessage.getData().size() > 0) {

            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            messageTitle = remoteMessage.getData().get("title");
            messageBody = remoteMessage.getData().get("body");
            image = remoteMessage.getData().get("image");
            //sendNotification("FcmActivity","data fcm","Body nai" );
            title = messageTitle;
            body =  messageBody;
            onclick = "FcmActivity";

        }
        sendNotification(onclick,title,body );
    }

    private void sendNotification(String onClick,String title, String body) {
        Intent intent = new Intent(onClick);
        intent.putExtra("title", messageTitle);
        intent.putExtra("body", messageBody);
        intent.putExtra("image", image);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.notificaion_moc)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}