package ar.edu.frc.utn.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mario Di Giorgio on 20/06/2017.
 */

public class CourseAdapterExpandable extends BaseExpandableListAdapter {
        private Context _context;
        private List<Course> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<Course>> _listDataChild;

        public CourseAdapterExpandable(Context context, List<Course> listDataHeader,
                                     HashMap<String, List<Course>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition).getId())
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {

            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final Course childText = (Course) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtClases = (TextView) convertView.findViewById(R.id.clases);
            if (!childText.getClases().equals("")) {
                txtClases.setText("Unidad: " + childText.getClases());
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
            return this._listDataChild.get(this._listDataHeader.get(groupPosition).getId())
                    .size();
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
            String headerTitleString = headerTitle.getAnio();
            if (headerTitleString.equals("")) {
                headerTitleString = headerTitle.getNombre();
                lblListHeader.setTextSize(17);
                lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorTitle));
                lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.colorDisabled));
            } else {
                lblListHeader.setTextSize(32);
                lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
                lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.color));
            }
            lblListHeader.setText(headerTitleString);

            TextView lblDocente = (TextView) convertView
                    .findViewById(R.id.docente);

            String strDocente = headerTitle.getDocente();
            if (strDocente.equals(""))
                strDocente = headerTitle.getFecha();
            lblDocente.setText(strDocente);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

