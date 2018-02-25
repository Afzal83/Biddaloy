package com.dgpro.biddaloy.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.dgpro.biddaloy.Model.NewMessageModel;
import com.dgpro.biddaloy.Network.Model.InboxDataModel;
import com.dgpro.biddaloy.Network.Model.MailDetailsModel;
import com.dgpro.biddaloy.Network.Model.OutboxDataModel;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.constants.EmailConstants;
import com.dgpro.biddaloy.serviceapi.MessageApi;

import org.parceler.Parcels;

import dmax.dialog.SpotsDialog;

public class MailDetailsActivity extends AppCompatActivity {

    public static final String MESSAGE_CATAGORY_INBOX = "inbox";
    public static final String MESSAGE_CATAGORY_OUTBOX = "outbox";

    MessageApi messageApi;

    private  String messageCatagory = "";
    private String messageId = "";

    private String messageSenderCagoty = "";
    private String messageSenderName = "";
    private String messageSenderId = "";
    private String messageSubject = "";
    private String messageBody = "";

    TextView mailFrom,mailTo,mailBody,mailSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_details);
        messageApi = new MessageApi(this);

        InboxDataModel inboxDataModel = Parcels.unwrap(getIntent()
                .getParcelableExtra(EmailConstants.MESSAGE_CATAGORY_INBOX));

        if(inboxDataModel != null){
            messageCatagory = MESSAGE_CATAGORY_INBOX;
            messageId = inboxDataModel.getMail_id();
        }
        OutboxDataModel outboxDataModel = Parcels.unwrap(getIntent()
                .getParcelableExtra(EmailConstants.MESSAGE_CATAGORY_OUTBOX));

        if(outboxDataModel != null){
            messageCatagory = MESSAGE_CATAGORY_OUTBOX;
            messageId = outboxDataModel.getMail_id();
        }

        //Log.e("data from message List","catagory : "+messageCatagory+" id : "+ messageId);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_student_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Message");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bindView();
        downLoadDetailMail();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.message_menu, menu);
        if(messageCatagory.contains(MESSAGE_CATAGORY_OUTBOX)){
            MenuItem item = menu.findItem(R.id.action_forward);
            item.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_reply:
                replyOrForwardMail(0);
                break;
            case R.id.action_forward:
                replyOrForwardMail(1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void bindView(){
        mailFrom = findViewById(R.id.message_from);
        mailTo = mailFrom ;
        mailBody = findViewById(R.id.message_body);
        mailSubject = findViewById(R.id.message_subject);
    }
    void downLoadDetailMail(){
        final AlertDialog dialog = new SpotsDialog(this);
        dialog.show();
        messageApi.downLoadDetailMail(messageId, new MessageApi.Callback<MailDetailsModel>() {
            @Override
            public void onSuccess(MailDetailsModel mailDetailsModel) {
                dialog.dismiss();
                messageSenderId = mailDetailsModel.getSender_id();
                messageSenderCagoty = mailDetailsModel.getSender_category();
                messageSenderName = mailDetailsModel.getSender_name();
                messageBody = mailDetailsModel.getMessage();
                messageSubject = mailDetailsModel.getSubject();
                updateView();
            }
            @Override
            public void onError(String errorMsg) {
                dialog.dismiss();
                //show error message ;
            }
        });
    }
    void replyOrForwardMail(int catagory){

        NewMessageModel newMessageModel = new NewMessageModel();
        newMessageModel.setSubject(messageSubject);

        switch (catagory){
            case 0: //reply
                newMessageModel.setReceiverId(messageSenderId);
                newMessageModel.setReceiverCatagory(messageSenderCagoty);
                newMessageModel.setReceiverName(messageSenderName);
                break;
            case 1: //forward
                newMessageModel.setMessageBody(messageBody);
                break;
            default:
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(EmailConstants.MESSAGE_TO_SENT
                ,Parcels.wrap(newMessageModel));

        Intent intent = new Intent(this,ComposeMessage.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    void updateView(){
        mailFrom.setText(messageSenderName);
        mailSubject.setText(messageSubject);
        mailBody.setText(messageBody);
    }
}
