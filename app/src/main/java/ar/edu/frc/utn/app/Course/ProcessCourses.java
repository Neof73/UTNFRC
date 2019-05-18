package ar.edu.frc.utn.app.Course;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ar.edu.frc.utn.app.Course.Course;
import ar.edu.frc.utn.app.Course.CourseAdapterExpandable;
import ar.edu.frc.utn.app.Main2Activity;
import ar.edu.frc.utn.app.MainActivity;
import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class ProcessCourses {
    ArrayList<Course> listDataHeader;
    EditText filter;
    ArrayList<Course> courseList = new ArrayList<>();
    ExpandableListView listview;
    Context context;
    private CourseAdapterExpandable adapter;

    public ProcessCourses(Context context){
        this.context = context;
        this.listview = (ExpandableListView) ((Main2Activity)context).findViewById(R.id.expListView) ;
        filter = (EditText) ((Main2Activity)context).findViewById(R.id.search);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //prepareListData(courseList);
                adapter.getFilter().filter(s.toString());
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

        adapter = new CourseAdapterExpandable(context, listDataHeader);
        listview.setAdapter(adapter);

    }

}
