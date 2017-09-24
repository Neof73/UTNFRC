package ar.edu.frc.utn.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Mario Di Giorgio on 12/08/2017.
 *
 * Adaptador para inflar la lista de entradas
 */
public class FeedAdapter extends CursorAdapter {

    /*
    Etiqueta de Depuración
     */
    private static final String TAG = FeedAdapter.class.getSimpleName();

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        WebView titulo;
        WebView descripcion;
        NetworkImageView imagen;
        TextView pubdate;

        int tituloI;
        int descripcionI;
        int imagenI;
        int pubdateI;
    }

    public FeedAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.rss_item, null, false);

        ViewHolder vh = new ViewHolder();

        // Almacenar referencias
        vh.titulo = (WebView) view.findViewById(R.id.titulo);
        vh.descripcion = (WebView) view.findViewById(R.id.descripcion);
        //vh.imagen = (NetworkImageView) view.findViewById(R.id.imagen);
        vh.pubdate = (TextView) view.findViewById(R.id.pubDate);

        // Setear indices
        vh.tituloI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.TITULO);
        vh.descripcionI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.DESCRIPCION);
        vh.pubdateI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.PUBDATE);
        view.setTag(vh);

        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder vh = (ViewHolder) view.getTag();

        // Setear el texto al titulo
        String titulo = cursor.getString(vh.tituloI);

        /*
        try {
            titulo = Html.fromHtml( URLDecoder.decode( titulo, "UTF-8")).toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /*
        String titulo = null;
        try {
            titulo = new String(cursor.getString(vh.tituloI).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        vh.titulo.loadDataWithBaseURL("", titulo, "text/html", "UTF-8", ""); //.loadData(titulo, "text/html", "UTF-8"); //setText(titulo);

        // Obtener acceso a la descripción y su longitud
        //int ln = cursor.getString(vh.descripcionI).length();
        String descripcion = cursor.getString(vh.descripcionI);

        // Acortar descripción a 150 caracteres
        //if (ln >= 150)
        //    vh.descripcion.setText(descripcion.substring(0, 150)+"...");
        //else vh.descripcion.setText(descripcion);

        //vh.descripcion.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //vh.descripcion.getSettings().setLoadWithOverviewMode(true);
        //vh.descripcion.getSettings().setUseWideViewPort(true);
        descripcion = "<style> img { max-width: 100% !important; }</style>" + descripcion;
        vh.descripcion.loadDataWithBaseURL("", descripcion, "text/html", "UTF-8", "");
/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            vh.descripcion.setText(Html.fromHtml(descripcion, Html.FROM_HTML_MODE_LEGACY));
        } else {
            vh.descripcion.setText(Html.fromHtml(descripcion));
        }
*/
        String fecha = cursor.getString(vh.pubdateI);
        try {
            DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat dateFormatTo = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = dateFormatFrom.parse(fecha);
            fecha = dateFormatTo.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        vh.pubdate.setText(fecha);

        //} else {
        //    vh.pubdate.setText(fecha);
        //}
        // Obtener URL de la imagen
        //String thumbnailUrl = cursor.getString(vh.imagenI);

        // Obtener instancia del ImageLoader
        //ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        // Volcar datos en el image view
        //vh.imagen.setImageUrl(thumbnailUrl, imageLoader);

    }
}