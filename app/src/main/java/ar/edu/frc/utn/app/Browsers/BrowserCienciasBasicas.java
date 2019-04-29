package ar.edu.frc.utn.app.Browsers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

import ar.edu.frc.utn.app.CustomView.NestedWebView;
import ar.edu.frc.utn.app.Main2Activity;
import ar.edu.frc.utn.app.R;

import static java.lang.Thread.sleep;

/**
 * Created by Mario Di Giorgio on 16/09/2017.
 */

public class BrowserCienciasBasicas {
    private FragmentActivity context;
    private View view;
    private String mainurl;
    private NestedWebView browser;
    private SwipeRefreshLayout swipe;
    final BrowserCienciasBasicas browserCienciasBasicas;
    public BrowserCienciasBasicas(final FragmentActivity context, Integer view, String Url, Integer swipe) {
        this.browserCienciasBasicas = this;
        this.context = context;
        this.browser = (NestedWebView) context.findViewById(view);
        this.mainurl = Url;
        this.swipe = (SwipeRefreshLayout) context.findViewById(swipe);

        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                browserCienciasBasicas.GetContent();
            }
        });
    }

    public void GetContent() {
        swipe.setRefreshing(true);
        // Enable javascript
        browser.getSettings().setJavaScriptEnabled(true);
        //Fit content to screen..
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        // Set WebView client

        browser.setWebChromeClient(new WebChromeClient());
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }



            @Override
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {
                //control you layout, show something like a retry button, and
                //call view.loadUrl(failingUrl) to reload.
                view.loadData("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><div style='display:inline-block;text-align:center;border: 2px solid #fc9a02;padding:15px;'><h1 style='font-size:24px;color:grey;'>Problemas de conexión, reintente mas tarde...</h1></div>","text/html; charset=utf-8", "UTF-8");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        browser.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        // Load the webpage with content async..
        browser.loadUrl(mainurl);
    }
}
