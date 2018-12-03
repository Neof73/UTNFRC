package ar.edu.frc.utn.app.Browsers;

/**
 * Created by Mario Di Giorgio on 22/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ar.edu.frc.utn.app.CustomView.NestedWebView;

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

            @Override
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {
                //control you layout, show something like a retry button, and
                //call view.loadUrl(failingUrl) to reload.
                String sData = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><div style='display:inline-block;text-align:center;border: 2px solid #fc9a02;padding:15px;'><h1 style='font-size:24px;color:grey;'>Problemas de conexión, reintente mas tarde...</h1></div>";
                view.loadData(sData , "text/html; charset=utf-8",  "UTF-8");
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
