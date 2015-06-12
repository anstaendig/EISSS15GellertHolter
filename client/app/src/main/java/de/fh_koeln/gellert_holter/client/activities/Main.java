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
import util.Authentication;

public class Main extends Activity {

    Boolean login;
    Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //authentication = new Authentication(this);
        //login = authentication.isAuthorized();
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

    public void startChildren(View view) {
        Intent intent = new Intent(this, MainChildren.class);
        startActivity(intent);
    }

    public void startParents(View view) {
        // TODO Implement MainParents
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }
}

