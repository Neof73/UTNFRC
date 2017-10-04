package ar.edu.frc.utn.app;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;

/**
 * Created by Mario Di Giorgio on 03/10/2017.
 */

public interface AsyncResultList {
    void onResult(ArrayList<Course> list);
}