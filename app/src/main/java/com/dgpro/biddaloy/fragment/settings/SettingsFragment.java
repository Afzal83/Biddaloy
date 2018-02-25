package com.dgpro.biddaloy.fragment.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.SettingsActivity;

/*
 * Created by Babu on 1/27/2018.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings,container,false);
        ((Button)mView.findViewById(R.id.btn_image_privacy)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.btn_privacy)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.btn_image_noti)).setOnClickListener(this);
        ((Button)mView.findViewById(R.id.btn_noti)).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        switch(view.getId()){
            case R.id.btn_image_privacy:
            case R.id.btn_privacy:
                intent.putExtra("setting","privacy");
                break;
            case R.id.btn_image_noti:
            case R.id.btn_noti:
                intent.putExtra("setting","notification");
                break;
            default:
                    break;
        }
        startActivity(intent);
    }

}
