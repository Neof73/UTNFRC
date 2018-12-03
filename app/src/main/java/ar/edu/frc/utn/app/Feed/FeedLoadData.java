package ar.edu.frc.utn.app.Feed;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 */

public class FeedLoadData extends AsyncTask<Void, Void, Cursor> {

    private FeedAdapterRecycler adapter;
    private Context context;
    private RecyclerView listView;
    private SwipeRefreshLayout swipeContainer;

    public FeedLoadData(Context context, RecyclerView listView, SwipeRefreshLayout swipeContainer) {
        this.context = context;
        this.listView = listView;
        this.swipeContainer = swipeContainer;
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        // Carga inicial de registros
        return FeedSQLDatabase.getInstance(context).obtenerEntradas();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);

        // Crear el adaptador
        adapter = new FeedAdapterRecycler(
                context, cursor);

        // Relacionar la lista con el adaptador
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(adapter);
        swipeContainer.setRefreshing(false);
    }
}