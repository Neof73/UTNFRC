package ar.edu.frc.utn.app.Interfaces;

import java.util.ArrayList;

import ar.edu.frc.utn.app.Course.Course;

/**
 * Created by Mario Di Giorgio on 03/10/2017.
 */

public interface AsyncResultList {
    void onResult(ArrayList<Course> list);
}