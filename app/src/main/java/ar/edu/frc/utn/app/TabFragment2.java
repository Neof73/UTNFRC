package ar.edu.frc.utn.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario- on 20/10/2016.
 */

public class TabFragment2 extends Fragment {
    ListView listview;
    Button btnDownload;
    Boolean downloaded = false;
    //private JSONObject currentObject;
    private ArrayList<Course> currentList;
    private EditText searchText;

    public TabFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listview = (ExpandableListView) getActivity().findViewById(R.id.expListView);
        searchText = (EditText) getActivity().findViewById(R.id.search);
        btnDownload = (Button) getActivity().findViewById(R.id.button);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProcessJson proc = new ProcessJson(getActivity());
                final String spreadsheetID = "1vZfLj-vfK_qgRIj3yrbM-Ya8DlvSvrfFyOOUQB1Z4_0";
                final String url = "https://spreadsheets.google.com/feeds/list/" + spreadsheetID + "/od6/public/values?alt=json";
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());

                if (currentList == null) {
                    new GetCronograma(new AsyncResultList(){
                        @Override
                        public void onResult(ArrayList<Course> list){
                            currentList = list;
                            proc.processList(list);
                        }
                    }, getContext(), progressDialog).execute("");
                    //new DownloadWebpageTask(new AsyncResult() {
                    //    @Override
                    //    public void onResult(JSONObject object) {
                    //        currentObject = object;
                    //        proc.processJson(object);
                    //    }
                    //}, progressDialog).execute(url);
                } else {
                    proc.processList(currentList);
                    //proc.processJson(currentObject);
                }
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
                View view = getActivity().findViewById(R.id.button);
                final ProcessJson proc = new ProcessJson(getActivity());
                proc.processList(currentList);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !downloaded) {
            btnDownload.callOnClick();
            downloaded = true;
        }
    }
}



