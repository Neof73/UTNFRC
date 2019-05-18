package ar.edu.frc.utn.app.Course;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.frc.utn.app.Course.Course;
import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class CourseAdapter extends BaseAdapter implements Filterable {
    Context context;
    int resource;
    private ArrayList<Course> courseList;
    private ItemFilter mFilter = new ItemFilter();

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
       return view;
    }

    @Override
     public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Course> list = courseList;

            int count = list.size();
            final ArrayList<Course> nlist = new ArrayList<Course>(count);

            Course filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getNombre().toLowerCase().contains(filterString)
                || filterableString.getDocente().toLowerCase().contains(filterString)
                ) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            courseList = (ArrayList<Course>) results.values;
            notifyDataSetChanged();
        }
    }
}
