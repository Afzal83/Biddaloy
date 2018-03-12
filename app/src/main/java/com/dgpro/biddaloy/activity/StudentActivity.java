package com.dgpro.biddaloy.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.fragment.MyStudents.StudentPaymentStatusFragment;
import com.dgpro.biddaloy.fragment.MyStudents.StudentAttendenceFragment;
import com.dgpro.biddaloy.fragment.MyStudents.StudentDiaryFragment;
import com.dgpro.biddaloy.fragment.MyStudents.StudentResultFragment;
import com.dgpro.biddaloy.fragment.MyStudents.StudentRoutineFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class StudentActivity extends BaseStudentActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent intent = new Intent();
        biddaloyApplication.studentName = getIntent().getStringExtra("student_name");
        biddaloyApplication.studentRoll = getIntent().getStringExtra("student_id");
        biddaloyApplication.studentImage = getIntent().getStringExtra("student_image");
        biddaloyApplication.stuedentClass = getIntent().getStringExtra("student_class");
        biddaloyApplication.studentId = getIntent().getStringExtra("student_id");
        //Log.e("std name",""+biddaloyApplication.studentName);

        Log.e("stdActivity","name:"+biddaloyApplication.studentName);
        Log.e("stdActivity","studentRoll:"+biddaloyApplication.studentRoll);
        Log.e("stdActivity","studentId:"+biddaloyApplication.studentId);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_student_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(biddaloyApplication.studentName);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bindView();

        downLoadFromNetwork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            clearMemory();
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    void bindView(){
        CircleImageView img = (CircleImageView)findViewById(R.id.student_image) ;
        TextView studentId = (TextView)findViewById(R.id.roll) ;
        TextView studentClass = (TextView)findViewById(R.id.std_class) ;

        studentId.setText(biddaloyApplication.studentRoll);
        studentClass.setText(biddaloyApplication.stuedentClass);

        String url = biddaloyApplication.baseUrl+biddaloyApplication.studentImage;
        Picasso.with(this).load(url).fit().centerInside().into(img);
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
            {
                fragment = new StudentAttendenceFragment();
            }
            else if (position == 1)
            {
                fragment = new StudentDiaryFragment();
            }
            else if (position == 2)
            {
                fragment = new StudentRoutineFragment();
            }
            else if (position == 3)
            {
                fragment = new StudentResultFragment();
            }
            else if (position == 4)
            {
                fragment = new StudentPaymentStatusFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
            {
                title = "Attendence";
            }
            else if (position == 1)
            {
                title = "Diary";
            }
            else if (position == 2)
            {
                title = "Routine";
            }
            else if (position == 3)
            {
                title = "Result";
            }
            else if (position == 4)
            {
                title = "Payment";
            }
            return title;
        }
    }
    void createViewPager(){
        viewPager = (ViewPager) findViewById(R.id.student_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.students_tab_menu);
        tabLayout.setupWithViewPager(viewPager);
        wrapTabIndicatorToTitle(tabLayout,0,0);
    }

    @Override
    public void networkCallFinished() {
        super.networkCallFinished();
        createViewPager();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearMemory();
        finish();
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

}
