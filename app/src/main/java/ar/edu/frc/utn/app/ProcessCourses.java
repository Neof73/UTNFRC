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

public class ProcessCourses {
    ArrayList<Course> listDataHeader;
    EditText filter;
    ArrayList<Course> courseList = new ArrayList<>();
    ExpandableListView listview;
    Context context;

    public ProcessCourses(Context context){
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
        courseList = list;
        prepareListData(list);
    }

    private void prepareListData(ArrayList<Course> courseList) {

        Collections.sort(courseList, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return (o1.getAnio() + o1.getNombre()).compareTo((o2.getAnio() + o2.getNombre()));
            }
        });


        listDataHeader = new ArrayList<Course>();
        for (Course course : courseList) {
            if (course.getNombre().toLowerCase().contains(filter.getText().toString().toLowerCase()) ||
                    course.getDocente().toLowerCase().contains(filter.getText().toString().toLowerCase())) {
                listDataHeader.add(course);
            } else if (course.getNombre().equals("") && !course.getAnio().equals("")) {
                listDataHeader.add(course);
            }
        }

        final CourseAdapterExpandable adapter = new CourseAdapterExpandable(context, listDataHeader);
        listview.setAdapter(adapter);

    }

}
