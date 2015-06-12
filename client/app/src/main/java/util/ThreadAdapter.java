package util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.fh_koeln.gellert_holter.client.R;

public class ThreadAdapter extends ArrayAdapter<Thread> {

    int resource;

    public ThreadAdapter(Context context, int resource, List<Thread> threads) {
        super(context, resource, threads);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout threadView;
        Thread t = getItem(position);

        if (convertView == null) {
            threadView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, threadView, true);
        } else {
            threadView = (LinearLayout) convertView;
        }

        TextView author = (TextView) threadView.findViewById(R.id.author);
        TextView date = (TextView) threadView.findViewById(R.id.date);
        TextView body = (TextView) threadView.findViewById(R.id.body);
        TextView topics = (TextView) threadView.findViewById(R.id.topics);
        TextView comments = (TextView) threadView.findViewById(R.id.comments);

        author.setText(t.author);
        date.setText(t.date);
        body.setText(t.body);
        topics.setText(t.topics.get(0));
        comments.setText(t.comments.get(0).author);

        return threadView;
    }
}