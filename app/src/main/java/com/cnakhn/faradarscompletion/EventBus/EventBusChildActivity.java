package com.cnakhn.faradarscompletion.EventBus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cnakhn.faradarscompletion.R;

import org.greenrobot.eventbus.EventBus;

public class EventBusChildActivity extends AppCompatActivity {
    EditText inputEbText;
    Button btnSubmitTxtEb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_child);
        inputEbText = findViewById(R.id.input_txt_eb);
        btnSubmitTxtEb = findViewById(R.id.btn_submit_txt_eb);
        btnSubmitTxtEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = inputEbText.getText().toString();
                CustomMessageEvent event = new CustomMessageEvent();
                event.setCustomMsg(userText);
                EventBus.getDefault().post(event);
                finish();
            }
        });
    }
}