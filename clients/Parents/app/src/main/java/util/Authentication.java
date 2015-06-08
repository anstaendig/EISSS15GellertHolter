package util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import de.fh_koeln.gellert_holter.dms.activities.Login;

/**
 * Created by anstaendig on 30/05/15.
 */
public class Authentication extends Application {

    String PREFS_NAME = "sharedPreferences";

    Context context;
    SharedPreferences settings;
    static SharedPreferences.Editor editor;
    public static String token;

    public Authentication(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        token = settings.getString("token", "");
    }

    public static void setToken(String token) {
        Log.e("Token: ", token);
        editor.putString("token", token);
        editor.commit();
    }

    public boolean isAuthorized() {
        return !token.equals("");
    }

    public void logout() {
        editor.putString("token", "");
        editor.apply();
        openLogin();
    }

    public void openLogin() {
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }
}