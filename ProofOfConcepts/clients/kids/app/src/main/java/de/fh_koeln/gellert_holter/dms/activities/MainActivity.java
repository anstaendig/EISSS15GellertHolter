package de.fh_koeln.gellert_holter.dms.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import de.fh_koeln.gellert_holter.dms.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startClientServerCommunicationActivity(View view) {
        Intent intent = new Intent(this, ClientServerCommunicationActivity.class);
        startActivity(intent);
    }

    public void startDataStorageActivity(View view) {
        Intent intent = new Intent(this, DataStorageActivity.class);
        startActivity(intent);
    }

    public void startActivityCommunicationActivity(View view) {
        Intent intent = new Intent(this, ActivityCommunicationActivity.class);
        startActivity(intent);
    }

    public void startActivityInsulinCalculatorActivity(View view) {
        Intent intent = new Intent(this, InsulinCalculatorActivity.class);
        startActivity(intent);
    }

}