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

/**
 * Created by donncha on 11/23/2015.
 */
public class CreateDebate extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button bBack, bCreate;
    EditText editDebateQuestion;
    Spinner dropdown;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdebate);

        bBack = (Button) findViewById(R.id.bBack);
        bCreate = (Button) findViewById(R.id.bCreateDebate);
        editDebateQuestion = (EditText) findViewById(R.id.editDebateQuestion);

        dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Politics", "Sports", "Video Games", "Contemporary Issues", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        bBack.setOnClickListener(this);
        bCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("Username");

        switch (v.getId()) {
            case R.id.bBack:

                username = getIntent().getStringExtra("Username");
                Intent i = new Intent(CreateDebate.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);
                break;

                case R.id.bCreateDebate:

                    //EditText debateQuestion = (EditText) findViewById(R.id.editDebateQuestion);
                    EditText debateQuestion = (EditText) findViewById(R.id.editDebateQuestion);
                    Spinner topic = (Spinner) findViewById(R.id.spinner1);

                    String debateQuestionStr = debateQuestion.getText().toString();
                    String topicStr = topic.getSelectedItem().toString();

                    DebateInfo c = new DebateInfo();

                    c.setQuestion(debateQuestionStr);
                    c.setTopic(topicStr);
                    c.setUsername(username);

                    helper.insertDebate(c);

                    username = getIntent().getStringExtra("Username");
                    Intent j = new Intent(CreateDebate.this, Menu.class);
                    j.putExtra("Username", username);
                    startActivity(j);
                    break;
        }
    }
}
