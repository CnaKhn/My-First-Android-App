package com.cnakhn.faradarscompletion.Activities.JSONParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cnakhn.faradarscompletion.DataModel.JSONParser.JSONParserAdapter;
import com.cnakhn.faradarscompletion.DataModel.JSONParser.JSONParserItem;
import com.cnakhn.faradarscompletion.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParserActivity extends AppCompatActivity implements JSONParserAdapter.onItemClickListener {
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LIKES = "likes";

    private RecyclerView recyclerView;
    private JSONParserAdapter adapter;
    private ArrayList<JSONParserItem> jsonList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parser);
        jsonList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recycler_view_json_parser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parseJSON();

    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=17331473-64745815e882ee72ae2c24d24";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("hits");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject hit = array.getJSONObject(i);
                        String title = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likes = hit.getInt("likes");
                        jsonList.add(new JSONParserItem(imageUrl, title, likes));
                    }

                    adapter = new JSONParserAdapter(JSONParserActivity.this, jsonList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(JSONParserActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    @Override
    public void onItemClicked(int position) {
        Intent detail = new Intent(this, DetailActivity.class);
        JSONParserItem clickedItem = jsonList.get(position);
        detail.putExtra(EXTRA_URL, clickedItem.getJsonImageUrl());
        detail.putExtra(EXTRA_TITLE, clickedItem.getJsonTitle());
        detail.putExtra(EXTRA_LIKES, clickedItem.getJsonLikes());
        startActivity(detail);
    }
}