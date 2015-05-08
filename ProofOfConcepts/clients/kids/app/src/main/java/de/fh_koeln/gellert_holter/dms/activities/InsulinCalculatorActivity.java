package de.fh_koeln.gellert_holter.dms.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import de.fh_koeln.gellert_holter.dms.R;

public class InsulinCalculatorActivity extends ActionBarActivity {

    EditText be;
    EditText beFaktor;
    EditText blutZucker;
    EditText zielWert;
    EditText korrekturZahl;

    TextView result;

    DecimalFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_calculator);
        be = (EditText) findViewById(R.id.be);
        beFaktor = (EditText) findViewById(R.id.beFaktor);
        blutZucker = (EditText) findViewById(R.id.blutzucker);
        zielWert = (EditText) findViewById(R.id.zielwert);
        korrekturZahl = (EditText) findViewById(R.id.korrekturzahl);

        result = (TextView) findViewById(R.id.result);

        numberFormat = new DecimalFormat("#.00");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insulin_calculatora, menu);
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

    public void calculateBasis(View view) {

        double beDouble = Double.parseDouble(be.getText().toString());
        double beFaktorDouble = Double.parseDouble(beFaktor.getText().toString());
        double ie = beDouble * beFaktorDouble;
        result.setText(numberFormat.format(ie));
    }

    public void calculateBolus(View view) {

        double blutZuckerDouble = Double.parseDouble(blutZucker.getText().toString());
        double zielWertDouble = Double.parseDouble(zielWert.getText().toString());
        double korrekturZahlDouble = Double.parseDouble(korrekturZahl.getText().toString());
        double ie = (blutZuckerDouble - zielWertDouble) / korrekturZahlDouble;
        result.setText(numberFormat.format(ie));
    }
}
