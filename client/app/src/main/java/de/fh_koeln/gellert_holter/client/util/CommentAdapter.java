package de.fh_koeln.gellert_holter.client.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.fh_koeln.gellert_holter.client.R;

/**
 * Notwendiger Adapter zum DataBinding vom implementierten Typ "Comment" an ListView
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    int resource;

    public CommentAdapter(Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout commentView;
        Comment c = getItem(position);

        if (convertView == null) {
            commentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, commentView, true);
        } else {
            commentView = (LinearLayout) convertView;
        }

        TextView commentAuthor = (TextView) commentView.findViewById(R.id.commentAuthor);
        TextView commentDate = (TextView) commentView.findViewById(R.id.commentDate);
        TextView commentBody = (TextView) commentView.findViewById(R.id.commentBody);

        commentAuthor.setText(c.getAuthor());
        commentDate.setText(c.getDate());
        commentBody.setText(c.getBody());

        return commentView;
    }
}