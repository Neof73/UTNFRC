package ar.edu.frc.utn.app;

/**
 * Created by Mario Di Giorgio on 22/09/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class BrowserG2M {
    private FragmentActivity context;
    private View view;
    private String mainurl;
    private NestedWebView browser;
    public BrowserG2M(final FragmentActivity context, Integer view, String Url) {
        this.context = context;
        this.browser = (NestedWebView) context.findViewById(view);
        this.mainurl = Url;
    }

    public void GetContent() {
        // Enable javascript
        browser.getSettings().setJavaScriptEnabled(true);
        //Fit content to screen..
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        // Set WebView client

        browser.setWebChromeClient(new WebChromeClient());
        //browser.addJavascriptInterface(new MyJavaScriptInterface(context), "HtmlViewer");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                //return false;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
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
