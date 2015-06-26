package de.fh_koeln.gellert_holter.client.util;

import android.app.Application;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Statischer RestClient zur asynchronen Kommunikation via HTTP Methoden GET, PUT, POST, DELETE
 * zwischen Client und Server.
 * Access-Token wird bei jedem Request über Header übermittelt für zustandslose Authentifizierung.
 */
public class RestClient extends Application {

    private static final String BASE_URL = "http://10.0.2.2:3000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String token = Authentication.token;

    public RestClient() {
    }

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("x-access-token", token);
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("x-access-token", token);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("x-access-token", token);
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("x-access-token", token);
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}