package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.RestClient;

public class MainChildren extends Activity {

    String PREFS_NAME = "sharedPreferences";
    SharedPreferences.Editor editor;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_children);
        context = this;
        updateProfile();
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

    private void updateProfile() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        RestClient.get("children/exampleChild", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("Response: ", response.toString());
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