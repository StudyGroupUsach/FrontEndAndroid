package com.studygroup.studygroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.Lugar;
import com.studygroup.studygroup.Poco.PerfilAyudante;
import com.studygroup.studygroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllob on 03-07-2016.
 */
public class PerfilAyudanteAdapter extends ArrayAdapter<PerfilAyudante>{

    Context context;
    int resource, textViewResourceId;
    List<PerfilAyudante> items, tempItems, suggestions;

    public PerfilAyudanteAdapter(Context context, int resource, int textViewResourceId, List<PerfilAyudante> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<PerfilAyudante>(items);
        suggestions = new ArrayList<PerfilAyudante>();
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
        main_text.setText(items.get(position).getAyudanteId());

        return view;
    }

}
