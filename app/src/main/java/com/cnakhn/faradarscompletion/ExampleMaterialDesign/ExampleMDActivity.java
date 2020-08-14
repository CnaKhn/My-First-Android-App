package com.cnakhn.faradarscompletion.ExampleMaterialDesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.cnakhn.faradarscompletion.R;

public class ExampleMDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_md);
        Toolbar toolbar = findViewById(R.id.toolbar_example_md);
        setSupportActionBar(toolbar);
    }
}