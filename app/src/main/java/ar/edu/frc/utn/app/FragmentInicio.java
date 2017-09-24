package ar.edu.frc.utn.app;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment {
    private WebView browser;
    private boolean pageLoaded = false;

    public FragmentInicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.inicio_layout, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!pageLoaded) {

            BrowserEducacion browserEducacion = new BrowserEducacion(getActivity(), R.id.inicio_webview, getString(R.string.inicioUrl));
            browserEducacion.GetContent();
        }
        /*
        WebView webView = (WebView) getActivity().findViewById(R.id.cursos_webview);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.cursosUrl));
        */

    }

    /*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && browser != null) {
            // Load the webpage
           browser.loadUrl(getString(R.string.inicioUrl));
        }
        else {
        }
    }
    */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("pageLoaded", pageLoaded);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            pageLoaded = savedInstanceState.getBoolean("pageLoaded", false);
            if (pageLoaded) {
                browser = (WebView) getActivity().findViewById(R.id.inicio_webview);
            }
        }
    }
}
