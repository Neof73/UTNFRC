package ar.edu.frc.utn.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ar.edu.frc.utn.app.Browsers.BrowserG2M;
import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 22/09/2017.
 */

public class FragmentG2M  extends Fragment {
    private WebView browser;
    public FragmentG2M() {
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
        View layout = inflater.inflate(R.layout.g2m_layout, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getActivity().findViewById(R.id.g2m_webview);
        SwipeRefreshLayout swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeG2M);
        BrowserG2M browserG2M = new BrowserG2M(getActivity(), view, getString(R.string.g2mUrl), swipe);
        browserG2M.GetContent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
