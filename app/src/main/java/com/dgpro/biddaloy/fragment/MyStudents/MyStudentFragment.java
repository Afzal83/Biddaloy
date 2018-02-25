package com.dgpro.biddaloy.fragment.MyStudents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgpro.biddaloy.adapter.MyStudentAdapter;
import com.dgpro.biddaloy.CallBack.CallFromMyStudentList;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.StudentDataModel;
import com.dgpro.biddaloy.Network.Model.StudentListModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.activity.StudentActivity;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.serviceapi.StudentApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Babu on 12/31/2017.
 */

public class MyStudentFragment extends Fragment implements CallFromMyStudentList {

    BiddaloyApplication biddaloyApplication;
    StudentApi studentApi;

    View mView ;
    List<StudentDataModel> mList ;
    RecyclerView recyclerViewForMyStudent;
    MyStudentAdapter mAdapter;

    String sName = "";
    String sId = "";
    String sClass = "";
    String sDue = "";
    String sAdvance = "";
    String sTotalPaid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_stuednt,container,false);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        studentApi = new StudentApi(getActivity());

        downLoadStudentList();
        return  mView;
    }

    void downLoadStudentList(){

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Loading ...")
                .content("Please Wait")
                .progress(true, 0)
                .show();
        studentApi.downLoadStudentList(new StudentApi.Callback<List<StudentDataModel>>() {
            @Override
            public void onSuccess(List<StudentDataModel> studentDataModels) {
                dialog.dismiss();
                mList = studentDataModels;
                setAttendenceListView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
            }
        });
    }
    void setAttendenceListView(){

        recyclerViewForMyStudent = (RecyclerView)mView. findViewById(R.id.recycler_view_for_my_student);
        mAdapter = new MyStudentAdapter(getActivity(),mList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewForMyStudent.setLayoutManager(mLayoutManager);
        recyclerViewForMyStudent.setItemAnimator(new DefaultItemAnimator());

        recyclerViewForMyStudent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForMyStudent.setAdapter(mAdapter);

    }


    @Override
    public void onMyStudentListItemClick(int position) {
        //Log.e("my_student_fragment","get called from myStudent adapter");
        StudentDataModel sModel = mList.get(position);

        Log.e("name : ",""+sModel.getName());
        Log.e("image : ",""+sModel.getImage());

        Intent intent = new Intent(getActivity(),StudentActivity.class);
        intent.putExtra("student_name",sModel.getName());
        intent.putExtra("student_id",sModel.getId());
        intent.putExtra("student_image", sModel.getImage());
        intent.putExtra("student_class", sModel.getStudent_class());
        intent.putExtra("student_id", sModel.getId());
        startActivity(intent);
    }
}
