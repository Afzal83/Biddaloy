package com.dgpro.biddaloy.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dgpro.biddaloy.Helper.ImageHelper;
import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.SubmitBlogResponseModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.ComposeBlogActivity;
import com.dgpro.biddaloy.activity.LandingActivity;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.dialog.TransientDialog;
import com.dgpro.biddaloy.serviceapi.UserApi;
import com.dgpro.biddaloy.store.AppSharedPreferences;

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


    private static final int  SELECT_PICTURE = 3999 ;

    BiddaloyApplication biddaloyApplication;
    UserApi userApi;

    View mView;

    CircleImageView profileImage;
    Button uploadImage,changeImage,changePass;
    TextView userProfileName,userProfileCategory;

    Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile,container,false);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        userApi = new UserApi(getActivity());
        bindView();

        return mView;
    }
    void bindView(){
        profileImage = (CircleImageView)mView.findViewById(R.id.user_profile_image);

        uploadImage = (Button)mView.findViewById(R.id.upload_image);
        changeImage = (Button)mView.findViewById(R.id.change_image);
        changePass = (Button)mView.findViewById(R.id.change_password);

        userProfileName = (TextView)mView.findViewById(R.id.user_name);
        userProfileCategory = (TextView)mView.findViewById(R.id.user_category);

        uploadImage.setOnClickListener(this);
        changeImage.setOnClickListener(this);
        changePass.setOnClickListener(this);

        String userCat = "User Category : "+biddaloyApplication.userCatagory;
        userProfileName.setText(biddaloyApplication.userName);
        userProfileCategory.setText(userCat);

        Uri savedImageURI = Uri.parse(biddaloyApplication.usreImageUrl);
        profileImage.setImageURI(savedImageURI);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_image:
                changeImage();
                break;
            case R.id.upload_image:
                uploadProfileImage(bitmap);
                break;
            case R.id.change_password:
                showPasswordChangeDialog();
                break;
            default:
                break;
        }
    }
    void changeImage(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialog_layout_for_profile, null);
        final AlertDialog alertD = new AlertDialog.Builder(getActivity()).create();
        Button galery = (Button) promptView.findViewById(R.id.from_galery);
        Button camera = (Button) promptView.findViewById(R.id.from_camera);

        galery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            getGalleryImage();
            alertD.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getCameraImage();
                alertD.dismiss();
            }
        });
        alertD.setView(promptView);
        alertD.setCancelable(true);
        alertD.show();

    }
    void getGalleryImage(){

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    21);
            Log.e("pick image","not permitted");
        }else{
            uploadImage();
        }

    }
    void getCameraImage(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 709);
    }

    void uploadImage(){
        Log.e("permission accepted","permission accepted");
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
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
        else if(requestCode == 21){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadImage();

            } else {
                Toast.makeText(getActivity(),"You have to grant permission to upload image..",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
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
                    bitmap = (Bitmap) extras.get("data");
                    profileImage.setImageBitmap(bitmap);
                    uploadImage.setVisibility(View.VISIBLE);

                   // uploadProfileImage (bitmap);
                }
            }
            else if (requestCode == SELECT_PICTURE) {

                Uri selectedImage = data.getData();
                String imageAbsolutePath = getRealPathFromUri(getActivity(),selectedImage);

                Log.e("received image uri ",""+selectedImage);
                Log.e("received absoluteUrl ",""+imageAbsolutePath);

                bitmap = BitmapFactory.decodeFile(imageAbsolutePath);//decodeSampledBitmapFromResource(imageAbsolutePath,250,250);
                profileImage.setImageBitmap(bitmap);
                uploadImage.setVisibility(View.VISIBLE);
               // uploadProfileImage(bmp);
            }
        }
    }



    void uploadProfileImage(Bitmap bm){

        Bitmap bmp = getResizedBitmap(bm,200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imageName = timestamp.getTime()+"";

        MultipartBody.Part body = null;
        try {

            File f =  new File(getActivity().getCacheDir(), imageName);
            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(byteArray);
            fos.flush();
            fos.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), f);
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
                    uploadImage.setVisibility(View.INVISIBLE);
                    new TransientDialog(getActivity())
                            .showTransientDialogWithOutAction("Success...","Porfile Image Changed Successfully..");
                    saveImage();
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
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    void saveImage(){
        ImageHelper imgHelper = new ImageHelper();
        String uri = imgHelper.saveToInternalStorage(bitmap, getActivity(), biddaloyApplication.userName);
        AppSharedPreferences.saveStringToSharePreference(getActivity(),"user_image_uri",uri);
        biddaloyApplication.usreImageUrl = uri;
    }





    void showPasswordChangeDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_view_for_change_password, null);
        dialogBuilder.setView(dialogView);

        final EditText newPass = dialogView.findViewById(R.id.inserted_pass);
        final EditText confirmNewPass = dialogView.findViewById(R.id.inserted_confirm_pass);
        final EditText oldPass = dialogView.findViewById(R.id.inserted_old_pass);

        dialogBuilder.setTitle("Change Password");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                removeKeyBoard();

                String newPassStr = newPass.getText().toString();
                String confNewPassStr = confirmNewPass.getText().toString();
                String oldPassStr = oldPass.getText().toString();

                changePass(newPassStr,confNewPassStr,oldPassStr);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                removeKeyBoard();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        b.setCancelable(true);
    }
    void changePass(String newPass,String confNewPass,String oldPass){
        Log.e("changePass : ",newPass);
        Log.e("changePass : ",confNewPass);
        Log.e("changePass : ",oldPass);
        final  TransientDialog transientDialog = new TransientDialog(getActivity());
        if(newPass.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","New Password field is empty");
            return;
        }
        if(confNewPass.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","\"Confirm password\" field is empty");
            return;
        }
        if(oldPass.isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","Old password field is empty");
            return;
        }
        if(!oldPass.contentEquals(biddaloyApplication.password)){
            transientDialog.showTransientDialogWithOutAction("Error...","Old password is not valid");
            return;
        }
        if(!newPass.contentEquals(confNewPass)){
            transientDialog.showTransientDialogWithOutAction("Error...","Password don't match");
            return;
        }

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();
        userApi.changeUserPassword(newPass, confNewPass, new UserApi.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                dialog.dismiss();
                new UserApi(getActivity()).insertLoginModelToSharePreference(getActivity(),new LoginModel());
                startActivity(new Intent(getActivity(),LandingActivity.class));
                getActivity().finish();
            }

            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                transientDialog.showTransientDialogWithOutAction("",errorMsg);
            }
        });
    }
    void removeKeyBoard(){
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
