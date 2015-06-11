package de.fh_koeln.gellert_holter.dms.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.fh_koeln.gellert_holter.dms.R;
import util.RestClient;

public class AddEntry extends Activity {

    EditText bsValue, notes;
    TextView ieValue, beFactorValue, correctionValue, beValue;
    Integer correctionValueResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        bsValue = (EditText) findViewById(R.id.bsValue);
        beValue = (TextView) findViewById(R.id.beValue);
        correctionValue = (TextView) findViewById(R.id.correctionValue);
        ieValue = (TextView) findViewById(R.id.ieValue);
        beFactorValue = (TextView) findViewById(R.id.beFactorValue);
        notes = (EditText) findViewById(R.id.notes);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        ArrayList<String> be = intent.getStringArrayListExtra("results");
        beValue.setText(be.get(1));
        correctionValue.setText(be.get(0));
        beFactorValue.setText(be.get(2));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
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

    public void calculateBe(View view) {
        Intent intent = new Intent(this, ProductSearch.class);
        intent.putExtra("bsValue", Integer.parseInt(bsValue.getText().toString()));
        startActivity(intent);
    }

    public void calculateInsulin(View view) {
        Double insulinUnits = (Double.parseDouble(beValue.getText().toString()) + Double.parseDouble(correctionValue.getText().toString())) * Double.parseDouble(beFactorValue.getText().toString());
        ieValue.setText(insulinUnits.toString());
    }

    public void addEntry(View view) {
        RequestParams params = new RequestParams();
        params.put("bloodsugar", bsValue.getText().toString());
        params.put("be", beValue.getText().toString());
        params.put("beFactor", beFactorValue.getText().toString());
        params.put("correctionValue", correctionValue.getText().toString());
        params.put("insulin", ieValue.getText().toString());
        params.put("notes", notes.getText().toString());
        //params.put("mood", mood.getText().toString());
        RestClient.post("child/exampleChild/log", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("Response", response.toString());
            }
        });
    }
}