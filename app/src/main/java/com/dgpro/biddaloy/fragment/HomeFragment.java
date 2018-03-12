package com.dgpro.biddaloy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.AboutInstituteModel;
import com.dgpro.biddaloy.Network.Model.BlogListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.serviceapi.InstituteApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by Babu on 12/27/2017.
 */

public class HomeFragment extends Fragment {

    InstituteApi instituteApi;

    String schoolImageUrl,principalImageUrl;
    String schoolName = "";
    String schoolAddress = "";
    String schoolPhone = "";
    String schoolWeb = "";
    String schoolPrinciPalName = "";
    String aboutSchool = "";

    View mView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_home,container,false);
        instituteApi = new InstituteApi(getActivity());
        downLoadInstituteInfo();
        return mView;
    }
    void downLoadInstituteInfo(){
        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();
        instituteApi.downLoadInstituteInfo(new InstituteApi.Callback<AboutInstituteModel>() {
            @Override
            public void onSuccess(AboutInstituteModel aboutInstituteModel) {
                dialog.dismiss();
                schoolName = aboutInstituteModel.getInstitute_name();
                schoolAddress = aboutInstituteModel.getInstitute_address();
                schoolPhone = aboutInstituteModel.getInstitute_phone();
                schoolWeb = aboutInstituteModel.getInstitute_website();
                schoolPrinciPalName = aboutInstituteModel.getHead_of_institute();
                aboutSchool = aboutInstituteModel.getAbout_us();
                schoolImageUrl =  aboutInstituteModel.getInstitute_logo();
                principalImageUrl = aboutInstituteModel.getHead_image();
                updateView();
            }

            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                Log.e("home_fragment","error to downLoad institute data");
            }
        });
    }
    void updateView(){

        BiddaloyApplication biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        ((TextView)mView.findViewById(R.id.school_name)).setText(schoolName);
        ((TextView)mView.findViewById(R.id.school_address)).setText(schoolAddress);
        ((TextView)mView.findViewById(R.id.school_phone)).setText(schoolPhone);
        ((TextView)mView.findViewById(R.id.school_web)).setText(schoolWeb);
        ((TextView)mView.findViewById(R.id.about_school)).setText(aboutSchool);
        ((TextView)mView.findViewById(R.id.header_name)).setText(schoolPrinciPalName);

        String schoolLogo = biddaloyApplication.baseUrl+ schoolImageUrl;
        String principalImage = biddaloyApplication.baseUrl+principalImageUrl;

        Log.e("school url : ",schoolLogo);
        Log.e("principalImage url : ",principalImage);

        Picasso.with(getActivity())
                .load(schoolLogo)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(((ImageView)mView.findViewById(R.id.school_image)));

        Picasso.with(getActivity())
                .load(principalImage)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(((ImageView)mView.findViewById(R.id.head_image)));
    }
}
