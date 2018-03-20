package com.dgpro.biddaloy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dgpro.biddaloy.Helper.Constants;
import com.dgpro.biddaloy.Model.LoginModel;
import com.dgpro.biddaloy.Network.Model.LoginDataModel;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.fragment.AboutUsFragment;
import com.dgpro.biddaloy.fragment.HomeFragment;
import com.dgpro.biddaloy.fragment.Message.MessageFragment;
import com.dgpro.biddaloy.fragment.MyStudents.MyStudentFragment;
import com.dgpro.biddaloy.fragment.NoticeFragment;
import com.dgpro.biddaloy.fragment.BlogFragment;
import com.dgpro.biddaloy.fragment.ProfileFragment;
import com.dgpro.biddaloy.fragment.TrackingFragment;
import com.dgpro.biddaloy.fragment.settings.SettingsFragment;
import com.dgpro.biddaloy.serviceapi.UserApi;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BiddaloyApplication biddaloyApplication;
    Toolbar toolbar;
    AppBarLayout mAappbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationctivity);

        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());

        mAappbarLayout = (AppBarLayout)findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_navigationctivity);
        CircleImageView imgvw = (CircleImageView)hView.findViewById(R.id.header_image);
        TextView userName = (TextView)hView.findViewById(R.id.user_name);
        TextView schoolName = (TextView)hView.findViewById(R.id.school_name);
        userName.setText(biddaloyApplication.userName);
        schoolName.setText(biddaloyApplication.schoolName);
       // Bitmap b= new ImageHelper().loadImageFromStorage(biddaloyApplication.usreImageUrl, "attila.png");
        Uri savedImageURI = Uri.parse(biddaloyApplication.usreImageUrl);
        // Display the saved image to ImageView
        imgvw.setImageURI(savedImageURI);

        getSupportFragmentManager().beginTransaction().add(R.id.item_container,new HomeFragment()).commit();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.item_container);
                if (f != null){
                    updateTitleAndDrawer (f);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigationctivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this,SearchActivity.class));
                return true;
             default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(new HomeFragment());
            setTitle ("Home");

        }else if(id == R.id.nav_student){
            if(biddaloyApplication.userCatagory.contains("eacher")){
                Toast.makeText(this,"This featue is not implimented for you yet",
                        Toast.LENGTH_LONG).show();
            }else{
                replaceFragment(new MyStudentFragment());
                setTitle ("My Students");
            }
        }
        else if (id == R.id.nav_notice) {
            replaceFragment(new NoticeFragment());
            setTitle ("Notice");
        }
        else if (id == R.id.nav_message) {
            replaceFragment(new MessageFragment());
            setTitle ("Message");
            ViewCompat.setElevation(mAappbarLayout, 0);
            //getSupportActionBar().setElevation(0);
            //mAappbarLayout.setElevation(0);
        }

        else if (id == R.id.nav_blog) {
            replaceFragment(new BlogFragment());
            setTitle ("Blog");
        }
        else if (id == R.id.nav_biddaloy) {

            replaceFragment(new AboutUsFragment());
            setTitle ("About Us");
        }
        else if (id == R.id.nav_settings) {
            replaceFragment(new SettingsFragment());
            setTitle ("Settings");
        }
        else if (id == R.id.nav_tracking) {
            replaceFragment(new TrackingFragment());
            setTitle ("Tracking");
        }
        else if (id == R.id.nav_profile) {
            replaceFragment(new ProfileFragment());
            setTitle ("Profile");
        }
        else if (id == R.id.nav_logout) {

            new UserApi(this).insertLoginModelToSharePreference(getBaseContext(),new LoginModel());
            startActivity(new Intent(this,LandingActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.item_container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    private void updateTitleAndDrawer (Fragment fragment){

        String fragClassName = fragment.getClass().getName();
        if (fragClassName.equals(HomeFragment.class.getName())){
            setTitle ("Home");
        }
        else if (fragClassName.equals(MyStudentFragment.class.getName())){
            setTitle ("My Students");
        }
    }
}
