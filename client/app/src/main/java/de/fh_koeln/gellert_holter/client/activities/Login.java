package de.fh_koeln.gellert_holter.client.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.activities.parents.MainParents;
import de.fh_koeln.gellert_holter.client.util.Authentication;
import de.fh_koeln.gellert_holter.client.util.RestClient;

/**
 * Activity zum Login.
 * TODO: Da die Ressourcen /signup und /authenticate nicht restkonform sind, muss eine alternative
 * modelliert werden. Keine Zeit mehr!
 */
public class Login extends Activity {

    RequestParams params;
    EditText email;
    EditText password;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        context = this;
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

    /**
     * e-mail und password werden per asynchronem http post request an den serer übermittelt.
     * Bei responsecode 200 wird der übermittelte token in der Authentication Klasse gespeichert.
     * Bei responsecode 401 wird die vom server übermittelte Fehlermeldung per Toast angezeigt.
     * Bei responsecode 404 wird gefragt, ob ein Account erstellt werden möchte und falls ja,
     * per asynchronem http post request ein neuer Account auf dem API-Endpoint signup angelegt und
     * der im response stehende token in der Authentication Klasse gespeichert.
     *
     * @param view
     */
    public void login(View view) {
        params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        RestClient.post("api/authenticate", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String token;
                try {
                    token = response.getString("token");
                    Authentication.setToken(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switchA();
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                try {
                    Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (statusCode) {
                    case 401:
                        email.setText("");
                        password.setText("");
                        break;
                    case 404:
                        new AlertDialog.Builder(context)
                                .setTitle("Account Signup")
                                .setMessage("Do you want to register an account?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        RestClient.post("api/signup", params, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                try {
                                                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                                    Authentication.setToken(response.getString("token"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                }
            }
        });
    }


    public void switchA() {
        Intent intent = new Intent(this, MainParents.class);
        startActivity(intent);
    }
}