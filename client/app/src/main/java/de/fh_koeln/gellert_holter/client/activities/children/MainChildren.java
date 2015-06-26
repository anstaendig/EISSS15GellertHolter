package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.Child;
import de.fh_koeln.gellert_holter.client.util.RestClient;

public class MainChildren extends Activity {

    String PREFS_NAME = "sharedPreferences";
    SharedPreferences settings;

    SharedPreferences.Editor editor;
    Activity context;
    TextView lastBs, lastIe, lastDate, lastBe;

    Child child;


    /**
     * Beim Starten der Activty wird der letzte Logbucheintrag aus dem lokalen Speicher (SharedPref)
     * geladen und angezeigt.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_children);
        lastBs = (TextView) findViewById(R.id.lastEntryBS);
        lastIe = (TextView) findViewById(R.id.lastEntryIe);
        lastDate = (TextView) findViewById(R.id.lastEntryDate);
        lastBe = (TextView) findViewById(R.id.lastEntryBe);
        context = this;
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        child = new Gson().fromJson(settings.getString("profile", ""), Child.class);
        List<Child.LogEntry> log = child.log;
        Double be = round(log.get(log.size()-1).be, 2);
        lastBs.setText(log.get(log.size() - 1).bloodsugar.toString());
        lastIe.setText(log.get(log.size() - 1).insulin.toString());
        lastDate.setText(log.get(log.size() - 1).date);

        lastBe.setText(be.toString());

        updateProfile();

    }

    /**
     * Methoden zum Runden von Doubles
     *
     * @param value Double, der gerundet werden soll
     * @param places Anzahl der Nachkommastellen
     * @return
     */
    public static double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_children, menu);
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
     * Das lokale Kindprofil wird mit dem Kindprofil auf dem Server synchronisiert.
     */
    private void updateProfile() {
        String target = "children/" + child._id;
        RestClient.get(target, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("Response: ", response.toString());
                editor = settings.edit();
                editor.putString("profile", response.toString());
                editor.apply();
                Toast.makeText(context, "Profil wurde erfolgreich aktualisiert!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startNewEntry(View view) {
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);
    }
}