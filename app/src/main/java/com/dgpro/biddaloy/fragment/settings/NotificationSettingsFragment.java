package com.dgpro.biddaloy.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 2/2/2018.
 */

public class NotificationSettingsFragment extends Fragment implements View.OnClickListener{

    ImageView stopNotification,selectNotificationSound;

    View mView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notification_settings,container,false);

        ((RelativeLayout)mView.findViewById(R.id.stop_noti)).setOnClickListener(this);
        ((RelativeLayout)mView.findViewById(R.id.noti_sound)).setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.stop_noti:
                Log.e("RelativeLayout","stopNoti");
                stopNotification();
                break;
            case R.id.noti_sound:
                stopNotificationSound();
                Log.e("RelativeLayout","notisound");
                break;
            default:
                break;

        }
    }
    void stopNotification(){

    }
    void stopNotificationSound(){

    }

}
