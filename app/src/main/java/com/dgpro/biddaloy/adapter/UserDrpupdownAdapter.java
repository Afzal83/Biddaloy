package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dgpro.biddaloy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Babu on 2/27/2018.
 */

public class UserDrpupdownAdapter  extends  ArrayAdapter<String>{

    private final LayoutInflater mInflater;
    private List<String> mValues;
    private Context mContext;
    int resourceId ;

    public UserDrpupdownAdapter(Context context, int resourceId,List<String> values) {
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
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dorupdown_header_color));
            itemText.setTextColor(ContextCompat.getColor(getContext(), R.color.dorupdown_header_text_color));
        }else{

        }
        return view;
    }
}
