package ar.edu.frc.utn.app;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 */

public class FeedLoadData extends AsyncTask<Void, Void, Cursor> {

    private FeedAdapter adapter;
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout swipeContainer;

    public FeedLoadData(Context context, ListView listView, SwipeRefreshLayout swipeContainer) {
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
        adapter = new FeedAdapter(
                context,
                cursor,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // Relacionar la lista con el adaptador
        listView.setAdapter(adapter);
        swipeContainer.setRefreshing(false);
    }
}