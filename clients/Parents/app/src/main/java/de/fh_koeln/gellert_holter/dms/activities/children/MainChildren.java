package de.fh_koeln.gellert_holter.dms.activities.children;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.fh_koeln.gellert_holter.dms.R;

public class MainChildren extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_children);
        context = this;
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

    public void startNewEntry(View view) {
        /*
        CharSequence cs[] = { "Basis", "Bolus" };
        new AlertDialog.Builder(context, 4)
                .setTitle("Welche Art von Eintrag möchtest Du anlegen=?")
                .setItems(cs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(context, BasisInsulin.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, BolusInsulin.class);
                            startActivity(intent);
                        }
                    }
                })
                .show();
                */
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);
    }

    public void startLogBook(View view) {
    }
/*
    public void startLogBook(View view) {
        Intent intent = new Intent(this, LogBook.class);
        startActivity(intent);
    }
    */

}
