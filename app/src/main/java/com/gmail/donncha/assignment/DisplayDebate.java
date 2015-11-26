package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by donncha on 11/23/2015.
 */
public class DisplayDebate extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    TextView txtQuestion, txtYesVote, txtNoVote;
    Button bBack, bSubmitComment, bYesVote, bNoVote;
    EditText editComment;
    ArrayList<String> commentData = new ArrayList<String>();
    ArrayList<String> yesVoteData = new ArrayList<String>();
    ArrayList<String> noVoteData = new ArrayList<String>();
    ListView listview;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydebate);

        String data = getIntent().getStringExtra("Data");

        String Id = getIntent().getStringExtra("Id");
        bBack = (Button)findViewById(R.id.bBack);
        bNoVote = (Button)findViewById(R.id.bNoVote);
        bYesVote  = (Button)findViewById(R.id.bYesVote);
        txtNoVote = (TextView)findViewById(R.id.txtnovote);
        txtYesVote = (TextView)findViewById(R.id.txtyesvote);
        bSubmitComment = (Button)findViewById(R.id.bSubmitComment);
        editComment = (EditText)findViewById(R.id.editComment);
        listview = (ListView)findViewById(R.id.ListComment);

        String question = getIntent().getStringExtra("QuestionData");

        TextView tv = (TextView) findViewById(R.id.txtQuestion);
        tv.setText("Question:" + data);

        commentData = helper.queryColumnWhere("commentdata", "comment", data, "question");
        yesVoteData = helper.queryColumnWhere("yes", "debate", data, "question");
        noVoteData = helper.queryColumnWhere("no", "debate", data, "question");

        String yesVote = yesVoteData.get(0);
        String noVote = noVoteData.get(0);
        //String noVote =
        txtYesVote.setText(yesVote);
        txtNoVote.setText(noVote);

        ArrayAdapter<String> myArrayAdapter =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, commentData);
        listview.setAdapter(myArrayAdapter);

        bBack.setOnClickListener(this);
        bSubmitComment.setOnClickListener(this);
        bYesVote.setOnClickListener(this);
        bNoVote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("Username");
        String data = getIntent().getStringExtra("Data");

        switch (v.getId()) {
            case R.id.bBack:

                Intent i = new Intent(DisplayDebate.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);
                break;

            case R.id.bYesVote:

                //commentData = helper.queryColumnWhere("commentdata", "comment", data, "question");

                //if user has voted, make new function to handle taking in two where clauses
                ArrayList<String> hasVoted = new ArrayList<String>();

                hasVoted = helper.queryColumnWhereAnd("username", "question", "hasvoted",
                        username, data );

                if(hasVoted.isEmpty())
                {
                    ArrayList<String> yesVoteIncrement = new ArrayList<String>();
                    yesVoteIncrement = helper.queryColumnWhere("yes", "debate", data, "question");
                    String yesVoteIncrementStr = yesVoteData.get(0);
                    int incrementYesVote = Integer.parseInt(yesVoteIncrementStr) + 1;
                    String newYesValue = String.valueOf(incrementYesVote);

                    helper.insertVoteToDebate("yes", newYesValue, data);
                    Log.w("it has been created", newYesValue);

                    DebateInfo cur = new DebateInfo();

                    cur.setUsername(username);
                    cur.setQuestion(data);

                    helper.insertVote(cur);
                }

                break;

            case R.id.bNoVote:

                ArrayList<String> noVoteIncrement = new ArrayList<String>();
                noVoteIncrement = helper.queryColumnWhere("no", "debate", data, "question");
                String noVoteIncrementStr = noVoteData.get(0);
                int incrementNoVote =  Integer.parseInt(noVoteIncrementStr) + 1;
                String newNoValue = String.valueOf(incrementNoVote);

                helper.insertVoteToDebate("no", newNoValue, data );
                Log.w("it has been created", newNoValue);

                break;

            case R.id.bSubmitComment:

                EditText debateComment = (EditText) findViewById(R.id.editComment);

                String debateQuestionStr = debateComment.getText().toString();

                if (debateQuestionStr != "" || debateQuestionStr != "Enter comment here...") {
                    CommentInfo c = new CommentInfo();
                    c.setUsername(username);
                    c.setCommentData(debateQuestionStr);
                    c.setQuestion(data);
                    helper.insertComment(c);
                    commentData = helper.queryColumnWhere("commentdata", "comment", data, "question");
                    ArrayAdapter<String> myArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, commentData);
                    listview.setAdapter(myArrayAdapter);

                    editComment = (EditText) findViewById(R.id.editComment);
                    editComment.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Please enter something into the comment before submitting", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}