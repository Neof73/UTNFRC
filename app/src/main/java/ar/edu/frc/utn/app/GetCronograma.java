package ar.edu.frc.utn.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.select.Collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mario Di Giorgio on 03/10/2017.
 */

public class GetCronograma extends AsyncTask<String, ProgressDialog, ArrayList<Course>> {
    String urlLogin;
    String urlCronograma;
    String loginData;
    AsyncResultList callback;
    Context context;
    //ProgressDialog progress;
    SwipeRefreshLayout swipe;
    ArrayList<Course> CourseList;

    public GetCronograma(AsyncResultList callback, Context context, /*ProgressDialog progressDialog*/SwipeRefreshLayout swipe) {
        this.callback = callback;
        this.context = context;
        //this.progress = progressDialog;
        this.swipe = swipe;
    }

    @Override
    protected void onPreExecute(){
        //progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progress.setCancelable(false);
        //progress.setMessage(context.getString(R.string.cronoLoading));
        //progress.show();
        swipe.setRefreshing(true);
        urlLogin = context.getString(R.string.cronoLoginUrl);
        urlCronograma = context.getString(R.string.cronoResourceUrl);
        loginData = context.getString(R.string.cronoData);
        CourseList = new ArrayList<Course>();
    }

    @Override
    protected ArrayList<Course> doInBackground(String... params) {
        try {

            CookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

            OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieJar).build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, loginData);
            Request request = new Request.Builder()
                    .url(urlLogin)
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() == 200) {
                Request request2 = new Request.Builder()
                        .url(urlCronograma)
                        .get()
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .build();

                Response response2 = client.newCall(request2).execute();
                parseExcel(response2.body().byteStream());
            }
            return CourseList;
        } catch (Exception e) {
            e.printStackTrace();
            return CourseList;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Course> result) {
        try {
            callback.onResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //progress.dismiss();
        swipe.setRefreshing(false);
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void parseExcel(InputStream fis){

        try{

            // Create a workbook using the Input Stream
            HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells
            Iterator<Row> rowIter = mySheet.rowIterator();
            Integer id = 0;
            while(rowIter.hasNext()){

                HSSFRow myRow = (HSSFRow) rowIter.next();
                // Skip the first 3 rows
                if(myRow.getRowNum() < 3) {
                    continue;
                }

                String fecha = "";
                Integer anio = 0;
                String tipo = "";
                String nombre = "";
                String clases = "";
                String presentacion = "";
                String docente = "";
                String email = "";
                Iterator<Cell> cellIter = myRow.cellIterator();
                while(cellIter.hasNext()){

                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    String cellValue = "";

                    // Check for cell Type
                    if(myCell.getCellType() == HSSFCell.CELL_TYPE_STRING){
                        cellValue = myCell.getStringCellValue();
                    } else if(myCell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                        cellValue = myCell.getStringCellValue();
                    }
                    else {
                        cellValue = String.valueOf(myCell.getNumericCellValue());
                    }

                    // Push the parsed data in the Java Object
                    // Check for cell index
                    switch (myCell.getColumnIndex()) {
                        case 0:
                            fecha = cellValue;
                            break;
                        case 1:
                            if (cellValue==""){
                                cellValue = "0";
                            }
                            anio = (int)(Double.parseDouble(cellValue));
                            break;
                        case 2:
                            tipo = cellValue;
                            break;
                        case 3:
                            nombre = cellValue;
                            break;
                        case 4:
                            if (cellValue==""){
                                cellValue = "0";
                            }
                            Integer cl = (int)(Double.parseDouble(cellValue));
                            clases = cl.toString();
                            break;
                        case 5:
                            presentacion = cellValue;
                            break;
                        case 6:
                            docente = cellValue;
                            if (docente.equals(""))
                                docente = fecha;
                            break;
                        case 8:
                            email = cellValue;
                            break;
                        default:
                            break;
                    }

                }

                if (anio.intValue() > 0) {
                    boolean itemAnio = false;
                    for (Course item: CourseList) {
                        if (item.getAnio().equals(anio.toString())) {
                            itemAnio = true;
                            break;
                        }
                    }
                    if (!itemAnio) {
                        CourseList.add(new Course(anio.toString(), "", "", ""));
                    }

                    if (!nombre.equals("")) {
                        Course course = new Course(anio.toString(), nombre, docente, email);
                        Clase claseItem = new Clase((id++).toString(), fecha, tipo, clases, presentacion);
                        Course courseItem = null;
                        for (Course item : CourseList) {
                            if (item.getAnio().equals(anio.toString()) && item.getNombre().equals(nombre)) {
                                courseItem = item;
                                break;
                            }
                        }
                        //ArrayList<Course> items  = CourseList.stream().filter(p -> p.getNombre().equals(nombre)).collect(Collectors.<Course>toList());
                        if (courseItem != null) {
                            courseItem.clase.add(claseItem);
                        } else {
                            if (!presentacion.equals(""))
                                course.clase.add(claseItem);
                            CourseList.add(course);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
