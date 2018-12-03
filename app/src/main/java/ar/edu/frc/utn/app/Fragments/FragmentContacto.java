package ar.edu.frc.utn.app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ar.edu.frc.utn.app.Browsers.BrowserEducacion;
import ar.edu.frc.utn.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContacto extends Fragment {
    private WebView browser;

    public FragmentContacto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.contacto_layout, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BrowserEducacion browserEducacion = new BrowserEducacion(getActivity(), R.id.contacto_webview, getString(R.string.contactoUrl), R.id.swipeContacto);
        browserEducacion.GetContent();

    }
}
