package de.fh_koeln.gellert_holter.parents.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.Cookie;

import java.util.List;

import de.fh_koeln.gellert_holter.parents.R;

public class Main extends Activity {

    List<Cookie> cookies;
    Boolean login = false;
    PersistentCookieStore mCookieStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCookieStore = new PersistentCookieStore(getApplicationContext());
        cookies = mCookieStore.getCookies();

        for (Cookie c : cookies) {
            if (c.getName().equals("connect.sid")) {
                login = true;
            }
        }

        if(!login) openLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(login) menu.getItem(1).setTitle("Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_login_logout:
                if(login) {logout();}
                else { openLogin() ;}
                return true;

            case R.id.action_settings:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void startForum(View view) {
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }

    private void logout() {
        mCookieStore.clear();
        login = false;
        openLogin();
    }

}