package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.RoutineDataModel;
import com.dgpro.biddaloy.Network.Model.SearchDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Babu on 2/3/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchDataModel> searchDataList;
    RecycleViewItemClickListener clickListener;
    Context mContext;

    public SearchAdapter(Context mContext,List<SearchDataModel> searchDataList
            ,RecycleViewItemClickListener clickListener) {
        this.mContext = mContext;
        this.searchDataList = searchDataList;
        this.clickListener = clickListener;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName,userCatagory,userClassOrDept,userRoll;
        CircleImageView userImage;

        MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.search_user_name);
            userCatagory = (TextView) view.findViewById(R.id.search_user_catagory);
            userClassOrDept = (TextView) view.findViewById(R.id.dept_or_class);
            userRoll = (TextView) view.findViewById(R.id.roll);
            userImage = (CircleImageView) view.findViewById(R.id.search_user_image);
        }
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_search_view, parent, false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {
        SearchDataModel mModel = searchDataList.get(position);
        String userName = mModel.getName();
        String userCatagory = mModel.getCategory();
        String userClassOrDept = mModel.getDepartment();
        String userClass = mModel.getStudent_class();
        String userRoll = mModel.getId();


        holder.userName.setText(userName);
        holder.userCatagory.setText(userCatagory);

        if(userCatagory.contains("tudent")){
            String rl = "Roll : "+ userRoll;
            String stClass = "Class : "+userClass;
            holder.userRoll.setText(rl);
            holder.userClassOrDept.setText(stClass);
        }

        BiddaloyApplication biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
        String userImage = biddaloyApplication.baseUrl+mModel.getImage();

        Picasso.with(mContext)
                .load(userImage)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(holder.userImage);


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchDataList.size();
    }
}