package de.fh_koeln.gellert_holter.parents.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import de.fh_koeln.gellert_holter.parents.R;
import util.Authentication;
import util.RestClient;

public class Login extends Activity {

    RequestParams params;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
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

    public void login(View view) {
        params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        RestClient.post("api/authenticate", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                String token = "";
                email.setText("Hat funktioniert!");
                try {
                    token = response.getString("token");
                    Authentication.setToken(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("Token: ", token);
                switchA();
            }
        });
    }

    public void switchA() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}