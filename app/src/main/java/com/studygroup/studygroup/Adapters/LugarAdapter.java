package com.studygroup.studygroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.Carrera;
import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllob on 15-06-2016.
 */
public class LugarAdapter extends ArrayAdapter<Lugar> {

    Context context;
    int resource, textViewResourceId;
    List<Lugar> items, tempItems, suggestions;

    public LugarAdapter(Context context, int resource, int textViewResourceId, List<Lugar> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Lugar>(items); // this makes the difference.
        suggestions = new ArrayList<Lugar>();
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
        main_text.setText(items.get(position).getIdLugar());

        return view;
    }

}
