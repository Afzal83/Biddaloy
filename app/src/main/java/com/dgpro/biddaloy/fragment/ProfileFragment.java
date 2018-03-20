package com.dgpro.biddaloy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.SubmitBlogResponseModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.ComposeBlogActivity;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.dialog.TransientDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Babu on 3/9/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{

    BiddaloyApplication biddaloyApplication;
    View mView;

    CircleImageView profileImage;
    Button uploadImage,changeImage;
    TextView userProfileName,userProfileCategory;

    //Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile,container,false);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());

        bindView();

        return mView;
    }
    void bindView(){
        profileImage = (CircleImageView)mView.findViewById(R.id.user_profile_image);

        uploadImage = (Button)mView.findViewById(R.id.upload_image);
        changeImage = (Button)mView.findViewById(R.id.change_image);

        userProfileName = (TextView)mView.findViewById(R.id.user_name);
        userProfileName = (TextView)mView.findViewById(R.id.user_category);

        uploadImage.setOnClickListener(this);
        changeImage.setOnClickListener(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 103) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 709);
            }else{
                Toast.makeText(getActivity(), "Camera Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_image:
                changeImage();
                break;
            case R.id.upload_image:
                //uploadProfileImage();
                break;
            default:
                break;

        }
    }
    void changeImage(){
        getCameraImage();
    }
    void getCameraImage(){

        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(intent, 709);

        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        requestPermissions(permissions, 103);


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("activityResultcalled","called");
        if(resultCode == RESULT_OK){
            Log.e("resultCode","RESULT_OK");
            if(requestCode == 709){
                Log.e("requestCode","709");
                if(data != null)
                {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    profileImage.setImageBitmap(bitmap);
                    uploadImage.setVisibility(View.VISIBLE);

                    uploadProfileImage (bitmap);
                }
            }
        }
    }
    void uploadProfileImage(Bitmap bmp){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,50,stream);
        byte[] byteArray = stream.toByteArray();

        Log.e("image array ", byteArray.toString());


         //=  File.createTempFile("test", null, getActivity().getCacheDir());//new File(getActivity().getCacheDir(),biddaloyApplication.userName);
        MultipartBody.Part body = null;
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String imageName = timestamp.getTime()+"";

            File f =  File.createTempFile(imageName, null, getActivity().getCacheDir());

            //File f = new File(getActivity().getCacheDir(),"test_img");
            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(byteArray);
            fos.flush();
            fos.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), f);
            //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            body = MultipartBody.Part.createFormData("image", f.getName(), requestFile);
            Log.e("exception ","not exception here");

        }catch (Exception e){
            Log.e("exception ","exception here");
            e.printStackTrace();
        }

        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.userName);
        RequestBody userPass = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.password);
        RequestBody userCategory = RequestBody.create(MediaType.parse("text/plain"),biddaloyApplication.userCatagory);

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.Uploading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        Call<SubmitBlogResponseModel> call = mService.uploadProfileImage(
                userName, userPass, userCategory,body
        );
        call.enqueue(new Callback<SubmitBlogResponseModel>() {
            @Override
            public void onResponse(Call<SubmitBlogResponseModel> call, Response<SubmitBlogResponseModel> response) {

                if(response.isSuccessful()) {
                    Log.e("send profile image","success");
                    dialog.dismiss();
                    new TransientDialog(getActivity())
                            .showTransientDialogWithOutAction("Success...","Porfile Image Changed Successfully..");
                }else {
                    int statusCode  = response.code();
                    Log.e("send profile image","fucked up ..");
                    dialog.dismiss();
                    new TransientDialog(getActivity())
                            .showTransientDialogWithOutAction("Error ...","Fail To change Profile Image ");
                }
            }

            @Override
            public void onFailure(Call<SubmitBlogResponseModel> call, Throwable t) {
                Log.e("send profile image","fail");
                dialog.dismiss();
                new TransientDialog(getActivity())
                        .showTransientDialogWithOutAction("Error ...","Network Errror ");

            }
        });
    }
}
