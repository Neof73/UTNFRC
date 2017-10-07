package ar.edu.frc.utn.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mario Di Giorgio on 20/06/2017.
 */

public class CourseAdapterExpandable extends BaseExpandableListAdapter {
        private Context _context;
        private ArrayList<Course> _listDataHeader;

        public CourseAdapterExpandable(Context context, ArrayList<Course> listDataHeader) {
            this._context = context;
            this._listDataHeader = listDataHeader;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataHeader.get(groupPosition).clase.get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final Course groupText = this._listDataHeader.get(groupPosition);
            final Clase childText = groupText.clase.get(childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtClases = (TextView) convertView.findViewById(R.id.clases);
            if (!childText.getClases().equals("")) {
                if (!childText.getClases().equals("0"))
                    txtClases.setText(_context.getString(R.string.cronoUnidad) + ": " + childText.getClases());
                else
                    txtClases.setText("");
                txtClases.setVisibility(View.VISIBLE);
            } else {
                txtClases.setVisibility(View.GONE);
            }
            TextView txtListChild = (TextView) convertView.findViewById(R.id.presentacion);
            txtListChild.setText(childText.getPresentacion());

            TextView txtFecha = (TextView) convertView.findViewById(R.id.fecha);
            txtFecha.setText(childText.getFecha());

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataHeader.get(groupPosition).clase.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Course headerTitle = (Course) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            String headerTitleString = headerTitle.getNombre();
            if (!headerTitleString.equals("")) {
                lblListHeader.setTextSize(17);
                lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorTitle));
                lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.colorDisabled));
            } else {
                headerTitleString = headerTitle.getAnio();
                lblListHeader.setTextSize(32);
                lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
                lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.color));
            }
            lblListHeader.setText(headerTitleString);

            TextView lblDocente = (TextView) convertView
                    .findViewById(R.id.docente);

            String strDocente = headerTitle.getDocente();
            //if (strDocente.equals(""))
            //    strDocente = headerTitle.getFecha();
            lblDocente.setText(strDocente);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

