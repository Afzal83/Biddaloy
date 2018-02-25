package com.dgpro.biddaloy.customview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dgpro.biddaloy.R;

import java.util.ArrayList;

/**
 * Created by Babu on 2/2/2018.
 */

public class DroupDownAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private ArrayList<String> mValues;
    private Context mContext;
    int resourceId ;

    public DroupDownAdapter(Context context, int resourceId, ArrayList<String> values) {
        super(context, resourceId, values);
        mValues = values;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.resourceId = resourceId ;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        parent.setPadding(0, 0, 0, 0);
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(resourceId, parent, false);
        TextView itemText = (TextView) view.findViewById(R.id.bolog_item_text);

        itemText.setText(mValues.get(position));
        if(position == 0){
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
            itemText.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }else{

        }
        return view;
    }
}
