package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.DataModel.Contract.ContractIncomingNumber;
import com.cnakhn.faradarscompletion.DataModel.Contract.Contract;
import com.cnakhn.faradarscompletion.DataModel.Contract.ContractDBHelper;
import com.cnakhn.faradarscompletion.DataModel.Contract.ContractRecyclerViewAdapter;
import com.cnakhn.faradarscompletion.R;

import java.util.ArrayList;

public class NumberReceiverActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    private ArrayList<ContractIncomingNumber> incomingNumbers = new ArrayList<>();
    private ContractRecyclerViewAdapter adapter;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_receiver);
        initViews();
        readFromDB();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromDB();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(Contract.UPDATE_UI_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view_incoming_numbers);
        textView = findViewById(R.id.tv_incoming_numbers);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ContractRecyclerViewAdapter(incomingNumbers);
        recyclerView.setAdapter(adapter);
    }

    private void readFromDB() {
        incomingNumbers.clear();
        ContractDBHelper dbHelper = new ContractDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = dbHelper.readNumber(database);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id;
                String number;
                id = cursor.getInt(cursor.getColumnIndex("id"));
                number = cursor.getString(cursor.getColumnIndex(Contract.INCOMING_NUMBER));
                incomingNumbers.add(new ContractIncomingNumber(id, number));
            }
            cursor.close();
            dbHelper.close();
            adapter.notifyDataSetChanged();
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}