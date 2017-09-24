package ar.edu.frc.utn.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.DocumentsContract;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class Intitucional extends AsyncTask<String, Void, String> {
    AsyncResultXML callback;
    ProgressDialog progress;

    public Intitucional(AsyncResultXML callback, ProgressDialog progressDialog) {
        this.callback = callback;
        this.progress = progressDialog;
    }

    @Override
    protected void onPreExecute(){
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setMessage("Cargando datos...");
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
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                org.jsoup.nodes.Document document = Jsoup.parse(result, "ISO-8859-1");
                Elements content = document.getElementsByClass("innertube");
                if (content != null && content.size() > 0) {
                    callback.onResult(content.first().children().toArray()[0].toString());
                } else {
                    callback.onResult(document.toString());
                }
            } catch (Exception e) {
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "ISO-8859-1"));
            String contentAsString = "";
            String line = reader.readLine();
            while (line != null){
                contentAsString += line;
                line = reader.readLine();
            }
            reader.close();
            return contentAsString;
        } finally {
            is.close();
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