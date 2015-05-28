package de.fh_koeln.gellert_holter.parents.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import de.fh_koeln.gellert_holter.parents.R;
import util.*;
import util.Thread;

public class ShowThread extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thread);

        Intent i = getIntent();

        List<Thread> thread = i.getParcelableArrayListExtra("thread");

        TextView author = (TextView) findViewById(R.id.author);
        author.setText(thread.get(0).getAuthor());
        TextView body = (TextView) findViewById(R.id.body);
        body.setText(thread.get(0).getBody());
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(thread.get(0).getDate());
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
