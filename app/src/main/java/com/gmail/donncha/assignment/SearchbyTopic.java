package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by donncha on 11/22/2015.
 */

// allows users to select a topic to search by for the debates and select one from the listview
public class SearchbyTopic extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button bBack, bSearch;
    Spinner dropdown;
    ArrayList<String> exList = new ArrayList<>();
    ArrayList<String> question = new ArrayList<String>();
    DatabaseHelper helper = new DatabaseHelper(this);
    ListView listview;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbytopic);

        bBack =(Button) findViewById(R.id.bBack);
        bSearch =(Button) findViewById(R.id.bSearch);
        listview = (ListView) findViewById(R.id.listDebate);

        dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Politics", "Sports", "Video Games", "Contemporary Issues", "Music", "Science", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // set click listeners
        bBack.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // bring user back to the menu screen
            case R.id.bBack:

                String username = getIntent().getStringExtra("Username");
                Intent i = new Intent(SearchbyTopic.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);

                break;

            // query the database and retrieve relivant debates and use them to populate the
            // listview
            case R.id.bSearch:

                Spinner topic = (Spinner) findViewById(R.id.spinner1);

                String topicStr = topic.getSelectedItem().toString();

                question = helper.queryColumnWhere("question", "debate", topicStr, "topic");
                ArrayAdapter<String> myArrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, question);
                listview.setAdapter(myArrayAdapter);

                break;

        }
    }

    // handle the onclick of the item in the listview and store relivant data in the putExtra
    // and sent to the next activity
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String username = getIntent().getStringExtra("Username");

        String data =(String)parent.getItemAtPosition(position);

        Intent i = new Intent(SearchbyTopic.this, DisplayDebate.class);
        i.putExtra("Username", username);
        i.putExtra("Data", data);
        startActivity(i);
    }
}
