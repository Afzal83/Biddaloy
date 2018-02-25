package com.dgpro.biddaloy.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.constants.PreferenceConstants;
import com.dgpro.biddaloy.store.AppSharedPreferences;

/**
 * Created by Babu on 2/2/2018.
 */

public class PrivacySettingsFragment extends Fragment implements View.OnClickListener{

    BiddaloyApplication biddaloyApplication;
    ImageView hidePersonalInfo,hidePhoto;

    View mView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_privacy_settings,container,false);

        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());

        hidePersonalInfo = (ImageView)mView.findViewById(R.id.hide_my_info_img);
        hidePhoto = (ImageView)mView.findViewById(R.id.hide_my_photo_img);


        ((RelativeLayout)mView.findViewById(R.id.hide_my_photo)).setOnClickListener(this);
        ((RelativeLayout)mView.findViewById(R.id.secret_personal_info)).setOnClickListener(this);

        if(AppSharedPreferences.readBoolean(getActivity().getBaseContext(),PreferenceConstants.SP_HIDE_USER_PHOTO)){
            hidePhoto.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checked_icon));
        }else{
            hidePhoto.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.unchecked_icon));
        }
        if(AppSharedPreferences.readBoolean(getActivity().getBaseContext(),PreferenceConstants.SP_HIDE_USER_INFO)){
            hidePersonalInfo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checked_icon));
        }else{
            hidePersonalInfo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.unchecked_icon));
        }

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hide_my_photo:
                Log.e("RelativeLayout","hidePhoto");
                if(AppSharedPreferences.readBoolean(getActivity().getBaseContext(),PreferenceConstants.SP_HIDE_USER_PHOTO)){
                    AppSharedPreferences.saveBooleanToSharedPreference(getActivity()
                            , PreferenceConstants.SP_HIDE_USER_PHOTO
                            ,false);
                    hidePhoto.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.unchecked_icon));
                }else{
                    AppSharedPreferences.saveBooleanToSharedPreference(getActivity()
                            , PreferenceConstants.SP_HIDE_USER_PHOTO
                            ,true);
                    hidePhoto.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checked_icon));
                }
                break;
            case R.id.secret_personal_info:
                if(AppSharedPreferences.readBoolean(getActivity().getBaseContext(),PreferenceConstants.SP_HIDE_USER_INFO)){
                    AppSharedPreferences.saveBooleanToSharedPreference(getActivity()
                            , PreferenceConstants.SP_HIDE_USER_INFO
                            ,false);
                    hidePersonalInfo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.unchecked_icon));
                }else{
                    AppSharedPreferences.saveBooleanToSharedPreference(getActivity()
                            , PreferenceConstants.SP_HIDE_USER_INFO
                            ,true);
                    hidePersonalInfo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checked_icon));
                }
                Log.e("RelativeLayout","personalInfo");
                break;
            default:
                break;

        }
    }
}
