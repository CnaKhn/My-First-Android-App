package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleIdentifierActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "GoogleIdentifierActivit";
    private static final int REQUEST_SIGN_IN = 1;
    private GoogleSignInOptions signInOptions;
    private GoogleApiClient apiClient;
    private SignInButton btnSignIn;
    private ImageView profileImage;
    private TextView txtUsername, txtEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_identifier);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        initGoogleSignIn();
    }

    private void initGoogleSignIn() {
        btnSignIn = findViewById(R.id.btn_sign_in);
        profileImage = findViewById(R.id.google_profile_image);
        txtUsername = findViewById(R.id.google_profile_user);
        txtEmailAddress = findViewById(R.id.google_profile_email);
        signInOptions =
                new GoogleSignInOptions.Builder
                        (GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient =
                new GoogleApiClient.Builder(this)
                        .enableAutoManage(this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                        .build();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        Auth.GoogleSignInApi
                                .getSignInIntent(apiClient);
                startActivityForResult(intent, REQUEST_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i(TAG, "onActivityResult: " + result.isSuccess());
            GoogleSignInAccount account = result.getSignInAccount();
            if (result.isSuccess()) {
                txtUsername.setText(account.getDisplayName());
                txtEmailAddress.setText(account.getEmail());
                Uri photoUrl = account.getPhotoUrl();
                Glide.with(this)
                        .load(photoUrl.toString())
                        .thumbnail(0.5f)
                        .into(profileImage);

            } else if (account == null) {
                profileImage.setImageResource(0);
                txtUsername.setText("");
                txtEmailAddress.setText("");
                return;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: " + connectionResult);
    }
}