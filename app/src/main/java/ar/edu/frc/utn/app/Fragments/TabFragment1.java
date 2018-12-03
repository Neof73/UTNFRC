package ar.edu.frc.utn.app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.List;

import ar.edu.frc.utn.app.R;

/**
 * Created by Usuario- on 20/10/2016.
 */

public class TabFragment1 extends Fragment implements AdapterView.OnItemClickListener {
    private List headlines;
    private List links;
    private String currentResponse;
    private ExpandableListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View institucionalLayout = inflater.inflate(R.layout.institucional_list, container, false);
        return institucionalLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ExpandableListView) getActivity().findViewById(R.id.listViewGroup);
        //final WebView webView = (WebView) getActivity().findViewById(R.id.institucional_webview);

        final InstitucionalAdapterExpandable adapter = new InstitucionalAdapterExpandable(getContext());
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
