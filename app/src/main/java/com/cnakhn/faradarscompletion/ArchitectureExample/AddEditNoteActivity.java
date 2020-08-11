package com.cnakhn.faradarscompletion.ArchitectureExample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;

public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_ID = "com.cnakhn.faradarscompletion.ArchitectureExample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.cnakhn.faradarscompletion.ArchitectureExample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.cnakhn.faradarscompletion.ArchitectureExample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.cnakhn.faradarscompletion.ArchitectureExample.EXTRA_PRIORITY";
    private EditText inputNoteName, inputNote;
    private ImageButton btnNoteClose, btnNoteSave;
    private NumberPicker priorityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            inputNoteName.setText(intent.getStringExtra(EXTRA_TITLE));
            inputNote.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

    }

    private void initViews() {
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));
        inputNoteName = findViewById(R.id.input_note_name);
        inputNote = findViewById(R.id.input_note);
        btnNoteClose = findViewById(R.id.btn_close_note);
        btnNoteClose.setOnClickListener(this);
        btnNoteSave = findViewById(R.id.btn_save_note);
        btnNoteSave.setOnClickListener(this);
        priorityPicker = findViewById(R.id.picker_note_priority);
        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnNoteSave)) {
            saveNote();
        }

        if (v.equals(btnNoteClose)) {
            finish();
        }
    }

    private void saveNote() {
        String noteName = inputNoteName.getText().toString().trim();
        String note = inputNote.getText().toString().trim();
        int priority = priorityPicker.getValue();

        if (noteName.isEmpty() | note.isEmpty()) {
            Toast.makeText(this, "Please insert Name and note to save the note...", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, noteName);
        data.putExtra(EXTRA_DESCRIPTION, note);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
}