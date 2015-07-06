package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.Child;
import de.fh_koeln.gellert_holter.client.util.RestClient;

/**
 * Activity zur Erstellung eines Logbucheintrags
 */
public class AddEntry extends Activity {

    EditText bsValue, notes, mood;
    TextView ieValue, beFactorValue, correctionValue, beValue;
    Activity context;
    String PREFS_NAME = "sharedPreferences";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        context = this;
        bsValue = (EditText) findViewById(R.id.bsValue);
        beValue = (TextView) findViewById(R.id.beValue);
        correctionValue = (TextView) findViewById(R.id.correctionValue);
        ieValue = (TextView) findViewById(R.id.ieValue);
        beFactorValue = (TextView) findViewById(R.id.beFactorValue);
        notes = (EditText) findViewById(R.id.notes);
    }

    /**
     * Bei Rückkehr auf die Activity werden aus dem Intent die nötigen Werte
     * (Broteinheiten in Mahlzeiten, Korrekturinsulin basierend auf Blutzucker etc..)
     * gelesen und daraufhin die Insulineinheiten berechnet und angezeigt.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        ArrayList<String> be = intent.getStringArrayListExtra("results");
        beValue.setText(be.get(1));
        correctionValue.setText(be.get(0));
        beFactorValue.setText(be.get(2));
        Double insulinUnits = (Double.parseDouble(beValue.getText().toString()) + Double.parseDouble(correctionValue.getText().toString())) * Double.parseDouble(beFactorValue.getText().toString());
        Double iU_rounded = round(insulinUnits, 2);
        ieValue.setText(iU_rounded.toString());
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

    /**
     * Starten der Activity, um Nahrungsmittel zu suchen
     * @param view
     */
    public void calculateInsulin(View view) {
        new AlertDialog.Builder(context)
                .setTitle("Broteinheiten")
                .setMessage("Du kannst jetzt nach Nahrungsmitteln suchen, um die Broteinheiten zu bestimmen!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ProductSearch.class);
                        intent.putExtra("bsValue", Integer.parseInt(bsValue.getText().toString()));
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    /**
     * Alle Informationen zum Logbucheintrag werden im Profil des Kindes sowohl auf dem Client
     * gespeichert, als auch an das Profil des Kindes auf dem Server gesendet.
     * @param view
     */
    public void addEntry(View view) {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        Child.LogEntry logEntry = new Child.LogEntry();
        logEntry.date = nowAsISO;
        logEntry.bloodsugar = Integer.parseInt(bsValue.getText().toString());
        logEntry.be = Double.parseDouble(beValue.getText().toString());
        logEntry.beFactor = Double.parseDouble(beFactorValue.getText().toString());
        logEntry.correctionValue = Integer.parseInt(correctionValue.getText().toString());
        logEntry.insulin = Double.parseDouble(ieValue.getText().toString());
        logEntry.notes = notes.getText().toString();
        //logEntry.mood = mood.getText().toString();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        String profile = settings.getString("profile", "");
        Child child = new Gson().fromJson(profile, Child.class);
        child.log.add(logEntry);
        String newProfile = new Gson().toJson(child);
        editor.putString("profile", newProfile);
        editor.apply();
        RequestParams params = new RequestParams();
        params.put("date", logEntry.date);
        params.put("bloodsugar", logEntry.bloodsugar);
        params.put("be", logEntry.be);
        params.put("beFactor", logEntry.beFactor);
        params.put("correctionValue", logEntry.correctionValue);
        params.put("insulin", logEntry.insulin);
        params.put("notes", logEntry.notes);
        //params.put("mood", mood.getText().toString());
        String target = "children/" + child._id + "/log";
        RestClient.put(target, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("Response", response.toString());
            }
        });
    }
}