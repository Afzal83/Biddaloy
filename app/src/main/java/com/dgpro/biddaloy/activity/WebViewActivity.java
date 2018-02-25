package com.dgpro.biddaloy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.dgpro.biddaloy.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView = null;
    String resultUrl= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_details);
        resultUrl = getIntent().getStringExtra("url");
        Log.e("web Url :",resultUrl);
        setUpWebView();
    }
    void setUpWebView(){

        this.webView = (WebView) findViewById(R.id.webview);
       // WebSettings webSettings = webView.getSettings();
       // WebClient webViewClient = new WebClient(this);
        //webView.setWebViewClient(webViewClient);

        webView.loadUrl(resultUrl);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
