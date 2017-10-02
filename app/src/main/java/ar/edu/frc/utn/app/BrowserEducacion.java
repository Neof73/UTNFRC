package ar.edu.frc.utn.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mario Di Giorgio on 16/09/2017.
 */

public class BrowserEducacion {
    private FragmentActivity context;
    private View view;
    private String mainurl;
    private NestedWebView browser;
    public BrowserEducacion(final FragmentActivity context, Integer view, String Url) {
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
                //view.stopLoading();
                //GetContentAsync contentAsync = new GetContentAsync();
                //contentAsync.execute();
                //view.loadData(content, "text/html", null

                if (Uri.parse(url).getHost().equals("educacionadistancia.frc.utn.edu.ar")) {
                    // This is my web site, so do not override; let my WebView load the page
                    if (Uri.parse(url).getPath().contains("") && !Uri.parse(mainurl).getPath().contains("")) {
                        final ViewPager viewPager = (ViewPager) context.findViewById(R.id.pager);
                        view.stopLoading();
                        viewPager.setCurrentItem(0);
                    }
                    if (Uri.parse(url).getPath().contains("cursos") && !Uri.parse(mainurl).getPath().contains("cursos")) {
                        final ViewPager viewPager = (ViewPager) context.findViewById(R.id.pager);
                        view.stopLoading();
                        viewPager.setCurrentItem(1);
                    }
                    if (Uri.parse(url).getPath().contains("contacto") && !Uri.parse(mainurl).getPath().contains("contacto")) {
                        final ViewPager viewPager = (ViewPager) context.findViewById(R.id.pager);
                        view.stopLoading();
                        viewPager.setCurrentItem(4);
                    }
                    return false;
                }
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                /*view.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                view.stopLoading();*/

                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('menu-mobile-effect navbar-toggle')[0].style.display='none'; " +
                        "})()");

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

    // AsyncTask
    private class GetContentAsync extends AsyncTask<Void, Void, String> {
        Document doc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // Connect to the web site
                doc = Jsoup.connect(mainurl).userAgent("Mozilla").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Set title into TextView
            doc.getElementsByClass("menu-mobile-effect navbar-toggle").remove();
        }
    }


    /*
    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            try {
                org.jsoup.nodes.Document document = Jsoup.parse(html, "ISO-8859-1");
                document.getElementsByClass("menu-mobile-effect navbar-toggle").remove();
                browser.loadData(document.toString(), "text/html", "ISO-8859-1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
            //        .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }
    }
    */

}
