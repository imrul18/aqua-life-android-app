package com.imrul.aqua_life.screens.main

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun Site(
    navController: NavController,
    innerPadding: PaddingValues
) {
    var url = "https://www1.aqua-global.com"
    var webViewBundle by remember { mutableStateOf<Bundle?>(null) }
    Scaffold() { innerPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
        ) {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    CookieManager.getInstance().setAcceptCookie(true)
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    settings.setSupportZoom(true)
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()


                    webViewBundle?.let {
                        restoreState(it)
                    } ?: loadUrl(url)
                }
            }, update = { webView ->
                webViewBundle?.let {
                    webView.restoreState(it)
                } ?: webView.loadUrl(url)
            })
            DisposableEffect(Unit) {
                onDispose {
                    val newBundle = Bundle()
                    webViewBundle = newBundle
                }
            }
        }
    }

}