package com.example.thingstodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditor extends AppCompatActivity {
     int noteId;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
         noteId = intent.getIntExtra("noteId",-1);
        if(noteId != -1)
        {  //to get the appropriate string
            editText.setText(MainActivity.notes.get(noteId));
        }else
        {
            //add a new note
            MainActivity.notes.add(" ");
            noteId = MainActivity.notes.size()-1; // 0 based index
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get access to the notes array and apply changes at the index(notesId)
                 MainActivity.notes.set(noteId,String.valueOf(charSequence));
                 //tell the arrayAdapter about the changes
                 MainActivity.arrayAdapter.notifyDataSetChanged();
                 //to save the changes done in the notes
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.thingstodo", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}