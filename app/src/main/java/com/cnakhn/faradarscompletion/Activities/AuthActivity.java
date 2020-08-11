package com.cnakhn.faradarscompletion.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AuthActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final String FINGERPRINT_KEY = "FingerprintKey";
    TextView tvAuthError;
    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;
    KeyStore keyStore;
    Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws SecurityException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorBlack));
        tvAuthError = findViewById(R.id.tv_auth_error);

        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        checkPermissions();

        if (!hasPermissions()) {
            tvAuthError.setText("Fingerprint permission not enabled!");
            tvAuthError.setVisibility(View.VISIBLE);
        } else if (fingerprintManager.isHardwareDetected()) {
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                tvAuthError.setText("Register at least 1 fingerprint on your device.");
                tvAuthError.setVisibility(View.VISIBLE);

            } else if (!keyguardManager.isKeyguardSecure()) {
                tvAuthError.setText("Lock screen security not enabled in settings.");
                tvAuthError.setVisibility(View.VISIBLE);
            } else {
                generateKeys();
                if (initCipher()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    CancellationSignal cancellationSignal = new CancellationSignal();
                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            tvAuthError.setText(errString);
                            tvAuthError.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                            tvAuthError.setText(helpString);
                            tvAuthError.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                            tvAuthError.setText("");
                            tvAuthError.setVisibility(View.INVISIBLE);
                            finish();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            tvAuthError.setVisibility(View.VISIBLE);
                            tvAuthError.setText("Authentication Failed...");
                        }
                    }, null);

                }
            }
        } else if (!fingerprintManager.isHardwareDetected()) {
            finish();
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
        }
    }

    private boolean hasPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED;
    }

    private void checkPermissions() {
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Finger Print Permission not Granted!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKeys() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(FINGERPRINT_KEY,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean initCipher() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(FINGERPRINT_KEY,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }
}