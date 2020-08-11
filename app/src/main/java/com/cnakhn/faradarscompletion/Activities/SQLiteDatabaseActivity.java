package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cnakhn.faradarscompletion.DataModel.Product.Product;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductDBHelper;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductListViewAdapter;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductPullParser;
import com.cnakhn.faradarscompletion.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseActivity extends AppCompatActivity {
    private static final String TAG = "SQLiteDatabaseActivity";
    private ProductDBHelper dbHelper;
    private ListView dbProductsList;
    private List<Product> productList;
    private ProductListViewAdapter adapter;
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);
        analytics = FirebaseAnalytics.getInstance(this);
        initViews();
    }

    private void initViews() {
        dbHelper = new ProductDBHelper(this);
        dbProductsList = findViewById(R.id.db_products_list);
        productList = new ArrayList<>();
    }

    private void initList() {
        if (productList == null) productList = new ArrayList<>();
        productList = dbHelper.getProducts();
        adapter = new ProductListViewAdapter(this, productList);
        dbProductsList.setDividerHeight(1);
        dbProductsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void insertProducts() {
        List<Product> products = new ProductPullParser(SQLiteDatabaseActivity.this).parseXml();
        for (Product product : products) {
            dbHelper.insertProducts(product);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Import Products").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                insertProducts();
                initList();
                Product product = new Product();
                Bundle bundle = new Bundle();
                bundle.putLong("productID", product.getId());
                bundle.putString("productName", product.getName());
                bundle.putString("productCategory", product.getCategory());
                bundle.putDouble("productPrice", product.getPrice());
                analytics.logEvent("product", bundle);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}