package ar.edu.frc.utn.app;

/**
 * Created by Mario Di Giorgio on 22/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class BrowserG2M {
    private Context context;
    private String mainurl;
    private NestedWebView browser;
    private SwipeRefreshLayout swipe;
    final BrowserG2M browserG2M;
    public BrowserG2M(final Context context, View view, String Url, SwipeRefreshLayout swipe) {
        this.context = context;
        this.browser = (NestedWebView) view;
        this.mainurl = Url;
        this.browserG2M = this;
        this.swipe = swipe;
        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                browserG2M.GetContent();
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
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
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
                String sData = "<!DOCTYPE html><html><head><title>Connection Error</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></meta></link></head><body><div style='display:inline-block;text-align:center;border: 2px solid #fc9a02;padding:15px;'><h1 style='font-size:24px;color:grey;'>Problemas de conexión, reintente mas tarde...</h1></div></body></html>";
                view.loadData(sData, "text/html; charset=utf-8", "UTF-8");
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
