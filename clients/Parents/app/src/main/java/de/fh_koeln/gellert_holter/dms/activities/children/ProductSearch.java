package de.fh_koeln.gellert_holter.dms.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fh_koeln.gellert_holter.dms.R;
import util.*;
import util.Thread;

public class ProductSearch extends Activity {

    EditText search;
    ListView lv;
    ProductAdapter pa;
    ArrayList<Product> products;

    //Long BE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        search = (EditText) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.productList);
        products = new ArrayList<>();
        pa = new ProductAdapter(this, products);
        lv.setAdapter(pa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_search, menu);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        List<Product> product = intent.getParcelableArrayListExtra("product");
        Log.e("TESTEN WIR HIER", product.toString());
        products.add(product.get(0));
        pa.notifyDataSetChanged();
    }

    public void searchProduct(View view) {
        String productSearch = search.getText().toString();
        Intent intent = new Intent(this, ProductSearchResult.class);
        intent.putExtra("productSearch", productSearch);
        startActivity(intent);
    }

    public void backToEntry(View view) {
        Log.e("TEST", "test");
        Intent intent = new Intent(this, AddEntry.class);
        for(int i = 0; i < lv.getCount(); i++) {
            String item = pa.getItemNew(i);
            Log.e("VALUES", "" +item);
        }
    }
}
