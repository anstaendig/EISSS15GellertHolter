package de.fh_koeln.gellert_holter.client.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import de.fh_koeln.gellert_holter.client.activities.Login;

/**
 * Helfer-Klasse, die die Authentifizierung implementiert. Diese wird aufgrund der nicht restkonformen
 * REST-API Endpoints "/signup" und "/authenticate" nicht mehr genutzt.
 */
public class Authentication extends Application {

    String PREFS_NAME = "sharedPreferences";

    Context context;
    SharedPreferences settings;
    static SharedPreferences.Editor editor;
    public static String token;

    public Authentication(Activity context) {
        this.context = context;
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        token = settings.getString("token", "");
    }

    /**
     * Setzt Token in den SharedPreferences
     *
     * @param token
     */
    public static void setToken(String token) {
        Log.e("Token: ", token);
        editor.putString("token", token);
        editor.commit();
    }

    /**
     * Überprüft, ob token vorhanden
     *
     * @return true, wenn token vorhanden
     * false, wenn nicht.
     */
    public boolean isAuthorized() {
        return !token.equals("");
    }

    /**
     * Token löschen und Login activity öffnen.
     */
    public void logout() {
        editor.putString("token", "");
        editor.apply();
        openLogin();
    }

    /**
     * Login Activity starten.
     */
    public void openLogin() {
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }
}