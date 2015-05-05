package de.fh_koeln.gellert_holter.dms;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by anstaendig on 05/05/15.
 */
public class AsyncTaskGet extends AsyncTask<URL, String, String> {

    public URL _url;

    public AsyncTaskGet(URL url) {
        _url = url;
    }

    @Override
    protected String doInBackground(URL... params) {
        String result = null;
        try {
            InputStream in = new BufferedInputStream(_url.openStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line + "\n\r");
            in.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}