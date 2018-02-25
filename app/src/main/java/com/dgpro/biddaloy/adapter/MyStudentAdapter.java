package com.dgpro.biddaloy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.CallBack.CallFromMyStudentList;
import com.dgpro.biddaloy.Network.Model.StudentDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.squareup.picasso.Picasso;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Babu on 12/31/2017.
 */

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.MyViewHolder> {

    private List<StudentDataModel> MyStudentList;
    private CallFromMyStudentList callFromStudentList ;
    Context mContext;

    public MyStudentAdapter(Context mContext,List<StudentDataModel> MyStudentList,CallFromMyStudentList callFromStudentList) {

        this.mContext = mContext;
        this.MyStudentList = MyStudentList;
        this.callFromStudentList = callFromStudentList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentClass,studentId;
        CircleImageView studentImage;
        MyViewHolder(View view) {
            super(view);
            studentImage = (CircleImageView)view.findViewById(R.id.student_image);
            studentName = (TextView) view.findViewById(R.id.my_stuent_name);
            studentClass = (TextView) view.findViewById(R.id.student_class);
            studentId = (TextView) view.findViewById(R.id.student_std_id);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_for_my_student_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        StudentDataModel mStudent = MyStudentList.get(position);

        holder.studentName.setText(mStudent.getName());
        holder.studentClass.setText(mStudent.getStudent_class());
        holder.studentId.setText(mStudent.getId());

        BiddaloyApplication biddaloyApplication = ((BiddaloyApplication)mContext.getApplicationContext());
        String userImageUrl = biddaloyApplication.baseUrl+mStudent.getImage();
        Log.e("senderImage",""+userImageUrl);
        Picasso.with(mContext)
                .load(userImageUrl)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(holder.studentImage);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                callFromStudentList.onMyStudentListItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MyStudentList.size();
    }
}