package com.urgence.adapter;
/**
 * Created by SooheibSelmi
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.urgence.R;
import com.urgence.pojo.Salle;

import java.util.List;

public class ListSalleAdapter extends ArrayAdapter<Salle> {

    public ListSalleAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListSalleAdapter(Context context, int resource, List<Salle> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_salle, null);
        }

        Salle p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.description);


            if (tt1 != null) {
                tt1.setText(p.getNomsalle());
            }
            if (tt2 != null) {
                tt2.setText(p.getDescriptionsalle());
            }

        }

        return v;
    }

}