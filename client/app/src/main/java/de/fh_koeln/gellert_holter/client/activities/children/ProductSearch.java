package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.fh_koeln.gellert_holter.client.R;
import util.Child;
import util.Product;
import util.ProductAdapter;
import util.RestClient;

public class ProductSearch extends Activity {

    EditText search;
    ListView lv;
    ProductAdapter pa;
    ArrayList<Product> products;
    Integer correctionValue;
    Integer targetValue;
    Activity context;
    Integer bsValue;

    //Long BE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        Intent intent = getIntent();
        bsValue = intent.getIntExtra("bsValue", 0);
        search = (EditText) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.productList);
        products = new ArrayList<>();
        pa = new ProductAdapter(this, products);
        lv.setAdapter(pa);
        context = this;
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
        if (product != null) {
            products.add(product.get(0));
            pa.notifyDataSetChanged();
        }
    }

    public void searchProduct(View view) {
        String productSearch = search.getText().toString();
        Intent intent = new Intent(this, ProductSearchResult.class);
        intent.putExtra("productSearch", productSearch);
        startActivity(intent);
    }

    // TODO Take content from local child profile instead of a server request
    public void calculateUnits(View view) {
        Calendar calendar = Calendar.getInstance();
        Integer time = calendar.get(Calendar.HOUR_OF_DAY);
        SharedPreferences settings = context.getSharedPreferences("sharedPreferences", 0);
        String profile = settings.getString("profile", "");
        Child child = new Gson().fromJson(profile, Child.class);
        Double beFactor;
        if (isBetween(time, 5, 12)) {
            beFactor = child.therapy.factor.morning;
        } else if (isBetween(time, 12, 17)) {
            beFactor = child.therapy.factor.day;
        } else {
            beFactor = child.therapy.factor.evening;
        }
        Double total = 0.0;

        for (int i = 0; i < lv.getCount(); i++) {
            String item = pa.getProduct(i);
            Double tmp = Double.parseDouble(item);
            Double tmp2 = Double.parseDouble(pa.getItem(i).getBe());
            Double result = (tmp / 100) * tmp2;
            total += result;
        }
        correctionValue = child.therapy.correction;
        targetValue = child.therapy.target;
        Integer correctionUnits = (bsValue - targetValue) / correctionValue;

        ArrayList<String> results = new ArrayList<>();

        results.add(0, correctionUnits.toString());
        results.add(1, total.toString());
        results.add(2, beFactor.toString());

        Intent intent = new Intent(context, AddEntry.class);
        intent.putStringArrayListExtra("results", results);
        startActivity(intent);
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
