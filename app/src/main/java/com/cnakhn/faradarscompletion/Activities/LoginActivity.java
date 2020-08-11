package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.ApiService;
import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView txtMyApp, txtLoginAccount, txtNoMember;
    private TextInputEditText inputUsername, inputEmail, inputPassword;
    private MaterialButton btnLogin, btnSignUp;
    private boolean isSigningUp = false;
    private CoordinatorLayout layoutSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorOrange));
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.login_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtLoginAccount = findViewById(R.id.txt_login_account);
        txtMyApp = findViewById(R.id.txt_login_myapp);
        txtNoMember = findViewById(R.id.txt_no_member);
        btnSignUp = findViewById(R.id.btn_sign_up);
        inputUsername = findViewById(R.id.input_login_username);
        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        btnLogin = findViewById(R.id.btn_login);
        layoutSnackbar = findViewById(R.id.layout_login_snackbar);
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Utils utils = new Utils(LoginActivity.this.getApplicationContext());
        final String username = inputUsername.getText().toString();
        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        final Intent intent = new Intent();
        if (v.equals(btnSignUp)) {
            isSigningUp = !isSigningUp;
            if (isSigningUp) {
                txtLoginAccount.setText("Join Us!");
                btnLogin.setText("Sign Up");
                txtNoMember.setText("Already have an account?");
                btnSignUp.setText("Log in");
            } else {
                txtLoginAccount.setText("Log in to your account");
                btnLogin.setText("Log in");
                txtNoMember.setText("Not a member yet?");
                btnSignUp.setText("Sign up");
            }
        }

        if (v.equals(btnLogin)) {
            if (isSigningUp) {
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 4) {
                        if (isEmailValid(email)) {
                            ApiService apiService = new ApiService(LoginActivity.this);
                            apiService.saveUser(username, email, password, new ApiService.onSignUpResponse() {
                                @Override
                                public void onResponse(int status) {
                                    switch (status) {
                                        case ApiService.STATUS_EMAIL_EXISTS:
                                            Snackbar.make(layoutSnackbar, "Email already exists.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        case ApiService.STATUS_FAILED:
                                            Snackbar.make(layoutSnackbar, "Sign Up Failed.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        case ApiService.STATUS_SUCCESS:
                                            Snackbar.make(layoutSnackbar, "User Created Successfully!", Snackbar.LENGTH_LONG).show();
                                            utils.saveUserLoginInfo(username, email);
                                            intent.putExtra("username", username);
                                            intent.putExtra("email", email);
                                            setResult(MainActivity.RESULT_OK, intent);
                                            break;
                                    }
                                }
                            });
                        } else {
                            inputEmail.setError("Email address is not valid.");
                        }
                    } else {
                        Snackbar.make(layoutSnackbar, "Password must be at least 4 characters.", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    inputEmail.setError("Enter your email address.");
                    inputPassword.setError("Enter your password.");
                }
            } else {
                if (!email.isEmpty() && !password.isEmpty()) {
                    ApiService apiService = new ApiService(LoginActivity.this);
                    apiService.loginUser(email, password, new ApiService.onLoginResponse() {
                        @Override
                        public void onResponse(boolean success) {
                            Snackbar.make(layoutSnackbar, "You've logged in successfully.", Snackbar.LENGTH_LONG).show();
                            utils.saveUserLoginInfo(username, email);
                            intent.putExtra("username", username);
                            intent.putExtra("email", email);
                            setResult(MainActivity.RESULT_OK, intent);
                        }
                    });
                } else {
                    inputEmail.requestFocus();
                    inputEmail.setError("Enter your email address.");
                    inputPassword.setError("Enter your password.");
                }
            }
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}