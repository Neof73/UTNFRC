package ar.edu.frc.utn.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class CourseAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Course> courseList;

    public CourseAdapter(Context context, int elemento_lista_1, ArrayList<Course> courseList) {
        this.context = context;
        this.resource = elemento_lista_1;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista_2, null, true);

        if (!this.courseList.get(position).getAnio().equals("")) {
            TextView anio = (TextView) view.findViewById(R.id.anio);
            anio.setText(this.courseList.get(position).getAnio());
        } else {
            TextView anio = (TextView) view.findViewById(R.id.anio);
            anio.setVisibility(View.GONE);
        }


        if (!this.courseList.get(position).getNombre().equals("")) {

            TextView titulo = (TextView) view.findViewById(R.id.titulo);
            titulo.setText(this.courseList.get(position).getNombre());

            TextView docente = (TextView)view.findViewById(R.id.docente);
            if (this.courseList.get(position).getDocente().equals(""))
                docente.setText("");
            else
                docente.setText("Docente: " + this.courseList.get(position).getDocente());
        } else {
            TextView titulo = (TextView) view.findViewById(R.id.titulo);
            titulo.setVisibility(View.GONE);

            TextView docente = (TextView) view.findViewById(R.id.docente);
            docente.setVisibility(View.GONE);
        }

        if (!this.courseList.get(position).getClases().equals("")) {
            TextView clases = (TextView) view.findViewById(R.id.clases);
            clases.setText("Unidad: " + this.courseList.get(position).getClases());
        } else {
            TextView clases = (TextView) view.findViewById(R.id.clases);
            clases.setVisibility(View.GONE);
        }

        if (!this.courseList.get(position).getPresentacion().equals("")) {
            TextView presentacion = (TextView) view.findViewById(R.id.presentacion);
            presentacion.setText(this.courseList.get(position).getPresentacion());
        } else {
            TextView presentacion = (TextView) view.findViewById(R.id.presentacion);
            presentacion.setVisibility(View.GONE);
        }


        if (!this.courseList.get(position).getFecha().equals("")) {
            TextView fecha = (TextView) view.findViewById(R.id.fecha);
            fecha.setText("Fecha: " + this.courseList.get(position).getFecha());
        } else {
            TextView fecha = (TextView) view.findViewById(R.id.fecha);
            fecha.setText("");
        }

        return view;
    }
}
