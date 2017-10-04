package ar.edu.frc.utn.app;

import android.app.ListActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import static ar.edu.frc.utn.app.R.id.editText;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class ProcessJson {
    List<Anio> anioArray = new ArrayList<>();
    List<Curso> cursoArray = new ArrayList<>();
    List<Unidad> unidadArray = new ArrayList<>();
    List<Clase> clasesArray = new ArrayList<>();
    List<String> fechaArray = new ArrayList<>();

    List<Course> listDataHeader;
    HashMap<String, List<Course>> listDataChild;
    EditText filter;

    ArrayList<Course> courseArray = new ArrayList<Course>();
    List<Course> courseList = new ArrayList<>();
    ExpandableListView listview;
    Context context;

    public ProcessJson(Context context){
        this.context = context;
        this.listview = (ExpandableListView) ((MainActivity)context).findViewById(R.id.expListView) ;
        filter = (EditText) ((MainActivity)context).findViewById(R.id.search);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prepareListData(courseList);
            }
        });
    }

    public void processList(ArrayList<Course> list) {
        prepareListData(list);
        prepareListItemClick();
    }

    public void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("entry");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);

                String fecha = (new JSONObject(row.getString("gsx$fecha"))).getString("$t");
                String anio = (new JSONObject(row.getString("gsx$año"))).getString("$t");
                String tipo = (new JSONObject(row.getString("gsx$tipo"))).getString("$t");
                String nombre = (new JSONObject(row.getString("gsx$nombre"))).getString("$t");
                String clases = (new JSONObject(row.getString("gsx$clases"))).getString("$t");
                String presentacion = (new JSONObject(row.getString("gsx$presentación"))).getString("$t");
                String docente = (new JSONObject(row.getString("gsx$docente"))).getString("$t");
                String email = (new JSONObject(row.getString("gsx$email"))).getString("$t");

                Course course = new Course("", fecha, anio, tipo, nombre, clases, presentacion, docente, email);
                courseList.add(course);
            }

            prepareListData(courseList);
            prepareListItemClick();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void prepareListItemClick() {
        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(
                        v.getContext(),
                        listDataHeader.get(groupPosition).getNombre()
                                + " : "
                                +listDataChild.get(
                                listDataHeader.get(groupPosition).getNombre()).get(
                                childPosition).getPresentacion(), Toast.LENGTH_SHORT)
                        .show();

                return false;
            }
        });

    }

    private void prepareListData(List<Course> courseList) {

        Collections.sort(courseList, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return (o1.getAnio() + o1.getNombre()).compareTo((o2.getAnio() + o2.getNombre()));
            }
        });

        listDataHeader = new ArrayList<Course>();
        listDataChild = new HashMap<String, List<Course>>();

        Integer headerID = 0;
        String anio = "";
        String name = "";
        //String pres = "";
        String unid = "";
        List<Course> dataChild = null;
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            if (!course.getAnio().equals(anio)) {
                anio = course.getAnio();
                name = "";
                unid = "";
                Course couAnio = new Course(anio, "", anio, "", "", "", "", "", "");
                listDataHeader.add(couAnio);
                dataChild = new ArrayList<Course>();
                listDataChild.put(name, dataChild);
            }

            if (course.getNombre().toLowerCase().contains(filter.getText().toString().toLowerCase()) ||
                    course.getDocente().toLowerCase().contains(filter.getText().toString().toLowerCase())) {


                if (!course.getNombre().equals(name)) {
                    headerID++;
                    name = course.getNombre();
                    unid = course.getClases();
                    Course couName = new Course(
                            anio + "_" + headerID,
                            course.getFecha(),
                            "", //course.getAnio(),
                            course.getTipo(),
                            course.getNombre(),
                            course.getClases(),
                            course.getPresentacion(),
                            course.getDocente(),
                            course.getEmail());
                    listDataHeader.add(couName);
                    dataChild = new ArrayList<Course>();
                    listDataChild.put(anio + "_" + headerID, dataChild);
                    if (!course.getPresentacion().equals(""))
                        dataChild.add(course);
                } else {
                    if (course.getClases().equals(unid)) {
                        course.setClases("");
                    } else {
                        unid = course.getClases();
                    }
                    if (!course.getPresentacion().equals("") && dataChild != null)
                        dataChild.add(course);
                }
            }
        }

        final CourseAdapterExpandable adapter = new CourseAdapterExpandable(context, listDataHeader, listDataChild);
        listview.setAdapter(adapter);

    }
}
