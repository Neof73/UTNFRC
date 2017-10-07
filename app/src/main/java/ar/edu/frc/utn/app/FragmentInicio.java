package ar.edu.frc.utn.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment {
    private WebView browser;
    private boolean pageLoaded = false;
    private SwipeRefreshLayout swipeInicio;
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
        final BrowserEducacion browserEducacion = new BrowserEducacion(getActivity(), R.id.inicio_webview, getString(R.string.inicioUrl), R.id.swipeInicio);
        if (!pageLoaded) {
            browserEducacion.GetContent();
        }
    }

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
