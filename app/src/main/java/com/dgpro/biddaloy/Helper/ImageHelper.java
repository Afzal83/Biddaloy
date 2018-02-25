package com.dgpro.biddaloy.Helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Babu on 1/28/2018.
 */

public class ImageHelper {

    public String saveToInternalStorage(Bitmap bitmapImage, Context context, String name){

        ContextWrapper wrapper = new ContextWrapper(context);
        File file = wrapper.getDir("Images",MODE_PRIVATE);
        file = new File(file, "user"+".png");

        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();

        }catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /** Method to retrieve image from your device **/
    public Bitmap loadImageFromStorage(String path, String naam)
    {
        Bitmap b;
        String name_= naam;
        try {
            File f=new File(path, name_);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.e("found image","image");
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
