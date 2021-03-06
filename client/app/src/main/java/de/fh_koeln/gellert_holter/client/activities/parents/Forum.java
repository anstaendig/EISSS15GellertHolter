package de.fh_koeln.gellert_holter.client.activities.parents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.*;
import de.fh_koeln.gellert_holter.client.util.Thread;

public class Forum extends Activity {

    ListView lv;
    ThreadAdapter ta;
    ArrayList<Thread> threads = null;

    /**
     * ThreadAdapter ta wird an den ListView und die ArrayList Threads gebunden.
     * Bei Click auf ein Item im ListView wird dieser Thread an neue Activity gesendet.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        lv = (ListView) findViewById(R.id.listView);
        threads = new ArrayList<>();
        ta = new ThreadAdapter(Forum.this, R.layout.thread, threads);
        lv.setAdapter(ta);
        getThreads();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showThread((Thread) lv.getItemAtPosition(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum, menu);
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

    /**
     * Abruf der Threads vom Server via asynchronem http get request.
     * Response des Server ist ein JSONArray. Die JSONObjects werden via GSON in Java-Objekte
     * geparst und dem der ArrayList threads hinzugefügt. Der ThreadAdapter wird auf die Änderungen
     * per notifyDataSetChanged hingewiesen.
     */
    private void getThreads() {
        RestClient.get("forum", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Thread thread = new Gson().fromJson(response.getJSONObject(i).toString(), Thread.class);
                        threads.add(thread);
                    } catch (JSONException e) {
                        // TO-DO: Handle Exception
                    }
                }
                ta.notifyDataSetChanged();
            }
        });
    }

    /**
     * Neue Activity wird gestartet und per Intent der anzuzeigende Thread übergeben.
     *
     * @param thread
     */
    private void showThread(Thread thread) {
        Intent intent = new Intent(this, ShowThread.class);
        intent.putExtra("thread", thread);
        startActivity(intent);
    }


    /**
     * Activity zum Erstellen eines neuen Threads wird gestartet
     *
     * @param view
     */
    public void newThread(View view) {
        Intent intent = new Intent(this, NewThread.class);
        startActivity(intent);
    }
}
