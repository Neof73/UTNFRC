package ar.edu.frc.utn.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mario Di Giorgio on 30/09/2017.
 */

public class FeedAdapterRecycler extends  RecyclerView.Adapter<FeedAdapterRecycler.FeedAdapterHolder> {
    public Cursor FeedCursor;
    public Context context;
    public FeedAdapterRecycler(Context context, Cursor cursor) {
        this.FeedCursor = cursor;
        this.context = context;
    }

    public class FeedAdapterHolder extends RecyclerView.ViewHolder {
        protected WebView titulo;
        private BrowserPress descripcion;
        private TextView pubdate;
        private CardView card;
        public FeedAdapterHolder(View itemView, Context context) {
            super(itemView);
            this.titulo = (WebView) itemView.findViewById(R.id.titulo);
            this.card = (CardView) itemView.findViewById(R.id.card_press);
            this.descripcion = new BrowserPress(context, itemView.findViewById(R.id.descripcion), card);
            this.pubdate = (TextView) itemView.findViewById(R.id.pubDate);
        }
    }

    @Override
    public FeedAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_item, parent, false);

        return new FeedAdapterHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(FeedAdapterHolder holder, int position) {
        FeedCursor.moveToPosition(position);
        int tituloI = FeedCursor.getColumnIndex(ScriptDatabase.ColumnEntradas.TITULO);
        int descripcionI = FeedCursor.getColumnIndex(ScriptDatabase.ColumnEntradas.DESCRIPCION);
        int pubdateI = FeedCursor.getColumnIndex(ScriptDatabase.ColumnEntradas.PUBDATE);

        // Setear el texto al titulo
        String titulo = FeedCursor.getString(tituloI);
        holder.titulo.loadDataWithBaseURL("", titulo, "text/html", "ISO-8859-1", "");

        String descripcion = FeedCursor.getString(descripcionI);

        descripcion = "<style> img { max-width: 100% !important; }</style>" + descripcion;
        holder.descripcion.SetContent(descripcion);
        String fecha = FeedCursor.getString(pubdateI);
        try {
            DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = dateFormatFrom.parse(fecha);
            fecha = dateFormatTo.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        holder.pubdate.setText(fecha);

    }

    @Override
    public int getItemCount() {
        return FeedCursor.getCount();
    }
}
