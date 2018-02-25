package com.dgpro.biddaloy.Helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Babu on 1/24/2018.
 */

public class WebClient extends WebViewClient {

    private Activity activity = null;

    public WebClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (Uri.parse(url).getHost().equals("demo.101bd.com")) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }
}