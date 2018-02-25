package com.dgpro.biddaloy.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Babu on 2/1/2018.
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void showDialog(String dTitle,String dMessage
            ,Boolean positiveButton,String pBtnTitle
            ,Boolean negativeButton,String nBtnTitle
            ,Boolean neutralButrton,String nuBtnTitle){

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(dTitle);
        alertDialog.setMessage(dMessage);
        if(positiveButton){
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, pBtnTitle,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        if(negativeButton){
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, nBtnTitle,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

        }
        if(neutralButrton){
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, nuBtnTitle,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        alertDialog.show();
    }
}
