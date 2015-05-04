package de.fh_koeln.gellert_holter.dms;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProofOfConcept extends ActionBarActivity {

    EditText etResponse;
    URL url;
    /* InputStream in; */
    HttpURLConnection urlConnection = null;
    Activity a = this;
    static StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_of_concept);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
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


    public void request(View view) {
        /*
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    url = new URL("http://192.168.1.45:3000/poc/client-server");
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line + "\n\r");
                    in.close();
                    Log.d("POC", sb.toString());
                    a.runOnUiThread(new Thread(new Test1(sb)) {

                    });
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        thread.start();
        */

        try {
            url = new URL("http://192.168.1.45:3000/poc/client-server");
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line + "\n\r");
            in.close();
            etResponse = (EditText) findViewById(R.id.etResponse);
            etResponse.setText(sb.toString());
            Log.d("POC", sb.toString());
        } catch (Exception e) { e.printStackTrace(); }
    }
/*
    public void test(StringBuilder sb) {
        class Test1 implements Runnable {
            StringBuilder sb;
            Test(StringBuilder sb) { this.sb = sb; }
            public void run() {
                etResponse = (EditText) findViewById(R.id.etResponse);
                etResponse.setText(sb.toString());
            }
        }
    }
    */
}
