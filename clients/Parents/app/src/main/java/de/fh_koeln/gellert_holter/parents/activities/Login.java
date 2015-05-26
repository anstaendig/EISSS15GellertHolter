package de.fh_koeln.gellert_holter.parents.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import de.fh_koeln.gellert_holter.parents.R;

public class Login extends Activity {

    RequestParams params;
    EditText email;
    EditText password;
    AsyncHttpClient client;
    PersistentCookieStore myCookieStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        client = new AsyncHttpClient();
        myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void testLogin(View view) {
        client.get("http://10.0.2.2:3000/parent", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("STATUS: ", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void login(View view) {
        params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());

        client.post("http://10.0.2.2:3000/parent/login", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.e("LOGIN-STATUS", String.valueOf(statusCode));
                Log.e("LOGIN-RESPONSE", response.toString());
            }
/*
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
*/
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}
