package com.dgpro.biddaloy.fragment.MyStudents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.dgpro.biddaloy.Network.Model.AttendenceDataModel;
import com.dgpro.biddaloy.adapter.AttendenceAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.serviceapi.StudentApi;
import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Babu on 1/19/2018.
 */

public class StudentAttendenceFragment extends Fragment implements View.OnClickListener {

    View mView;
    RecyclerView attendenceListView;
    AttendenceAdapter attendencListAdapter;

    String studentName = "";
    String studentClass = "";
    String studentRoll = "";
    String  present = "";
    String absent = "";

    BiddaloyApplication biddaloyApplication;
    StudentApi studentApi;

    Button selectMonth;
    TextView presendDays ;
    TextView absentDays ;
    TextView presentDayInPercent ;
    TextView absentDayInPercent;

    RoundCornerProgressBar pBarPresent;
    RoundCornerProgressBar  pBarAbsent ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biddaloyApplication = ((BiddaloyApplication)getActivity().getApplicationContext());
        attendencListAdapter = new AttendenceAdapter(biddaloyApplication.attendentList);
        studentApi = new StudentApi(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_attendence,container,false);
        bindView();
        setAttendenceListView();
        updateView();
        return mView;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.select_month:
                selectMonth();
                break;
            default:
                break;
        }
    }
    void selectMonth(){
        new RackMonthPicker(getActivity())
                .setLocale(Locale.ENGLISH)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        Log.e("month: ","  "+month);
                        Log.e("year: ","  "+year);
                        downLoadAttendenceDataFromNetwork(Integer.toString(month)
                                ,Integer.toString(year));
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(AlertDialog dialog) {

                    }
                }).show();
    }

    void setAttendenceListView(){

        attendenceListView = (RecyclerView)mView. findViewById(R.id.recycler_view_for_attendence);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        attendenceListView.setLayoutManager(mLayoutManager);
        attendenceListView.setItemAnimator(new DefaultItemAnimator());

        attendenceListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        attendenceListView.setAdapter(attendencListAdapter);

    }
    void bindView(){

        selectMonth = mView.findViewById(R.id.select_month);
        selectMonth.setOnClickListener(this);

        presendDays = (TextView)mView.findViewById(R.id.day_presents);
        absentDays = (TextView)mView.findViewById(R.id.day_absent);
        presentDayInPercent = (TextView)mView.findViewById(R.id.presence_in_percentage);
        absentDayInPercent = (TextView)mView.findViewById(R.id.absent_in_percentage);
        pBarPresent = (RoundCornerProgressBar)mView.findViewById(R.id.progress_present);
        pBarAbsent = (RoundCornerProgressBar)mView.findViewById(R.id.progress_absent);


    }
    void updateView(){
        if(biddaloyApplication.studentPresentDay.isEmpty()
                || biddaloyApplication.studentAbsentDay.contentEquals("0")
                || biddaloyApplication.studentAbsentDay.isEmpty()
                || biddaloyApplication.studentPresentDay.contentEquals("0")){
            ((LinearLayout)mView.findViewById(R.id.attendence_bar_container)).setVisibility(View.GONE);
            return;
        }

        String sPresent = "Present "+biddaloyApplication.studentPresentDay+" days";
        String sAbsent = "Absent "+biddaloyApplication.studentAbsentDay+" days";
        presendDays.setText(sPresent);
        absentDays.setText(sAbsent);

        int totalPresentInMonth =  (Integer.parseInt(biddaloyApplication.studentPresentDay));
        int totalAbsentInMonth = (Integer.parseInt(biddaloyApplication.studentAbsentDay));

        float presenceInPercent = (totalPresentInMonth*100)/25;
        float absenceInParcent = (totalAbsentInMonth*100)/25;

        pBarPresent.setProgress(presenceInPercent);
        pBarAbsent.setProgress(absenceInParcent);

        String prestInPercentStr= Float.toString(presenceInPercent)+" %";
        String absentInPercentStr = Float.toString(absenceInParcent) + " %";

        absentDayInPercent.setText(absentInPercentStr);
        presentDayInPercent.setText(prestInPercentStr);

    }
    void downLoadAttendenceDataFromNetwork(String month,String year){

        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .show();
        studentApi.downLoadAttendenceDataFromNetwork(month
                ,year
                ,new StudentApi.Callback<List<AttendenceDataModel>>() {
                    @Override
                    public void onSuccess(List<AttendenceDataModel> attendenceDataModels) {
                        dialog.dismiss();
                        biddaloyApplication.attendentList.clear();
                        biddaloyApplication.attendentList.addAll(attendenceDataModels) ;
                        attendencListAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(String errorMsg) {
                        dialog.dismiss();
                        Log.e("attendence"," "+"refresh fail");
                    }
                });
    }

}
