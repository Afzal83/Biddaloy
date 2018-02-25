package com.dgpro.biddaloy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dgpro.biddaloy.Model.NewMessageModel;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.Network.Model.SentMailResponseModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.constants.EmailConstants;
import com.dgpro.biddaloy.dialog.TransientDialog;
import com.dgpro.biddaloy.serviceapi.MessageApi;

import org.parceler.Parcels;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeMessage extends AppCompatActivity implements View.OnClickListener{

    MessageApi messageApi;
    NewMessageModel newMessageModel;
    TransientDialog transientDialog;

    private String composeCatagory = "";

    Button searchSender_btn;
    EditText mailBOdy_et,mailSubject_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        messageApi = new MessageApi(this);
        transientDialog = new TransientDialog(this);

        newMessageModel = Parcels.unwrap(getIntent()
                .getParcelableExtra(EmailConstants.MESSAGE_TO_SENT));
        if(null == newMessageModel){
            newMessageModel = new NewMessageModel();
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.compose_mail_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Compose Mail");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Log.e("mail body : ###","##"+newMessageModel.getMessageBody());

        bindView();
        updateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose_mail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        else if(item.getItemId() ==  R.id.action_send){
            sendMail();
        }
        return super.onOptionsItemSelected(item);
    }

    void bindView(){

        mailSubject_et  = (EditText)findViewById(R.id.send_message_subject);
        mailBOdy_et = (EditText)findViewById(R.id.send_message_body);
        searchSender_btn = (Button)findViewById(R.id.search_sender);

        ((Button)findViewById(R.id.search_sender)).setOnClickListener(this);
    }
    void updateView(){

        mailSubject_et.setText(newMessageModel.getSubject());
        mailBOdy_et.setText(newMessageModel.getMessageBody());
        searchSender_btn.setText(newMessageModel.getReceiverName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_sender:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("backResult",0);
                startActivityForResult(intent, 420);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 420) {
            if (resultCode == RESULT_OK) {
                newMessageModel.setReceiverName(data.getStringExtra("name"));
                newMessageModel.setReceiverCatagory(data.getStringExtra("category"));
                newMessageModel.setReceiverId(data.getStringExtra("id"));
                getMailBodyAndSubject();
                updateView();
            }
        }
    }
    void getMailBodyAndSubject(){
        newMessageModel.setMessageBody(mailBOdy_et.getText().toString());
        newMessageModel.setSubject(mailSubject_et.getText().toString());
    }
    void sendMail(){
        getMailBodyAndSubject();
        if(!authenticateMail()){
            return;
        }

        final android.app.AlertDialog dialog = new SpotsDialog(this);
        dialog.show();

        messageApi.submitMail(newMessageModel, new MessageApi.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                dialog.dismiss();
                transientDialog.showTransientDialogWithOutAction("Success...","Message sent Successfully..");
            }

            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                transientDialog.showTransientDialogWithOutAction("Error...","Message sent Fail..");

            }
        });
    }
    Boolean authenticateMail(){
        if(newMessageModel.getReceiverName().isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","Please Select Receiver");
            return false;
        }
        if(newMessageModel.getSubject().isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","Message Subject is Empty");
            return false;
        }
        if(newMessageModel.getMessageBody().isEmpty()){
            transientDialog.showTransientDialogWithOutAction("Error...","Message Body is Empty");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
