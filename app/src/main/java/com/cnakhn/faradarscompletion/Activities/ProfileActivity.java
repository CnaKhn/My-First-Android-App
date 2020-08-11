package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CoordinatorLayout coordinatorLayout;
    private TextInputEditText inputFullName, inputNumber, inputEmail;
    private MaterialRadioButton btnMale, btnFemale;
    private MaterialButton btnSave;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));
        initViews();
        setupToolbar();
        if (sharedPreferences != null) {
            inputFullName.setText(sharedPreferences.getString("FullName", ""));
            inputEmail.setText(sharedPreferences.getString("Email", ""));
            inputNumber.setText(sharedPreferences.getString("Number", ""));
            if (sharedPreferences.getBoolean("Male", false)) {
                btnMale.setChecked(true);
            } else btnFemale.setChecked(true);
        }

    }

    private void initViews() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        coordinatorLayout = findViewById(R.id.layout_profile_coordinator_layout);
        inputFullName = findViewById(R.id.input_full_name);
        inputNumber = findViewById(R.id.input_number);
        inputEmail = findViewById(R.id.input_email);
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = inputEmail.getText().toString();

                if (email.isEmpty() || !email.contains("@") ||
                        email.lastIndexOf('@') > email.lastIndexOf('.') ||
                        email.split("@").length != 2 || email.indexOf('@') == 0 || email.charAt(email.length() - 1) == '.') {
                    inputEmail.setError("Invalid Email Address");
                    btnSave.setEnabled(false);
                } else {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnMale = findViewById(R.id.btn_male);
        btnFemale = findViewById(R.id.btn_female);
        btnSave = findViewById(R.id.btn_save_prefs);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSave)) {
            saveToPref();
        }
    }

    private void saveToPref() {
        editor.putString("FullName", inputFullName.getText().toString());
        editor.putString("Number", inputNumber.getText().toString());
        editor.putString("Email", inputEmail.getText().toString());
        editor.putBoolean("Male", btnMale.isChecked());
        editor.apply();
    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.edit_profile_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i) instanceof TextView) {
                ((TextView) toolbar.getChildAt(i)).setTypeface(typeface);
            }

        }
    }

}