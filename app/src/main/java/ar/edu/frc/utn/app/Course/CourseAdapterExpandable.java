package ar.edu.frc.utn.app.Course;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.frc.utn.app.Fragments.MoreOptions;
import ar.edu.frc.utn.app.Fragments.MyItemRecyclerViewAdapter;
import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 20/06/2017.
 */

public class CourseAdapterExpandable extends BaseExpandableListAdapter implements Filterable {
    private Context _context;
    private ArrayList<Course> originalList;
    private ArrayList<Course> _listDataHeader;
    private ItemFilter mFilter = new ItemFilter();

    public CourseAdapterExpandable(Context context, ArrayList<Course> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.originalList = listDataHeader;
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

        ViewHolderItem holderItem;
        final Course groupText = this._listDataHeader.get(groupPosition);
        final Clase childText = groupText.clase.get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.crono_list_item, null);
            holderItem = new ViewHolderItem(convertView);
        } else {
            holderItem = (ViewHolderItem) convertView.getTag();
        }

        if (!childText.getClases().equals("")) {
            if (!childText.getClases().equals("0"))
                holderItem.txtClases.setText(_context.getString(R.string.cronoUnidad) + ": " + childText.getClases());
            else
                holderItem.txtClases.setText("");
            holderItem.txtClases.setVisibility(View.VISIBLE);
        } else {
            holderItem.txtClases.setVisibility(View.GONE);
        }
        holderItem.txtListChild.setText(childText.getPresentacion());
        holderItem.txtFecha.setText(childText.getFecha());

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

        ViewHolderCourse holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.crono_list_group, null);
            holder = new ViewHolderCourse(convertView);
        } else {
            holder = (ViewHolderCourse) convertView.getTag();
        }

        holder.lblListHeader.setTypeface(null, Typeface.BOLD);
        String headerTitleString = headerTitle.getNombre();
        if (!headerTitleString.equals("")) {
            holder.lblListHeader.setTextSize(17);
            holder.lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorNavText));
            holder.lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.colorPrimary));
        } else {
            headerTitleString = headerTitle.getAnio();
            holder.lblListHeader.setTextSize(32);
            holder.lblListHeader.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
            holder.lblListHeader.setBackgroundColor(convertView.getResources().getColor(R.color.colorDisabled));
        }
        holder.lblListHeader.setText(headerTitleString);


        String strDocente = headerTitle.getDocente();
        holder.lblDocente.setText(strDocente);
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolderCourse extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView lblListHeader;
        public TextView lblDocente;

        public ViewHolderCourse(View view) {
            super(view);
            mView = view;
            lblListHeader = view.findViewById(R.id.lblListHeader);
            lblDocente = view.findViewById(R.id.docente);
            view.setTag(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + lblListHeader.getText() + "'";
        }
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView txtClases;
        public TextView txtListChild;
        public TextView txtFecha;

        public ViewHolderItem(View view) {
            super(view);
            mView = view;
            txtClases = view.findViewById(R.id.clases);
            txtListChild = view.findViewById(R.id.presentacion);
            txtFecha = view.findViewById(R.id.fecha);
            view.setTag(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtClases.getText() + "'";
        }
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Course> list = originalList;

            int count = list.size();
            final ArrayList<Course> nlist = new ArrayList<Course>(count);

            Course filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getNombre().toLowerCase().contains(filterString)
                        || filterableString.getDocente().toLowerCase().contains(filterString)
                ) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _listDataHeader = (ArrayList<Course>) results.values;
            notifyDataSetChanged();
        }
    }
}

