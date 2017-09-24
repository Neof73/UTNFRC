package ar.edu.frc.utn.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.ParseException;

/**
 * Created by Usuario- on 20/10/2016.
 */

public class TabFragment3 extends Fragment {
    public static final String TAG = "ERR:FRAGMENT3";
    private ListView listView;
    private SwipeRefreshLayout swipeContainer;
    private View layoutNoticias;
    private FeedAdapter adapter;
    private boolean loaded = false;
    private boolean visible = false;

    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Obtener la lista
        layoutNoticias = inflater.inflate(R.layout.rss_list, container, false);
        return layoutNoticias;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.lista);
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRssFeed();
            }
        });
    }

    private void initialize() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && listView == null) {
            getRssFeed();
        } else {
            getLastRssFeed();
        }
    }

    private void getRssFeed() {
        swipeContainer.setRefreshing(true);
        VolleySingleton singleton = VolleySingleton.getInstance(getActivity());
        Response.Listener<Rss> response = new Response.Listener<Rss>() {
            @Override
            public void onResponse(Rss response) {
                // Caching
                catchResponse(response);
            }

            ;
        };
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                //Try to get last feed...
                getLastRssFeed();
            }
        };

        String URL_FEED = getResources().getString(R.string.rssURl);
        XmlRequest<Rss> request = new XmlRequest<Rss>(URL_FEED, Rss.class, null, response, responseError);
        singleton.addToRequestQueue(request);
    }

    private void getLastRssFeed() {
        Log.i(TAG, "La conexión a internet no está disponible");
        swipeContainer.setRefreshing(true);
        adapter = new FeedAdapter(
                getContext(),
                FeedSQLDatabase.getInstance(getContext()).obtenerEntradas(),
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
        swipeContainer.setRefreshing(false);
    }

    private void catchResponse(Rss response) {
        try {
            FeedSQLDatabase.getInstance(getActivity()).
                    sincronizarEntradas(response.getChannel().getItems());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Carga inicial de datos...
        new FeedLoadData(getActivity(), listView, swipeContainer).execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !loaded) {
            initialize();
            loaded = true;
        }
        visible = isVisibleToUser;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (loaded) {
            outState.putString("courseList", "loaded");
        }
        outState.putBoolean("loaded", loaded);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String sLoaded = savedInstanceState.getString("courseList", "");
            Boolean bLoaded = savedInstanceState.getBoolean("loaded", false);
            if (!sLoaded.isEmpty() && bLoaded) {
                loaded = true;
                getLastRssFeed();
            }
        }
    }
}