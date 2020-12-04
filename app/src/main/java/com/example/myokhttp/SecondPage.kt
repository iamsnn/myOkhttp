package com.example.myokhttp

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SecondPage: AppCompatActivity(){

    private lateinit var secondPage_webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.second_layout)
        secondPage_webview = findViewById<WebView>(R.id.secondPage_webview)
        secondPage_webview.settings.javaScriptEnabled = true
        secondPage_webview.settings.useWideViewPort = true
        secondPage_webview.settings.loadWithOverviewMode = true

        val link = intent.getStringExtra(DetailActivity.myViewHolder.VIDEO_LINK_KEY)
        if (link != null) {
            secondPage_webview.loadUrl(link)
        }
        else{
            secondPage_webview.loadUrl("www.google.com")
        }

    }

}