package de.fh_koeln.gellert_holter.dms.util;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class AsyncTaskGet extends AsyncTask<URL, String, String> {

    URL _url;

    public AsyncTaskGet(URL url) {
        _url = url;
    }

    @Override
    protected String doInBackground(URL... params) {

        String result = null;

        try {
            result = Stream.read(new BufferedInputStream(_url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}