package com.gmail.donncha.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        ArrayAdapter<String> myArrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, question);
        listview.setAdapter(myArrayAdapter);

        bBack.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("Username");

        switch (v.getId()) {

            case R.id.bBack:
                Intent i = new Intent(DebateList.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String username = getIntent().getStringExtra("Username");

        Intent i = new Intent(DebateList.this, DisplayDebate.class);
        i.putExtra("Username", username);
        //String question = exList.get(position);
        //i.putExtra("QuestionData", question);
        //i.putExtra("Position", position);
        //i.putExtra("Id", id);
        startActivity(i);
    }
}
