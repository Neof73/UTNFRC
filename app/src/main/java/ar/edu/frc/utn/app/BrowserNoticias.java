package ar.edu.frc.utn.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.simpleframework.xml.strategy.VisitorStrategy;

import static ar.edu.frc.utn.app.R.id.imageView;

/**
 * Created by Mario Di Giorgio on 16/09/2017.
 */

public class BrowserNoticias {
    private FragmentActivity context;
    private View view;
    private String mainurl;
    private NestedWebView browser;
    private boolean loaded;
    public BrowserNoticias(final FragmentActivity context, Integer view, String Url) {
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
        browser.addJavascriptInterface(new WebAppInterface(context, browser), "Android");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                /*
                if (!loaded) {
                    view.setVisibility(View.INVISIBLE);
                }
                */
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loaded = true;
                /*view.loadUrl("javascript:window.addEventListener('DOMContentLoaded', function() { " +
                        "setTimeout(function (){ Android.showDocument(); }, 2000); " +
                        "}, false); ");*/

                view.loadUrl("javascript:(function() { " +
                        "var st = document.createElement('style'); " +
                        "st.type='text/css'; " +
                        "st.innerHTML = 'div { font-size:24px}'; " +
                        "document.head.appendChild(st); " +
                        "document.getElementsByTagName('table')[0].style.width='100%'; " +
                        "document.getElementsByTagName('tr')[0].style.display='none'; " +
                                "document.getElementsByTagName('tr')[1].style.display='none';"+
                        "document.getElementsByTagName('tr')[2].style.display='none'; "+
                        "document.getElementsByTagName('tr')[3].style.display='none'; "+
                        "document.getElementsByTagName('tr')[4].childNodes[1].style.display='none'; "+
                        "document.getElementsByTagName('tr')[4].childNodes[3].style.width='100%'; "+
                        "})()");


                //view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {
                //control you layout, show something like a retry button, and
                //call view.loadUrl(failingUrl) to reload.
                view.loadData("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><div style='display:inline-block;text-align:center;border: 2px solid #fc9a02;padding:15px;'><h1 style='font-size:24px;color:grey;'>Problemas de conexión, reintente mas tarde...</h1></div>", "text/html; charset=utf-8", "UTF-8");
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
                    NestedWebView webView = (NestedWebView) v;

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

    public class WebAppInterface {
        NestedWebView browser;
        FragmentActivity context;
        /** Instantiate the interface and set the context */
        WebAppInterface(FragmentActivity context, NestedWebView browser) {
            this.context = context;
            this.browser = browser;
        }

        /** Hide image view */
        @JavascriptInterface
        public void showDocument() {
            context.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    browser.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
