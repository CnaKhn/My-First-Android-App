package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cnakhn.faradarscompletion.DataModel.Product.FakeDataGenerator;
import com.cnakhn.faradarscompletion.DataModel.Product.ProductRecyclerViewAdapter;
import com.cnakhn.faradarscompletion.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<FakeDataGenerator> productList;
    ProductRecyclerViewAdapter adapter;
    private boolean isLoading = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.products_progress_bar);


        initRecyclerView();
        initScrollListener();
    }

    private void initRecyclerView() {
        initProductList();
        adapter = new ProductRecyclerViewAdapter(productList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        productList.add(new FakeDataGenerator("Lorem", "Lorem ipsum", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.", "10$"));
        adapter.notifyItemInserted(productList.size() - 1);
        Handler handler = new Handler();
        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                productList.remove(productList.size() - 1);
                int scrollPosition = productList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;
                while (currentSize - 1 < nextLimit) {
                    productList.add(new FakeDataGenerator("Lorem", "Lorem ipsum", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.", "15$"));
                    currentSize++;
                }

                adapter.notifyDataSetChanged();
                isLoading = false;
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }

    private void initProductList() {
        if (productList == null) {
            productList = new ArrayList<>();
        } else {
            productList.clear();
        }
        int i = 0;
        while (i < 10) {
            productList.add(new FakeDataGenerator("Lorem", "Lorem ipsum", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.", "10$"));
            i++;
        }
    }

}