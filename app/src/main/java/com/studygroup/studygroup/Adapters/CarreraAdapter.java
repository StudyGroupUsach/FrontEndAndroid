package com.studygroup.studygroup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.studygroup.studygroup.Poco.Carrera;
import com.studygroup.studygroup.Poco.Ramo;
import com.studygroup.studygroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 1/2/15.
 */
public class CarreraAdapter extends ArrayAdapter<Carrera> {

    Context context;
    int resource, textViewResourceId;
    List<Carrera> items, tempItems, suggestions;

    public CarreraAdapter(Context context, int resource, int textViewResourceId, List<Carrera> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Carrera>(items); // this makes the difference.
        suggestions = new ArrayList<Carrera>();
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
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
            view = inflater.inflate(R.layout.list_view_row, parent, false);
        }
        TextView main_text = (TextView) view.findViewById(R.id.lbl_name);
        main_text.setText(items.get(position).getName());

        return view;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Carrera) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Carrera carrera : tempItems) {
                    if (carrera.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(carrera);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Carrera> filterList = (ArrayList<Carrera>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Carrera carrera : filterList) {
                    add(carrera);
                    notifyDataSetChanged();
                }
            }
        }
    };
}