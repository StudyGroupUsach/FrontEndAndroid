package com.studygroup.studygroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllob on 24-06-2016.
 */
public class RamoAdapter extends ArrayAdapter<Ramo> {

    Context context;
    int resource, textViewResourceId;
    List<Ramo> items, tempItems, suggestions;

    public RamoAdapter(Context context, int resource, int textViewResourceId, List<Ramo> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Ramo>(items); // this makes the difference.
        suggestions = new ArrayList<Ramo>();
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.content_main, parent, false);
        }
        TextView main_text = (TextView) view.findViewById(R.id.list_lugar);
        main_text.setText(items.get(position).getRamoId());

        return view;
    }
}
