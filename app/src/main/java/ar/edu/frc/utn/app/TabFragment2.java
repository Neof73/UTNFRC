package ar.edu.frc.utn.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Usuario- on 20/10/2016.
 */

public class TabFragment2 extends Fragment {
    ListView listview;
    Button btnDownload;
    Boolean downloaded = false;
    private ArrayList<Course> currentList;
    private EditText searchText;
    public ProcessCourses proc;
    public SwipeRefreshLayout swipe;
    public TabFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        proc = new ProcessCourses(getActivity());
        listview = (ExpandableListView) getActivity().findViewById(R.id.expListView);
        searchText = (EditText) getActivity().findViewById(R.id.search);
        btnDownload = (Button) getActivity().findViewById(R.id.button);
        swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeCrono);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                new GetCronograma(new AsyncResultList(){
                    @Override
                    public void onResult(ArrayList<Course> list){
                        currentList = list;
                        proc.processList(list);
                    }
                }, getContext(), /*progressDialog*/swipe).execute("");
            }
        });
        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btnDownload.callOnClick();
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            btnDownload.setEnabled(true);
        } else {
            btnDownload.setEnabled(false);
        }
    }

    private void UpdateCronograma() {
        if (currentList == null) {
            btnDownload.callOnClick();
        } else {
            proc.processList(currentList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_read_excel_from_url, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentList != null) {
            String string_object = new Gson().toJson(currentList);
            outState.putString("listObject", string_object);
            outState.putString("searchText", searchText.getText().toString());
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState !=  null) {
            String sObject = savedInstanceState.getString("listObject", "").replace("\\", "");
            if (!sObject.isEmpty()) {
                currentList = new Gson().fromJson(sObject, new TypeToken<ArrayList<Course>>(){}.getType());
            }
            String sSearchText = savedInstanceState.getString("searchText", "");
            if (!sSearchText.isEmpty()) {
                searchText.setText(sSearchText);
            }
            if (currentList != null) {
                proc.processList(currentList);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !downloaded) {
            UpdateCronograma();
        }
    }
}



