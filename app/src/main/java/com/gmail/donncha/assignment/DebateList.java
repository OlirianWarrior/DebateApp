package com.gmail.donncha.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by donncha on 11/22/2015.
 */

// display all the created debates in a class which a user can select from to bring them to the
// debate of the selected item
public class DebateList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button bBack;
    String data;
    ArrayList<String> exList = new ArrayList<>();
    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<String> question = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debatelist);

        bBack = (Button) findViewById(R.id.bBack);
        ListView listview = (ListView) findViewById(R.id.listDebate);

        question = helper.queryColumn("question", "debate");

        // array adapter to store the data into the listview
        ArrayAdapter<String> myArrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, question);
        listview.setAdapter(myArrayAdapter);

        bBack.setOnClickListener(this);
        // the listview require an item clicked listner to return based on the item in the listview
        // clicked
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("Username");

        // bring menu screen up when user clicks this and save their username in the putExtra of
        // the currently logged in user
        switch (v.getId()) {

            case R.id.bBack:
                Intent i = new Intent(DebateList.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);

                break;
        }
    }

    // click method used for the arraylist to return the required data of the specific item
    // clicked in the list view
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String username = getIntent().getStringExtra("Username");

        String data =(String)parent.getItemAtPosition(position);

        Intent i = new Intent(DebateList.this, DisplayDebate.class);
        // put the question clicked and the logged in user into the putExtra to  be used in the
        // next class
        i.putExtra("Username", username);
        i.putExtra("Data", data);
        startActivity(i);
    }
}
