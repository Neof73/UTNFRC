package ar.edu.frc.utn.app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.frc.utn.app.Main2Activity;
import ar.edu.frc.utn.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMore extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static List<MoreOptions.MoreItem> list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentMore() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentMore newInstance() {
        FragmentMore fragment = new FragmentMore();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new MoreOptions(getContext()).ITEMS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(new FragmentMoreAdapter(getActivity(), list));
        return view;
    }

}
