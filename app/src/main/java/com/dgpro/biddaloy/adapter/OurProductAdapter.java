package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.Network.Model.ProductModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Babu on 2/10/2018.
 */

public class OurProductAdapter extends RecyclerView.Adapter<OurProductAdapter.MyViewHolder> {

    private List<ProductModel> productList;
    private CallFromMessageList mCallFromMessageList ;
    Context mContext ;

    public OurProductAdapter(Context mContext ,List<ProductModel> productList,CallFromMessageList mCallFromMessageList) {
        this.productList = productList;
        this.mCallFromMessageList = mCallFromMessageList;
        this.mContext = mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;

        MyViewHolder(View view) {
            super(view);
            productImage = (ImageView) view.findViewById(R.id.product_image);
            productName = (TextView) view.findViewById(R.id.product_name);
        }
    }

    @Override
    public OurProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_our_product, parent, false);
        return new OurProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OurProductAdapter.MyViewHolder holder,final int position) {
        ProductModel mModel = productList.get(position);
        holder.productName.setText(mModel.getTitle());

        Picasso.with(mContext)
                .load(mModel.getImage())
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallFromMessageList.onMessageListClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(null == productList) return 0;
        return productList.size();
    }
}
