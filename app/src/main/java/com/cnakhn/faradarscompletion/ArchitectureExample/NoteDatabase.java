package com.cnakhn.faradarscompletion.ArchitectureExample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase database) {
            noteDao = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Note name will be here", "The number top right of the note name is your note priority.", 1));
            noteDao.insert(new Note("Slide left/right", "Slide left/right the note your want to delete it!", 2));
            noteDao.insert(new Note("Delete all notes", "Delete all of your notes with Trash Can icon at the top.", 3));
            noteDao.insert(new Note("Write something...", "Start taking notes. enjoy!", 4));
            return null;
        }
    }
}
