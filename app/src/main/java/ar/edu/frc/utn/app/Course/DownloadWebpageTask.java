package ar.edu.frc.utn.app.Course;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.frc.utn.app.Interfaces.AsyncResult;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */
public class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    AsyncResult callback;
    ProgressDialog progress;

    public DownloadWebpageTask(AsyncResult callback, ProgressDialog progressDialog) {
        this.callback = callback;
        this.progress = progressDialog;
    }

    @Override
    protected void onPreExecute(){
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setMessage("Cargando cronogramas de cursos...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (Exception e) {
            return "Error: No se pudo descargar los datos.";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        // remove the unnecessary parts from the response and construct a JSON
        if (result.startsWith("Error:")){
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMessage(result);
            progress.setCancelable(true);
        } else {
            int start = result.indexOf("{", result.indexOf("{") + 1);
            int end = result.lastIndexOf("}");
            String jsonResponse = result.substring(start, end);
            try {
                JSONObject table = new JSONObject(jsonResponse);
                callback.onResult(table);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progress.dismiss();
        }
    }
    private String downloadUrl(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
            String contentAsString = convertStreamToString(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
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
}