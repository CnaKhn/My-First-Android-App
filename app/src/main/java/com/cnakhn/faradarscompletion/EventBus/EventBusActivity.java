package com.cnakhn.faradarscompletion.EventBus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventBusActivity extends AppCompatActivity {
    TextView txtEb;
    Button btnChildEb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);
        txtEb = findViewById(R.id.txt_eb);
        btnChildEb = findViewById(R.id.btn_child_eb);
        btnChildEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventBusActivity.this, EventBusChildActivity.class));
            }
        });
    }

    @Subscribe
    public void onEvent(CustomMessageEvent event) {
        Log.i("EventBus", "onEvent: " + event.getCustomMsg());
        txtEb.setText(event.getCustomMsg());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}