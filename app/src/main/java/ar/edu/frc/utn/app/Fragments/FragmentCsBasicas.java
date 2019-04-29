package ar.edu.frc.utn.app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.frc.utn.app.Browsers.BrowserCienciasBasicas;
import ar.edu.frc.utn.app.Browsers.BrowserEducacion;
import ar.edu.frc.utn.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCsBasicas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCsBasicas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentCsBasicas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCsBasicas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCsBasicas newInstance(String param1, String param2) {
        FragmentCsBasicas fragment = new FragmentCsBasicas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.csbasicas_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BrowserCienciasBasicas browserCienciasBasicas = new BrowserCienciasBasicas(getActivity(), R.id.csbasicas_webview, getString(R.string.csBasicasUrl), R.id.swipeCsBasicas);
        browserCienciasBasicas.GetContent();
    }

}
