package com.dgpro.biddaloy.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.dialog.TransientDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;

import static android.support.v4.content.ContextCompat.*;

/**
 * Created by Babu on 3/20/2018.
 */

public class TrackingFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        ,View.OnClickListener{

    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    String senderPhoneNo= "";
    String locationAddress = "";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;


    SupportMapFragment fragment;
    private GoogleMap mMap ;

    TextView selectdStudent_tv;
    Button findAssetOne,findAssetTwo,findAssetThree;
    String std_one_phone_no = "01979105311";
    String std_two_phone_no = "01979105310";
    String std_three_phone_no = "01979105310";

    String selectedDevicesimNo = "";
    String selectedStudent = "";

    Handler smsDelayHandler = new Handler();
    MaterialDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_map, container, false);


        findAssetOne = (Button)mView.findViewById(R.id.find_asset_one);
        findAssetTwo = (Button)mView.findViewById(R.id.find_asset_two);
        findAssetThree = (Button)mView.findViewById(R.id.find_asset_three);
        selectdStudent_tv = (TextView)mView.findViewById(R.id.selected_student);

        findAssetOne.setOnClickListener(this);
        findAssetTwo.setOnClickListener(this);
        findAssetThree.setOnClickListener(this);


       if(!isSmsPermissionGranted()){
           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           builder.setTitle(R.string.permission_alert_dialog_title); // Your own title
           builder.setMessage(R.string.permission_dialog_message); // Your own message

           builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   requestReadAndSendSmsPermission();
               }
           });

           builder.setCancelable(false);
           builder.show();
       }


        if (checkPlayServices()) {
            buildGoogleApiClient();
        }


        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_mapContainer);// this line may not needed..
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_mapContainer, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(smsBroadcastReceiver, filter);

        if (mMap == null) {
            fragment.getMapAsync(this);
        }
        mGoogleApiClient.connect();

        Log.e("end","resume");

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View view) {
        mMap.clear();
        switch (view.getId()){
            case R.id.find_asset_one:
                selectedDevicesimNo = std_one_phone_no;
                selectdStudent_tv.setText("Student A");
                Log.e("send_sms","std_ one");
                break;
            case R.id.find_asset_two:
                selectedDevicesimNo = std_two_phone_no;
                selectdStudent_tv.setText("Student B");
                Log.e("send_sms","std_ two");
                break;
            case R.id.find_asset_three:
                selectedDevicesimNo = std_three_phone_no;
                selectdStudent_tv.setText("Student C");
                Log.e("send_sms","std_ three");
                break;
        }
        dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content("Please Wait for location")
                .progress(true, 0)
                .cancelable(false)
                .show();

        smsDelayHandler.postDelayed(runnableCode, 60000);
        sendSMSMessage();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("google map ready","ready");
        mMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setPadding(0, 0, 0, 0);

        LatLng mLatlng = new LatLng(23.763743,90.387352);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatlng,18.0f));

    }
    @Override
    public void onConnected(Bundle bundle) {
        //   showMyLocation();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),"This device is not supported.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }





    public boolean isSmsPermissionGranted() {
        return checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 500);
    }
    protected void sendSMSMessage() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(selectedDevicesimNo, null, "DW", null, null);
                Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_LONG).show();
            }
            catch (Exception ErrVar)
            {
                Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }
    }

    private BroadcastReceiver smsBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                String smsSender = "";
                String smsBody = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        smsSender = smsMessage.getDisplayOriginatingAddress();
                        smsBody += smsMessage.getMessageBody();
                    }
                }
                else {
                    Bundle smsBundle = intent.getExtras();
                    if (smsBundle != null) {
                        Object[] pdus = (Object[]) smsBundle.get("pdus");
                        if (pdus == null) {
                            // Display some error to the user
                            Log.e("IN_BROADCAST", "SmsBundle had no pdus key");
                            return;
                        }
                        SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < messages.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            smsBody += messages[i].getMessageBody();
                        }
                        smsSender = messages[0].getOriginatingAddress();
                        String[] parts = smsSender.split(" ");
                    }
                }

                Log.e("IN_BROADCAST_sms", smsBody);
                if(!smsBody.contains("http")){return;}
                String [] pArtsForAddress = smsBody.split("http");
                locationAddress = pArtsForAddress[0];
                pArtsForAddress = locationAddress.split("：");
                locationAddress = pArtsForAddress[1];
               // locationAddress = locationAddress.replaceAll(",", "\n");


                String[] parts = smsBody.split(" ");
                String Latitude = "";
                String Longitude = "";
                for(int av=0;av<parts.length;av++){
                    if(parts[av].contains("http")){
                        Log.e("link",parts[av]);
                        String[] nPart = parts[av].split("=");
                        String latLonStr = nPart[1];
                        String[]mPart = latLonStr.split(",");
                        Latitude = mPart[0];
                        Longitude = mPart[1];
                        break;
                    }
                }
                Log.e("latitude",Latitude);
                Log.e("longitude",Longitude);
                setMarkerOnMarker(Latitude,Longitude);

                if (smsSender.equals(senderPhoneNo) && smsBody.startsWith("")) {
//                    if (listener != null) {
//                        listener.onTextReceived(smsBody);
//                    }
                }
            }
        }
    };
    void setMarkerOnMarker(String lat,String lon){

        double latDbl = Double.parseDouble(lat);
        double longDbl = Double.parseDouble(lon);

        Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latDbl, longDbl))
                    .title(locationAddress));
        marker.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latDbl, longDbl),14.0f));
        dialog.dismiss();
        smsDelayHandler.removeCallbacks(runnableCode);
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
            smsDelayHandler.removeCallbacks(runnableCode);
            new TransientDialog(TrackingFragment.this.getActivity())
                    .showTransientDialogWithOutAction("Error ...","Could Not get Locaion from object.. ");
        }
    };

}
