package com.apurba2509.weathersnap // Make sure this matches your package name

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // --- FULL BROWSER-LIKE CONFIGURATION ---
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // Set a standard mobile browser User-Agent string
        webSettings.userAgentString = "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Mobile Safari/537.36"

        // --- ADVANCED WebViewClient TO HANDLE ERRORS ---
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                // This tells the WebView to ignore SSL certificate errors.
                handler?.proceed()
            }
        }

        webView.loadUrl("https://weather-snap-one.vercel.app/")

        // --- MODERN BACK BUTTON HANDLING ---
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    // If there's no history, finish the activity
                    finish()
                }
            }
        })
    }
}
