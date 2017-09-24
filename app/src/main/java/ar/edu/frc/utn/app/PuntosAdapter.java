package ar.edu.frc.utn.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import static android.R.drawable.ic_input_add;

/**
 * Created by Usuario- on 03/11/2016.
 */

public class PuntosAdapter extends BaseAdapter {
    private Context context;
    private final Activity actividad;
    private final String[] lista;

    public PuntosAdapter(Context context, Activity actividad, String[] lista) {
        super();
        this.context = context;
        this.actividad = actividad;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista.length;
    }

    @Override
    public Object getItem(int position) {
        return this.lista[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista_1, null, true);
        TextView textView = (TextView)view.findViewById(R.id.titulo);
        textView.setText(this.lista[position]);

        /*
        ImageView imgView = (ImageView)view.findViewById(R.id.imageView);
        int resourceId = context.getResources().getIdentifier(
                textView.getText().toString().toLowerCase(),
                "drawable",
                context.getPackageName()
        );
        if (resourceId != -1) {
            imgView.setImageResource(resourceId);
        }
        */

        return view;
    }
}
