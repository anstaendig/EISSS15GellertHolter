package de.fh_koeln.gellert_holter.client.activities.parents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.Comment;
import de.fh_koeln.gellert_holter.client.util.CommentAdapter;
import de.fh_koeln.gellert_holter.client.util.Thread;

public class ShowThread extends Activity {
    ArrayList<Comment> commentList;
    CommentAdapter ca;
    Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thread);
        ListView lv = (ListView) findViewById(R.id.commentListView);
        commentList = new ArrayList<>();
        ca = new CommentAdapter(ShowThread.this, R.layout.comment, commentList);
        lv.setAdapter(ca);
        Intent intent = getIntent();
        thread = intent.getParcelableExtra("thread");
        TextView author = (TextView) findViewById(R.id.author);
        TextView date = (TextView) findViewById(R.id.date);
        TextView body = (TextView) findViewById(R.id.body);
        //TextView topics = (TextView) findViewById(R.id.topics);

        author.setText(thread.getAuthor());
        date.setText(thread.getDate());
        body.setText(thread.getBody());
        //topics.setText(thread.getTopics().toString());
        //comments.setText(thread.getComments().toString());
        getComments();
    }

    public void getComments() {
        Log.e("Length of Comments", thread.comments.size() + "");
        for (int i = 0; i < thread.comments.size(); i++) {
            commentList.add(thread.comments.get(i));
        }
        ca.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
