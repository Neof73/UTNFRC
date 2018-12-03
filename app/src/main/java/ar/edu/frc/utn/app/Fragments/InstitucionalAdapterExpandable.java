package ar.edu.frc.utn.app.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import ar.edu.frc.utn.app.Interfaces.AsyncResultXML;
import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 26/08/2017.
 */

public class InstitucionalAdapterExpandable extends BaseExpandableListAdapter{
    Context context;
    String[] institucional_group;
    String[] currentResponse;

    public InstitucionalAdapterExpandable(Context context){
        this.context = context;
        institucional_group = this.context.getResources().getStringArray(
                R.array.institucional_group);
        currentResponse = new String[institucional_group.length];
    }

    @Override
    public int getGroupCount() {
        return institucional_group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return institucional_group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.institucional_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.institucional_area);
        textView.setText( institucional_group[groupPosition].split(";")[0]);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.institucional_item, null);
        }

        final String URL = institucional_group[groupPosition].split(";")[1];
        final WebView webView = (WebView) convertView.findViewById(R.id.institucional_webview);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        final String header = "<style>\n" +
                ".titMnuSec {\n" +
                "    font-family: Arial, Helvetica, sans-serif;\n" +
                "    font-size: 16px;\n" +
                "    color: #07519A;\n" +
                "    font-weight: bold;\n" +
                "    text-transform: uppercase;\n" +
                "    line-height: normal;\n" +
                "    display: block;\n" +
                "    text-decoration: none;\n" +
                "}\n" +
                ".contorno {\n" +
                    "border: 1px solid #AACCEE; \n" +
                    "background-color: #FFFFFF; \n" +
                    "clear: both; \n" +
                    "color: #07519A; \n" +
                    "font-family: Arial, Helvetica, sans-serif; \n" +
                    "font-size: 11px; \n" +
                    "margin-top: 4px; \n" +
                "}\n" +
                ".titPpal { \n" +
                    "font-family: Arial, Helvetica, sans-serif; \n" +
                    "font-size: 16px; \n" +
                    "color: #238EB0; \n" +
                    "font-weight: bold; \n" +
                "}\n" +
                ".icoItem {\n" +
                "    font-family: Arial, Helvetica, sans-serif; \n" +
                "    color: #878787; \n" +
                "    font-size: 14px; \n" +
                "    font-weight: bold; \n" +
                "}\n" +
                "</style>\n";
        webView.clearView();

        if (currentResponse[groupPosition] == null) {
            new Intitucional(new AsyncResultXML() {
                @Override
                public void onResult(String response) {
                    currentResponse[groupPosition] = header + response;
                    webView.loadDataWithBaseURL("", currentResponse[groupPosition], "text/html", "ISO-8859-1", "");
                }
            }, progressDialog).execute(URL);
        } else {
            webView.loadDataWithBaseURL("", currentResponse[groupPosition], "text/html", "ISO-8859-1", "");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
