package com.cnakhn.faradarscompletion.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FilesActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText inputFileName, inputContent;
    MaterialButton btnCreateFile, btnLoadFile;
    TextView tvFile;
    CoordinatorLayout layoutFilesSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        initViews();
    }

    private void initViews() {
        inputFileName = findViewById(R.id.input_file_name);
        inputContent = findViewById(R.id.input_content);
        btnCreateFile = findViewById(R.id.btn_create_file);
        btnCreateFile.setOnClickListener(this);
        btnLoadFile = findViewById(R.id.btn_load_file);
        btnLoadFile.setOnClickListener(this);
        layoutFilesSnackBar = findViewById(R.id.layout_files_snackbar);
        tvFile = findViewById(R.id.tv_file);
    }

    @Override
    public void onClick(View v) {
        String fileName = inputFileName.getText().toString();
        String content = inputContent.getText().toString();
        if (v.equals(btnCreateFile)) {
            createInternalFile(fileName + ".txt", content);
        }

        if (v.equals(btnLoadFile)) {
            loadInternalFile(fileName + ".txt");
        }
    }

    private void createInternalFile(String fileName, String content) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
            Snackbar.make(layoutFilesSnackBar, "File " + fileName + " Created/Saved!", Snackbar.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInternalFile(String fileName) {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            tvFile.setText(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}