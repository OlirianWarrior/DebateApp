package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by donncha on 11/23/2015.
 */

// class to allow user to create a debate
public class CreateDebate extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button bBack, bCreate;
    EditText editDebateQuestion;
    Spinner dropdown;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // set layout
        setContentView(R.layout.activity_createdebate);

        bBack = (Button) findViewById(R.id.bBack);
        bCreate = (Button) findViewById(R.id.bCreateDebate);
        editDebateQuestion = (EditText) findViewById(R.id.editDebateQuestion);

        // initalise dropdown menu (spinner)
        dropdown = (Spinner) findViewById(R.id.spinner1);
        // create an array of topics which users can select from
        String[] items = new String[]{"Politics", "Sports", "Video Games", "Contemporary Issues", "Music", "Science", "Other"};
        // create adapter for the dropdown menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // set button listeners
        bBack.setOnClickListener(this);
        bCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("Username");

        switch (v.getId()) {
            // bring user back to the menu screen
            case R.id.bBack:

                username = getIntent().getStringExtra("Username");
                Intent i = new Intent(CreateDebate.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);
                finish();
                break;

            case R.id.bCreateDebate:

                EditText debateQuestion = (EditText) findViewById(R.id.editDebateQuestion);
                Spinner topic = (Spinner) findViewById(R.id.spinner1);

                String debateQuestionStr = debateQuestion.getText().toString();
                String topicStr = topic.getSelectedItem().toString();

                ArrayList<String> checkQuestion;
                // query database to find the question from the debate class where the
                // question is equals to that of the question currently open
                checkQuestion = helper.queryColumnWhere("question", "debate", debateQuestionStr,
                        "question");

                // if the above query has returned any results, it means that there is already
                // a debate asking that specific question created and will display a message
                // telling the user that that question has already been asked
                if (debateQuestionStr.length() <= 80)
                {
                    if (checkQuestion.isEmpty()) {
                        DebateInfo c = new DebateInfo();

                        c.setQuestion(debateQuestionStr);
                        c.setTopic(topicStr);
                        c.setUsername(username);

                        helper.insertDebate(c);

                        username = getIntent().getStringExtra("Username");
                        Intent j = new Intent(CreateDebate.this, Menu.class);
                        j.putExtra("Username", username);
                        startActivity(j);

                        Toast.makeText(getApplicationContext(),
                                "Debate Created", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "This question has already been asked", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Question length cannot be over 80 characters long", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
