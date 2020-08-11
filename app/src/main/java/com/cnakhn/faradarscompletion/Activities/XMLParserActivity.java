package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cnakhn.faradarscompletion.DataModel.Product.Product;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductPullParser;
import com.cnakhn.faradarscompletion.R;

import java.util.ArrayList;
import java.util.List;

public class XMLParserActivity extends AppCompatActivity {
    private List<Product> products;
    private ListView productList;
    private ArrayAdapter<Product> productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);
    }

    private void initAdapter() {
        if (products == null) products = new ArrayList<>();
        productList = findViewById(R.id.xml_products_list);
        productAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, products);
        productList.setAdapter(productAdapter);
    }

    private void initParser() {
        products = new ProductPullParser(this).parseXml();
        initAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Import Products").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                initParser();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}