package de.fh_koeln.gellert_holter.dms.util;


import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Streamer {

    private Streamer() {
    }

    public static String read(InputStream in) {

        String readLines = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            readLines = line;
            while ((line = br.readLine()) != null) {
                readLines += line;
            }
        } catch (IOException e) {
            readLines += "Reading InputStream failed!";
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    readLines += "Closing BufferedReader failed!";
                    e.printStackTrace();
                }
            }
        }

        return readLines;
    }

    public static void write(OutputStream out, String content) {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

        try {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}