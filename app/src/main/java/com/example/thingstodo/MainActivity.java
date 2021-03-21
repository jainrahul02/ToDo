package com.example.thingstodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

      static  ArrayList<String> notes = new ArrayList<>();
     static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.thingstodo", Context.MODE_PRIVATE);
        ListView listView  = findViewById(R.id.listView);

        // when the app starts we need to check if there was some saved notes or not
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet(" notes",null);
        if(set==null) {
            Toast.makeText(getApplicationContext(),"Add a note",Toast.LENGTH_SHORT).show();
        }else{
            notes = new ArrayList(set);
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        //single click operation
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //move to another activity
                Intent intent = new Intent(getApplicationContext(),NotesEditor.class);
                //to know which particular note was selected
                 intent.putExtra("noteId",i);
                 startActivity(intent);


            }
        });
        //DELETE A NOTE
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                //alert dialogue to ask the user if he wants to delete the particular selected note
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure??")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               notes.remove(itemToDelete);
                               arrayAdapter.notifyDataSetChanged();
                               // to save the changes in the notes

                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                .show();


                return true;
            }
        });
    }
    // MENU CODE (Add notes)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_notes,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.add_note) {
            //if item id matches move to the new note activity
            Intent intent = new Intent(getApplicationContext(),NotesEditor.class);
            startActivity(intent);
            return true;
        }
        return  false;
    }
}