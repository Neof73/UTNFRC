package ar.edu.frc.utn.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ar.edu.frc.utn.app.Browsers.BrowserEducacion;
import ar.edu.frc.utn.app.R;

public class FragmentCursos extends Fragment {
    private WebView browser;
    public FragmentCursos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.cursos_layout, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BrowserEducacion browserEducacion = new BrowserEducacion(getActivity(), R.id.cursos_webview, getString(R.string.cursosUrl), R.id.swipeCursos);
        browserEducacion.GetContent();

        /*
        WebView webView = (WebView) getActivity().findViewById(R.id.cursos_webview);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.cursosUrl));
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            WebView browser = (WebView) getActivity().findViewById(R.id.cursos_webview);
            // Load the webpage
            browser.loadUrl(getString(R.string.cursosUrl));
        }
        else {
        }
    }
    */
}
