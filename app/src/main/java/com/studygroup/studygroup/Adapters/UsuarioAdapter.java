package com.studygroup.studygroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.Usuario;
import com.studygroup.studygroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllob on 02-07-2016.
 */
public class UsuarioAdapter extends ArrayAdapter<Usuario>{


    Context context;
    int resource, textViewResourceId;
    List<Usuario> items, tempItems, suggestions;

    public UsuarioAdapter(Context context, int resource, int textViewResourceId, List<Usuario> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Usuario>(items); // this makes the difference.
        suggestions = new ArrayList<Usuario>();
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
        main_text.setText(items.get(position).getUsuarioId());

        return view;
    }

}
