package ar.edu.frc.utn.app.Course;

import android.provider.BaseColumns;

public class ScriptDatabase {
    /*
    Etiqueta para Depuración
     */
    private static final String TAG = ScriptDatabase.class.getSimpleName();

    // Metainformación de la base de datos
    public static final String ENTRADA_TABLE_NAME = "entrada";
    public static final String STRING_TYPE = "TEXT";
    public static final String DATE_TYPE = "DATETIME";
    public static final String INT_TYPE = "INTEGER";

    // Campos de la tabla entrada
    public static class ColumnEntradas {
        public static final String ID = BaseColumns._ID;
        public static final String TITULO = "titulo";
        public static final String DESCRIPCION = "descripcion";
        public static final String URL = "url";
        public static final String PUBDATE = "pub_date";
        public static final String GUID = "guid";

    }

    // Comando CREATE para la tabla ENTRADA
    public static final String CREAR_ENTRADA =
            "CREATE TABLE " + ENTRADA_TABLE_NAME + "(" +
                    ColumnEntradas.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnEntradas.TITULO + " " + STRING_TYPE + " not null," +
                    ColumnEntradas.DESCRIPCION + " " + STRING_TYPE + "," +
                    ColumnEntradas.URL + " " + STRING_TYPE + "," +
                    ColumnEntradas.PUBDATE + " " + DATE_TYPE + "," +
                    ColumnEntradas.GUID + " " + STRING_TYPE +")";
}
