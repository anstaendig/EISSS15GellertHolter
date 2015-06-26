package de.fh_koeln.gellert_holter.client.activities;

import android.app.Activity;
import android.content.Context;
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
import de.fh_koeln.gellert_holter.client.activities.children.MainChildren;
import de.fh_koeln.gellert_holter.client.activities.parents.Forum;
import de.fh_koeln.gellert_holter.client.activities.parents.MainParents;
import de.fh_koeln.gellert_holter.client.util.Authentication;
import de.fh_koeln.gellert_holter.client.util.RestClient;

/**
 * Erste Activity im Client zur Auswahl der Kind- oder Eltern-Applikation
 */
public class Main extends Activity {

    String PREFS_NAME = "sharedPreferences";
    SharedPreferences settings;

    SharedPreferences.Editor editor;
    Activity context;
    Boolean login;
    Authentication authentication;

    /**
     * Überprüfung der Authentifizierung über tokens auskommentiert, da API-Endpoints nicht
     * Restkonform.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //authentication = new Authentication(this);
        //login = authentication.isAuthorized();r
        //if (!login) authentication.openLogin();
        context = this;
        settings = context.getSharedPreferences(PREFS_NAME, 0);

        updateProfile();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //if (login) menu.getItem(1).setTitle("Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            /*
            case R.id.action_login_logout:
                if (login) {
                    authentication.logout();
                } else {
                    authentication.openLogin();
                }
                return true;

            case R.id.action_settings:
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Hole Kind-Profil vom Server und
     * Starte MainActivity der Kinder
     *
     * @param view
     */
    public void startChildren(View view) {
        Intent intent = new Intent(this, MainChildren.class);
        startActivity(intent);
    }

    /**
     * Das lokale Kindprofil wird mit dem Kindprofil auf dem Server synchronisiert.
     */
    private void updateProfile() {
        String target = "children/557ad78338559bbaf3580f9a";
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

    /**
     * Startet MainActivity der Eltern
     *
     * @param view
     */
    public void startParents(View view) {
        Intent intent = new Intent(this, MainParents.class);
        startActivity(intent);
    }
}

