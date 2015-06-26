package de.fh_koeln.gellert_holter.client.activities.children;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.fh_koeln.gellert_holter.client.R;
import de.fh_koeln.gellert_holter.client.util.Child;
import de.fh_koeln.gellert_holter.client.util.Product;
import de.fh_koeln.gellert_holter.client.util.ProductAdapter;

public class ProductSearch extends Activity {

    EditText search;
    ListView lv;
    ProductAdapter pa;
    ArrayList<Product> products;
    Integer correctionValue;
    Integer targetValue;
    Activity context;
    Integer bsValue;

    /**
     * Über den Intent wird der Blutzucker ausgelesen. Der Adapter pa wird an den ListView lv gebunden
     * um die Produkte aus der ArrayList im ListView anzuzeigen.
     * @param savedInstanceState
     */
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

    /**
     * Bei Rückkehr auf die Activity wird der ArrayList products das Produkt aus dem Intent hinzugefügt
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");
        if (product != null) {
            products.add(product);
            pa.notifyDataSetChanged();
        }
    }

    /**
     * Die Activity mit dem GridView der Suchresultate wird aufgerufen. Über den Intent
     * wird der Suchbegriff mitgegeben.
     * @param view
     */
    public void searchProduct(View view) {
        String productSearch = search.getText().toString();
        Intent intent = new Intent(this, ProductSearchResult.class);
        intent.putExtra("productSearch", productSearch);
        startActivity(intent);
    }


    /**
     * Insulineinheiten werden berechnet. Basierend auf der aktuellen Zeit wird der beFactor aus
     * dem lokalen Kindprofil ausgelesen (morgens, mittags, abends). Es wird über die Liste der
     * Nahrungsmittel iteriert und die Broteinheiten basierend auf dem Gewicht summiert.
     * Optional werden basierend auf dem Blutzucker und dem Ziel- und Korrekturwert (im Kindprofil)
     * Korrektureinheiten addiert. Broteinheiten, Korrektureinheiten und der BE-Faktor werden in
     * einer ArrayList gespeichert und per Intent an die Activity zur Erstellung des Eintrags gesendet.
     * @param view
     */
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

    /**
     * Methode um festzustellen, ob Integer in einem Intervall liegt
     * @param x Zu überprüfender Integer
     * @param lower Integer als untere Grenze des Intervalls
     * @param upper Integer als obere Grenze des Intervalls
     * @return true, wenn im Intervall. false, wenn nicht.
     */
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
