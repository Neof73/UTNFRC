package ar.edu.frc.utn.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mario Di Giorgio on 12/08/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Creado por Mario Di Giorgio.
 *
 * Clase que administra el acceso y operaciones hacia la base de datos
 */

public final class FeedSQLDatabase extends SQLiteOpenHelper {

    // Mapeado rápido de indices
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_TITULO = 1;
    private static final int COLUMN_DESC = 2;
    private static final int COLUMN_URL = 3;
    private static final int COLUMN_PUBDATE = 4;
    private static final int COLUMN_GUID = 5;

    /*
    Instancia singleton
    */
    private static FeedSQLDatabase singleton;

    /*
    Etiqueta de depuración
     */
    private static final String TAG = FeedSQLDatabase.class.getSimpleName();


    /*
    Nombre de la base de datos
     */
    public static final String DATABASE_NAME = "Noticias.db";

    /*
    Versión actual de la base de datos
     */
    public static final int DATABASE_VERSION = 7;


    public FeedSQLDatabase(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);

    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized FeedSQLDatabase getInstance(Context context) {
        if (singleton == null) {
            singleton = new FeedSQLDatabase(context.getApplicationContext());
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla 'entrada'
        db.execSQL(ScriptDatabase.CREAR_ENTRADA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Añade los cambios que se realizarán en el esquema
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.ENTRADA_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Obtiene todos los registros de la tabla entrada
     *
     * @return cursor con los registros
     */
    public Cursor obtenerEntradas() {
        // Seleccionamos todas las filas de la tabla 'entrada'
        String consulta = "SELECT " +
                ScriptDatabase.ColumnEntradas.ID + ", " +
                ScriptDatabase.ColumnEntradas.TITULO + ", " +
                ScriptDatabase.ColumnEntradas.DESCRIPCION + ", "+
                ScriptDatabase.ColumnEntradas.URL + ", "+
                //"strftime('%d/%m/%Y', "+ ScriptDatabase.ColumnEntradas.PUBDATE +") AS " +
                ScriptDatabase.ColumnEntradas.PUBDATE + ", " +
                ScriptDatabase.ColumnEntradas.GUID +
                " FROM " + ScriptDatabase.ENTRADA_TABLE_NAME +
                " ORDER BY " + ScriptDatabase.ColumnEntradas.PUBDATE + " DESC";
        return getWritableDatabase().rawQuery(
                consulta,
                null);
    }

    /**
     * Inserta un registro en la tabla entrada
     *
     * @param titulo      titulo de la entrada
     * @param descripcion desripcion de la entrada
     * @param url         url del articulo
     * @param pubDate     fecha de la publicacion
     * @param guid        identificador de la noticia
     */
    public void insertarEntrada(
            String titulo,
            String descripcion,
            String url,
            String pubDate,
            String guid) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntradas.TITULO, titulo);
        values.put(ScriptDatabase.ColumnEntradas.DESCRIPCION, descripcion);
        values.put(ScriptDatabase.ColumnEntradas.URL, url);
        values.put(ScriptDatabase.ColumnEntradas.PUBDATE, pubDate);
        values.put(ScriptDatabase.ColumnEntradas.GUID, guid);

        // Insertando el registro en la base de datos
        getWritableDatabase().insert(
                ScriptDatabase.ENTRADA_TABLE_NAME,
                null,
                values
        );
    }

    /**
     * Modifica los valores de las columnas de una entrada
     *
     * @param id          identificador de la entrada
     * @param titulo      titulo nuevo de la entrada
     * @param descripcion descripcion nueva para la entrada
     * @param url         url nueva para la entrada
     * @param pubDate     fecha de publicacion
     * @param guid        guid de identificacion.
     */
    public void actualizarEntrada(int id,
                                  String titulo,
                                  String descripcion,
                                  String url,
                                  String pubDate,
                                  String guid) throws ParseException {

        /*DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        Date dPubDate =  df.parse(pubDate);
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String sPubDate = df2.format(dPubDate);*/
        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntradas.TITULO, titulo);
        values.put(ScriptDatabase.ColumnEntradas.DESCRIPCION, descripcion);
        values.put(ScriptDatabase.ColumnEntradas.URL, url);
        values.put(ScriptDatabase.ColumnEntradas.PUBDATE, pubDate);
        values.put(ScriptDatabase.ColumnEntradas.GUID, guid);

        // Modificar entrada
        getWritableDatabase().update(
                ScriptDatabase.ENTRADA_TABLE_NAME,
                values,
                ScriptDatabase.ColumnEntradas.ID + "=?",
                new String[]{String.valueOf(id)});

    }

    /**
     * Procesa una lista de items para su almacenamiento local
     * y sincronización.
     *
     * @param entries lista de items
     */
    public void sincronizarEntradas(List<Item> entries) throws ParseException {
    /*
    #1  Mapear temporalemente las entradas nuevas para realizar una
        comparación con las locales
    */
        HashMap<String, Item> entryMap = new HashMap<String, Item>();
        for (Item e : entries) {
            entryMap.put(e.getTitle(), e);
        }

    /*
    #2  Obtener las entradas locales
     */
        Log.i(TAG, "Consultar items actualmente almacenados");
        Cursor c = obtenerEntradas();
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " entradas, computando...");

    /*
    #3  Comenzar a comparar las entradas
     */
        int id;
        String titulo;
        String descripcion;
        String url;
        String pubdate;
        String guid;

        while (c.moveToNext()) {

            id = c.getInt(COLUMN_ID);
            titulo = c.getString(COLUMN_TITULO);
            descripcion = c.getString(COLUMN_DESC);
            pubdate = c.getString(COLUMN_PUBDATE);
            url = c.getString(COLUMN_URL);
            guid = c.getString(COLUMN_GUID);

            Item match = entryMap.get(titulo);
            if (match != null) {
                // Filtrar entradas existentes. Remover para prevenir futura inserción
                entryMap.remove(titulo);

            /*
            #3.1 Comprobar si la entrada necesita ser actualizada
            */
                if ((match.getTitle() != null && !match.getTitle().equals(titulo)) ||
                        (match.getGuid()) != null && match.getGuid().equals(guid)) {
                    // Actualizar entradas
                    actualizarEntrada(
                            id,
                            match.getTitle(),
                            match.getDescripcion(),
                            match.getLink(),
                            match.getPubDate(),
                            match.getGuid()
                    );

                }
            }
        }
        c.close();

    /*
    #4 Añadir entradas nuevas
    */
        for (Item e : entryMap.values()) {
            Log.i(TAG, "Insertado: titulo=" + e.getTitle());
            insertarEntrada(
                    e.getTitle(),
                    e.getDescripcion(),
                    e.getLink(),
                    e.getPubDate(),
                    e.getGuid()
            );
        }
        Log.i(TAG, "Se actualizaron los registros");
    }

}