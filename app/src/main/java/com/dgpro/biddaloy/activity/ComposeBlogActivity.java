package com.dgpro.biddaloy.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.BlogCatagoryDataModel;
import com.dgpro.biddaloy.Network.Model.BlogCatagoryList;
import com.dgpro.biddaloy.Network.Model.SubmitBlogResponseModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.adapter.UserDrpupdownAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.dialog.TransientDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeBlogActivity extends AppCompatActivity implements View.OnClickListener{

    BiddaloyApplication biddaloyApplication ;

    private static final int  SELECT_PICTURE = 3999 ;
    Spinner mDroupDownSpinner;
    ArrayList<String> droupDownList ;

    String blogCatagory = "";
    String imageAbsolutePath = "";
    String imageUriStr = "";

    Button uploadImage,discardImage;
    EditText blogTitle_et,blogBody_et;
    ImageView blogImage_iv ;

    File bmpFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_blog);

        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.compose_blog_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Compose Blog");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bindView();
        downLoadBlogCagagoryData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose_mail_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        else if(item.getItemId() ==  R.id.action_send){
            submitBlog();
        }
        return super.onOptionsItemSelected(item);
    }


    void bindView(){

        blogBody_et = findViewById(R.id.blog_body);
        blogTitle_et = findViewById(R.id.blog_subject);
        blogImage_iv = findViewById(R.id.blog_image);
        uploadImage = findViewById(R.id.upload_img_btn);
        discardImage = findViewById(R.id.discard_img_btn);

        uploadImage.setOnClickListener(this);
        discardImage.setOnClickListener(this);
    }

    void downLoadBlogCagagoryData(){
        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.getBlogCatagories(
                biddaloyApplication.userName
                ,biddaloyApplication.password
                ,biddaloyApplication.userCatagory
        ).enqueue(new Callback<BlogCatagoryList>() {

            @Override
            public void onResponse(Call<BlogCatagoryList> call, Response<BlogCatagoryList> response) {
                if (response.isSuccessful()) {
                    droupDownList = new ArrayList<>();
                    droupDownList.add("Select Category");
                    for (BlogCatagoryDataModel model : response.body().getBlog_category()) {
                        droupDownList.add(model.getBlog_category());
                    }
                    setBlogCatagoryDropDown();
                } else {
                    int statusCode = response.code();
                    Log.e("fail ", "fail to download by retrofit");
                }
            }
            @Override
            public void onFailure(Call<BlogCatagoryList> call, Throwable t) {
                Log.e("error ", "error to download by retrofit");
            }
        });
    }
    void setBlogCatagoryDropDown(){

        mDroupDownSpinner = (Spinner) findViewById(R.id.blog_catagory_spinner);
        UserDrpupdownAdapter aa = new UserDrpupdownAdapter(this,R.layout.droupdown_blog_catagory,droupDownList);
        mDroupDownSpinner.setAdapter(aa);
        mDroupDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                blogCatagory =  droupDownList.get(i);
                if(blogCatagory.contains("Select")){
                    blogCatagory = "";
                    return;
                }
                Log.e("selected catagory ",blogCatagory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upload_img_btn:
                pickImage();
                break;
            case R.id.discard_img_btn:
                imageAbsolutePath = "";
                blogImage_iv.setVisibility(View.GONE);
                discardImage.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    void submitBlog(){

        String blogTitle = blogTitle_et.getText().toString();
        String blogBody = blogBody_et.getText().toString();

        if(blogCatagory.isEmpty() || blogCatagory.contentEquals("")){
            new TransientDialog(this)
                    .showTransientDialogWithOutAction("Error ...","Please Select Bolog Catagory");
            return ;
        }
        if(blogTitle.isEmpty() || blogTitle.contentEquals("")){
            new TransientDialog(this)
                    .showTransientDialogWithOutAction("Error ...","Blog title is Empty !!!");
            return ;
        }
        if(blogBody.isEmpty() || blogBody.contentEquals("")){
            new TransientDialog(this)
                    .showTransientDialogWithOutAction("Error ...","Please write some thing for this blog !!!");
            return ;
        }

//        File file = new File(imageAbsolutePath);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        File file ;
        RequestBody requestFile;
        MultipartBody.Part body = null;
        if(!imageAbsolutePath.contentEquals("")){

//            file = new File(imageAbsolutePath);
//            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            requestFile = RequestBody.create(MediaType.parse("image/*"), bmpFile);
            body = MultipartBody.Part.createFormData("image", bmpFile.getName(), requestFile);

        }
        //Log.e("multipartBody :",body.toString());

        RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.userName);
        RequestBody userPass = RequestBody.create(MediaType.parse("text/plain"), biddaloyApplication.password);
        RequestBody userCategory = RequestBody.create(MediaType.parse("text/plain"),biddaloyApplication.userCatagory);
        RequestBody blgCatagory = RequestBody.create(MediaType.parse("text/plain"),blogCatagory);
        RequestBody blgTitle = RequestBody.create(MediaType.parse("text/plain"),blogTitle);
        RequestBody blgBody = RequestBody.create(MediaType.parse("text/plain"),blogBody);

        Log.e("userName :",userName+"");
        Log.e("Pass :",userPass+"");
        Log.e("userCatagory :",userCategory+"");
        Log.e("blogcatagory :",blgCatagory+"");
        Log.e("title :",blgTitle+"");
        Log.e("blockBody :",blgBody+"");


        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Uploading Blog.....")
                .content("Please Wait")
                .progress(true, 0)
                .show();

        RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        Call<SubmitBlogResponseModel> call = mService.submitBlog( userName, userPass, userCategory,
                blgCatagory,blgTitle,blgBody,body);
        call.enqueue(new Callback<SubmitBlogResponseModel>() {
            @Override
            public void onResponse(Call<SubmitBlogResponseModel> call, Response<SubmitBlogResponseModel> response) {

                if(response.isSuccessful()) {
                    Log.e("send blog","success");
                    dialog.dismiss();
                    new TransientDialog(ComposeBlogActivity.this)
                            .showTransientDialogWithOutAction("Success...","Blog Submitted Successfully..");
                }else {
                    int statusCode  = response.code();
                    Log.e("send blog","fucked up ..");
                    dialog.dismiss();
                    new TransientDialog(ComposeBlogActivity.this)
                            .showTransientDialogWithOutAction("Error ...","Blog submit fail  ");
                }
            }

            @Override
            public void onFailure(Call<SubmitBlogResponseModel> call, Throwable t) {
                Log.e("send blog","fail");
                dialog.dismiss();
                new TransientDialog(ComposeBlogActivity.this)
                        .showTransientDialogWithOutAction("Error ...","Could Not Submit Blog ");

            }
        });
    }
    public void pickImage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        21);

                Log.e("pick image","not permitted");
        }else{
            uploadImage();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 21: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadImage();
                    
                } else {
                    Toast.makeText(this,"You have to grant permission to upload image..",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    void uploadImage(){
        Log.e("permission accepted","permission accepted");
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (resultCode == RESULT_OK) {
            Log.e("reasult code","ok");
            if (requestCode == SELECT_PICTURE) {

                discardImage.setVisibility(View.VISIBLE);
                blogImage_iv.setVisibility(View.VISIBLE);

                Uri selectedImage = dataIntent.getData();
                imageUriStr = selectedImage.getPath();
                imageAbsolutePath = getRealPathFromUri(this,selectedImage);

                Log.e("received image uri ",""+selectedImage);
                Log.e("received imageUriStr ",""+imageUriStr);
                Log.e("received absoluteUrl ",""+imageAbsolutePath);

                Bitmap bmp = BitmapFactory.decodeFile(imageAbsolutePath);//decodeSampledBitmapFromResource(imageAbsolutePath,250,250);
                blogImage_iv.setImageBitmap(bmp);

                try{
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String imageName = timestamp.getTime()+"";
                    bmpFile = new File(this.getCacheDir(), imageName);
                    bmpFile.createNewFile();

                    //Convert bitmap to byte array
                    Bitmap bTemp = bmp ;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bTemp.compress(Bitmap.CompressFormat.JPEG, 75 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(bmpFile);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }else {

            if (requestCode == SELECT_PICTURE){
                discardImage.setVisibility(View.GONE);
                blogImage_iv.setVisibility(View.GONE);
            }
            Log.e("reasult code","error");
        }
    }
    public Bitmap decodeSampledBitmapFromResource(String Filepath,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(Filepath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(Filepath, options);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        int inSampleSize = 1;
        final int height = options.outHeight;
        final int width = options.outWidth;


        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
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
}
