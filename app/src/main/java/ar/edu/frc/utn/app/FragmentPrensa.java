package ar.edu.frc.utn.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPrensa extends Fragment {
    private WebView browser;

    public FragmentPrensa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.prensa_layout, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BrowserNoticias noticias = new BrowserNoticias(getActivity(), R.id.prensa_webview, getString(R.string.prensaUrl));
        noticias.GetContent();
        /*
        WebView webView = (WebView) getActivity().findViewById(R.id.prensa_webview);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.prensaUrl));
        */
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            WebView browser = (WebView) getActivity().findViewById(R.id.prensa_webview);
            // Load the webpage
            browser.loadUrl(getString(R.string.prensaUrl));
        }
    }
}
