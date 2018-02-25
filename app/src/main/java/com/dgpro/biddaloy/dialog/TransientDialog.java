package com.dgpro.biddaloy.dialog;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.dgpro.biddaloy.R;

/**
 * Created by Babu on 2/1/2018.
 */

public class TransientDialog {

    private Context mContext;

    public TransientDialog(Context mContext){
        this.mContext = mContext;
    }

    public void showTransientDialogWithOutAction(String title,String message){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext)
                .setTitle(title).setMessage(message);
        final AlertDialog alert = dialog.create();
        alert.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 2000);
    }
}
