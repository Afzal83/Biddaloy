package com.dgpro.biddaloy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.BlogDataModel;
import com.dgpro.biddaloy.R;

import java.util.List;

/**
 * Created by Babu on 1/27/2018.
 */

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private List<BlogDataModel> BlogList;
    RecycleViewItemClickListener clickListener;

    public BlogAdapter(List<BlogDataModel> BlogList,RecycleViewItemClickListener clickListener) {
        this.BlogList = BlogList;
        this.clickListener = clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView blog;

        MyViewHolder(View view) {
            super(view);
            blog = (TextView) view.findViewById(R.id.blog);
        }
    }

    @Override
    public BlogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_blog_list, parent, false);
        return new BlogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogAdapter.MyViewHolder holder, final int position) {
        BlogDataModel mModel = BlogList.get(position);
        holder.blog.setText(mModel.getBlog_details());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BlogList.size();
    }
}