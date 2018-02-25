package com.dgpro.biddaloy.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpro.biddaloy.Model.NewMessageModel;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.Network.Model.SearchDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.application.BiddaloyApplication;
import com.dgpro.biddaloy.constants.EmailConstants;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class SearchDetailsActivity extends AppCompatActivity {

    SearchDataModel searchResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);

        searchResult = Parcels.unwrap(getIntent()
                .getParcelableExtra("search_result"));

        bindView();
    }
    void bindView(){
        ImageView userImage_iv = findViewById(R.id.search_result_image);
        TextView userName_tv = findViewById(R.id.search_details_name);
        TextView userCategory_tv = findViewById(R.id.search_details_catagory);
        TextView userDeptOrClass_tv = findViewById(R.id.search_details_dept_or_class);
        TextView userRoll_tv = findViewById(R.id.search_details_roll);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewMessageModel newMessageModel = new NewMessageModel();
                newMessageModel.setReceiverId(searchResult.getId());
                newMessageModel.setReceiverName(searchResult.getName());
                newMessageModel.setReceiverCatagory(searchResult.getCategory());

                Intent intent = new Intent(SearchDetailsActivity.this, ComposeMessage.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(EmailConstants.MESSAGE_TO_SENT, Parcels.wrap(newMessageModel));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        BiddaloyApplication biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());
        String userImg = biddaloyApplication.baseUrl+searchResult.getImage();
        Picasso.with(this)
                .load(userImg)
                .placeholder(R.drawable.artifical_soft)
                .error(R.drawable.artifical_soft)
                .into(userImage_iv);

        userName_tv.setText(searchResult.getName());
        userCategory_tv.setText(searchResult.getCategory());

        if(searchResult.getCategory().contains("Student")){
            String uClass = "Class : "+ searchResult.getStudent_class();
            String roll = "ID : " + searchResult.getId();
            userRoll_tv.setText(roll);
            userDeptOrClass_tv.setText(uClass);

        }else if(searchResult.getCategory().contains("Guardian")){

        }else if(searchResult.getCategory().contains("Teacher")){
            String uDept = "Dept : "+ searchResult.getStudent_class();
            userDeptOrClass_tv.setText(uDept);
        }
    }
}
