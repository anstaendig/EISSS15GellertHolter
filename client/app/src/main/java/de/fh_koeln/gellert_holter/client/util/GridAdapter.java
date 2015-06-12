package de.fh_koeln.gellert_holter.client.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fh_koeln.gellert_holter.client.R;

public class GridAdapter extends ArrayAdapter<Product> {
    private final List<Product> list;
    private final Activity context;

    public GridAdapter(Activity context, List<Product> list) {
        super(context, R.layout.product, list);
        this.context = context;
        this.list = list;
    }

    public class ViewHolder {
        protected TextView tv;
        //protexted ImageView iv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.product, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) view.findViewById(R.id.textView1);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tv.setText(list.get(position).getDescription());
        return view;
    }

}
