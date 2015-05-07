package de.fh_koeln.gellert_holter.dms.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.fh_koeln.gellert_holter.dms.R;
import de.fh_koeln.gellert_holter.dms.util.Stream;

public class DataStorageActivity extends ActionBarActivity {

    File file;
    String filePath;

    EditText stringToSave;

    final String filename = "dataStorage.json";
    String s;

    FileInputStream fis;
    FileOutputStream fos;

    Context context;

    TextView readText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        stringToSave = (EditText) findViewById(R.id.stringToSave);
        context = getApplicationContext();
        filePath = context.getFilesDir() + "/" + filename;
        file = new File(filePath);
        readText = (TextView) findViewById(R.id.readText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_storage, menu);
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

    public void save(View view) {
        s = stringToSave.getText().toString();
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(s.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "String successfully saved to dataStorage.json", Toast.LENGTH_SHORT).show();
    }

    public void read(View view) {
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String result = Stream.read(fis);
        readText.setText(result);
    }
}