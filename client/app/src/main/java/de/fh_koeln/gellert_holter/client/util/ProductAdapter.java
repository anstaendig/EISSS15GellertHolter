package de.fh_koeln.gellert_holter.client.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import de.fh_koeln.gellert_holter.client.R;


/**
 * Notwendiger Adapter zum DataBinding vom implementierten Typ "Product" an ListView
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    private final List<Product> list;
    private final Activity context;
    public HashMap<Integer, String> myItems = new HashMap<>();

    public ProductAdapter(Activity context, List<Product> list) {
        super(context, R.layout.rowbuttonlayout, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected EditText weight;
    }

    public String getProduct(int position) {
        return myItems.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.rowbuttonlayout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.description);
            viewHolder.weight = (EditText) view.findViewById(R.id.weight);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getDescription());
        holder.weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final EditText Text = (EditText) v;
                    myItems.put(position, Text.getText().toString());
                }
            }
        });
        return view;
    }
}

