package ar.edu.frc.utn.app;

/**
 * Created by Mario Di Giorgio on 22/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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

public class BrowserPress {
    private Context context;
    private NestedWebView browser;
    private ViewGroup viewGroup;
    public BrowserPress(final Context context, View view, ViewGroup viewGroup) {
        this.context = context;
        this.browser = (NestedWebView) view;
        this.viewGroup = viewGroup;
        Initialize();
    }

    private void Initialize() {
        // Enable javascript
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        //Fit content to screen..
        //browser.getSettings().setLoadWithOverviewMode(true);
        //browser.getSettings().setUseWideViewPort(true);
        // Set WebView client

        browser.setWebChromeClient(new WebChromeClient());
        //browser.addJavascriptInterface(new ShowCardJavaScriptInterface(), "ShowCard");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                //return false;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (viewGroup != null)
                   viewGroup.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (viewGroup != null)
                    viewGroup.setVisibility(View.VISIBLE);
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
        //browser.loadUrl(mainurl);
    }

    public void SetContent(String content) {
        //content = "<script>javascript:window.addEventListener('DOMContentLoaded', function() { " +
        //        "setTimeout(function (){ ShowCard.showDocument(); }, 100); " +
        //        "}, false);</script> " + content;
        content = content.replace("\"//","\"https://");
        browser.loadDataWithBaseURL("", content, "text/html", "UTF-8", null);
    }

/*
    public class ShowCardJavaScriptInterface {

        @JavascriptInterface
        public void showDocument() {
            ((FragmentActivity)context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (viewGroup != null)
                        viewGroup.setVisibility(View.VISIBLE);
                }
            });
        }
    }
*/
}
