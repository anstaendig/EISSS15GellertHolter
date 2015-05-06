package de.fh_koeln.gellert_holter.dms.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import de.fh_koeln.gellert_holter.dms.R;
import de.fh_koeln.gellert_holter.dms.util.AsyncTaskGet;
import de.fh_koeln.gellert_holter.dms.util.AsyncTaskPost;

public class ProofOfConceptsActivity extends ActionBarActivity {

    TextView twResponse;
    EditText etName;
    EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_of_concept);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        twResponse = (TextView) findViewById(R.id.twResponse);
        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etLastName);
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

    public void get(View view) {

        URL url = null;

        try {
            url = new URL("http://10.0.2.2:3000/poc");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonResult = new JSONObject(new AsyncTaskGet(url).execute().get());
            twResponse.setText(jsonResult.toString(2));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void post(View view) {

        URL url = null;
        String result = null;

        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();

        JSONObject content = new JSONObject();

        try {
            url = new URL("http://10.0.2.2:3000/poc");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            content.put("Name", name);
            content.put("LastName", lastName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            result = new AsyncTaskPost(url, content.toString()).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        twResponse.setText(result);
    }
}