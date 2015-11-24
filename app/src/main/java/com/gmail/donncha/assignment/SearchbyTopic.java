package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by donncha on 11/22/2015.
 */
public class SearchbyTopic extends AppCompatActivity implements View.OnClickListener {

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



        bBack.setOnClickListener(this);
        bSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bBack:

                String username = getIntent().getStringExtra("Username");
                Intent i = new Intent(SearchbyTopic.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);

                break;

            case R.id.bSearch:

                Spinner topic = (Spinner) findViewById(R.id.spinner1);

                String topicStr = topic.getSelectedItem().toString();

                question = helper.queryColumnWhere(topicStr);
                ArrayAdapter<String> myArrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, question);
                listview.setAdapter(myArrayAdapter);

                break;

        }
    }
}
