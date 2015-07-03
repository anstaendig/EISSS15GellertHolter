package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.GridAdapter;
import de.fh_koeln.gellert_holter.client.util.Product;
import de.fh_koeln.gellert_holter.client.util.RestClient;

public class ProductSearchResult extends Activity {

    GridView gv;
    GridAdapter ga;
    ArrayList<Product> products;
    String search;
    Activity context;

    /**
     * GridAdapter zum DataBinding von Suchergebnissen und GridView.
     * Über Intent wird Suchbegriff ausgelesen und searchProducts ausgeführt.
     * Beim Click auf ein Suchergebnis im GrdView wird es der Liste der Nahrungsmittel hinzugefügt.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_result);
        products = new ArrayList<>();
        gv = (GridView) findViewById(R.id.gridView1);
        ga = new GridAdapter(this, products);
        gv.setAdapter(ga);
        Intent intent = getIntent();
        search = intent.getStringExtra("productSearch");
        searchProducts(search);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) gv.getItemAtPosition(position);
                addProductToList(product);
            }
        });
    }

    /**
     * Produkt aus der Liste der Suchergebnisse an die Liste der ausgewählten Nahrungsmittel über
     * Intent an die vorige Activity übergeben.
     * @param product
     */
    private void addProductToList(Product product) {
        Intent intent = new Intent(this, ProductSearch.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_search_result, menu);
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
     * Suche der Nahrungsmittel per asynchronem Http Get Request an den Server.
     * Im Response vom Server steht ein array von JSON-Objekten. Diese werden per GSON zu Java-Objekten
     * geparst und der ArrayList products hinzugefügt, die an den GridAdapter gebindet ist. Per
     * notifyDataSetChanged wird der adapter über Änderungen informiert und zeigt diese an.
     * @param search
     */
    void searchProducts(String search) {
        RestClient.get("carbs?search=" + search, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Product product = new Gson().fromJson(response.getJSONObject(i).toString(), Product.class);
                        products.add(product);
                    } catch (JSONException e) {
                        // TO-DO: Handle Exception
                    }
                }
                ga.notifyDataSetChanged();
            }
        });
    }
}
