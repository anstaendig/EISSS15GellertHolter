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
import util.GridAdapter;
import util.Product;
import util.RestClient;

public class ProductSearchResult extends Activity {

    GridView gv;
    GridAdapter ga;
    ArrayList<Product> products = null;
    String search;
    Activity context;

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
                Log.e("TEST: ", product.getDescription());
                addProductToList(product);
            }
        });
    }

    private void addProductToList(Product product) {
        ArrayList<Product> list = new ArrayList<>();
        list.add(product);
        Intent intent = new Intent(this, ProductSearch.class);
        intent.putParcelableArrayListExtra("product", list);
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

    void searchProducts(String search) {
        RestClient.get("carbs/" + search, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Product product = new Gson().fromJson(response.getJSONObject(i).toString(), Product.class);
                        Log.e("PRODUCT-TYPE:", product.getType());
                        products.add(product);
                        Log.e("TEST: ", products.get(i)._id);
                    } catch (JSONException e) {
                        // TO-DO: Handle Exception
                    }
                }
                ga.notifyDataSetChanged();
            }
        });
    }
}
