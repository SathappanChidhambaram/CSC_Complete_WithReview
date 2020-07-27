package com.example.csc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class website extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        webView =findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://vec-cts-studentclub.000webhostapp.com/");
    }
}
