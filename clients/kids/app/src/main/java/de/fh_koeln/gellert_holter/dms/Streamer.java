package de.fh_koeln.gellert_holter.dms;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Streamer {

    //damit keine Instanziierung moeglich ist
    private Streamer() {
    }

    public static String readStream(InputStream in) {

        String readLines = "Fail";
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            readLines = line;
            while ((line = br.readLine()) != null) {
                readLines += line;
            }
        } catch (IOException e) {
            readLines += "Failed reading out of inputstream";
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    readLines += "Failed to Close BR! Caution!";
                }
            }
        }
        return readLines;
    }

    public static void writeStream(OutputStream out, String content) {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

        try {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            System.out.println("IO Exc on POST");
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("IO Exc on POST closing BufferedWriter");
            }
        }
    }
}