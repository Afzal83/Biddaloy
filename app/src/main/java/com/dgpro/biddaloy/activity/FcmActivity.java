package com.dgpro.biddaloy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgpro.biddaloy.R;
import com.squareup.picasso.Picasso;

public class FcmActivity extends AppCompatActivity {

    TextView notiHeader,notiBody;
    ImageView notiImage;

    String notificationHeader = "";
    String notificationBody = "";
    String notificationImage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_acticity);

        Intent intent_o = getIntent();
        notificationHeader = intent_o.getStringExtra("title");
        notificationBody = intent_o.getStringExtra("body");
        notificationImage = intent_o.getStringExtra("image");

        Log.e("FCM IMAGE ",notificationImage+"");
        Log.e("FCM BODY ",notificationBody+"");
        Log.e("FCM HEADER ",notificationHeader+"");

       // Toast.makeText(this, notificationBody, Toast.LENGTH_LONG).show();

        bindView();

    }
    void bindView(){
        notiHeader = findViewById(R.id.notification_header);
        notiBody = findViewById(R.id.notification_body);
        notiImage = findViewById(R.id.notification_image);

        notiBody.setText(notificationBody);
        notiHeader.setText(notificationHeader);


        Picasso.with(this)
                .load(notificationImage)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(notiImage);


    }
}
