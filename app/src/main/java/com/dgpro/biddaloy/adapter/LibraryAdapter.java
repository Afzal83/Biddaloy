package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMessageList;
import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.Model.LibraryModel;
import com.dgpro.biddaloy.Network.Model.ProductModel;
import com.dgpro.biddaloy.R;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by Babu on 2/10/2018.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {

    private List<LibraryModel> libraryList;
    private RecycleViewItemClickListener mCallFromLibrary ;
    Context mContext ;

    public LibraryAdapter(Context mContext ,List<LibraryModel> libraryList,RecycleViewItemClickListener mCallFromLibrary) {
        this.libraryList = libraryList;
        this.mCallFromLibrary = mCallFromLibrary;
        this.mContext = mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;

        MyViewHolder(View view) {
            super(view);
            bookName = (TextView) view.findViewById(R.id.book_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_library_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        LibraryModel mModel = libraryList.get(position);
        holder.bookName.setText(mModel.getBook_name());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallFromLibrary.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return libraryList.size();
    }
}
