package com.example.ingtips;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {
    private WebView wb;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        wb = (WebView) findViewById(R.id.webview);

        String url = getIntent().getStringExtra("url");
        wb.setWebViewClient(new WebViewClient());wb.loadUrl(url);

        wb.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);


    }
}
