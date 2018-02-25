package com.dgpro.biddaloy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.fragment.settings.NotificationSettingsFragment;
import com.dgpro.biddaloy.fragment.settings.PrivacySettingsFragment;

/**
 * Created by Babu on 2/2/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    String settingsCatagory = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsCatagory = getIntent().getStringExtra("setting");
        if(settingsCatagory.contains("privacy")){
            getSupportFragmentManager().beginTransaction().add(R.id.setting_item_container
                    ,new PrivacySettingsFragment()).commit();
        }
        else if(settingsCatagory.contains("notification")){
            getSupportFragmentManager().beginTransaction().add(R.id.setting_item_container
                    ,new NotificationSettingsFragment()).commit();
        }
        else{

        }


        Toolbar myToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Back");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
