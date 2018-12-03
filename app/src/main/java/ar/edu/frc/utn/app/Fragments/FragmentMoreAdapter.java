package ar.edu.frc.utn.app.Fragments;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.frc.utn.app.Main2Activity;
import ar.edu.frc.utn.app.R;

public class FragmentMoreAdapter extends BaseAdapter {
    private List<MoreOptions.MoreItem> list;
    private Context context;

    public FragmentMoreAdapter(Context context, List<MoreOptions.MoreItem> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
       return list.get(position).ItemId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.fragment_more_item, null);
        final TextView button = (TextView) convertView.findViewById(R.id.btnFragmentMoreItem);
        button.setText(list.get(position).ButtonCaption);
        button.setTag(list.get(position).FragmentTag);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity)context).openFragment(button);
            }
        });*/
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageItem);
        imageView.setImageResource((int)getItemId(position));

        TextView textView = (TextView) convertView.findViewById(R.id.descriptionItem);
        textView.setText(list.get(position).ItemDescription);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity)context).openFragment(button);
            }
        });
        return convertView;
    }
}
