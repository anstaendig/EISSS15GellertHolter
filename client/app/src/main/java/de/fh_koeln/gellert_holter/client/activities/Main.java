package de.fh_koeln.gellert_holter.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.activities.children.MainChildren;
import de.fh_koeln.gellert_holter.client.activities.parents.Forum;
import de.fh_koeln.gellert_holter.client.activities.parents.MainParents;
import de.fh_koeln.gellert_holter.client.util.Authentication;

/**
 * Erste Activity im Client zur Auswahl der Kind- oder Eltern-Applikation
 */
public class Main extends Activity {

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
     * Startet MainActivity der Kinder
     *
     * @param view
     */
    public void startChildren(View view) {
        Intent intent = new Intent(this, MainChildren.class);
        startActivity(intent);
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

