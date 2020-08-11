package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DownloadManagerActivity extends AppCompatActivity {
    TextInputEditText inputUrl;
    MaterialButton btnDownload;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        inputUrl = findViewById(R.id.input_url);
        btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = inputUrl.getText().toString();
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                downloadManager.enqueue(request);
            }
        });
    }
}