package de.fh_koeln.gellert_holter.dms;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by anstaendig on 05/05/15.
 */
public class AsyncTaskPost extends AsyncTask<URL, String, String> {

    URL _url;
    String _content;

    public AsyncTaskPost(URL url, String content) {
        _url = url;
        _content = content;
    }

    @Override
    protected String doInBackground(URL... params) {
        String result = null;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) _url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setChunkedStreamingMode(0);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
            Streamer.writeStream(bos, _content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            result = Streamer.readStream(new BufferedInputStream(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
