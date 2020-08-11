package com.cnakhn.faradarscompletion.ArchitectureExample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cnakhn.faradarscompletion.Utils;
import com.cnakhn.faradarscompletion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int ADD_NOTE_REQUEST_CODE = 654;
    private static final int EDIT_NOTE_REQUEST_CODE = 655;
    private NoteViewModel noteViewModel;
    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initViews();

        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update RecyclerView
                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Delete note?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(NotesActivity.this, "Note deleted.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });

                builder.show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Note note) {
                Intent intent = new Intent(NotesActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
            }
        });
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));
        setTitle("Notes");
        noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteAdapter = new NoteAdapter();
        btnAddNote = findViewById(R.id.btn_add_note);
        btnAddNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAddNote)) {
            Intent intent = new Intent(NotesActivity.this, AddEditNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note saved.", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated.", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Delete all notes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Delete all notes?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteViewModel.deleteAllNotes();
                        Toast.makeText(NotesActivity.this, "All notes deleted.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
                return false;
            }
        }).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_delete, null))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}