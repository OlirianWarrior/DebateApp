package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by donncha on 11/22/2015.
 */
public class Menu extends AppCompatActivity implements View.OnClickListener {

    Button bAccount, bSearch, bSearch2, bCreateDebate;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String username = getIntent().getStringExtra("Username");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //String username = getIntent().getStringExtra("Username");

        bAccount =(Button)findViewById(R.id.bAccount);
        bSearch =(Button)findViewById(R.id.bSearch);
        bSearch2 =(Button)findViewById(R.id.bSearch2);
        bCreateDebate =(Button)findViewById(R.id.bCreateDebate);

        bAccount.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        bSearch2.setOnClickListener(this);
        bCreateDebate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String username;

        // open the various screens, storing the username in the putExtra and passing it onto
        // any of the clicked screens
        switch (v.getId()) {

            case R.id.bAccount:

                username = getIntent().getStringExtra("Username");
                Intent i = new Intent(Menu.this, Account.class);
                i.putExtra("Username", username);
                startActivity(i);

                break;

            case R.id.bSearch:

                username = getIntent().getStringExtra("Username");
                Intent k = new Intent(Menu.this, DebateList.class);
                k.putExtra("Username", username);
                startActivity(k);

                break;

            case R.id.bSearch2:

                username = getIntent().getStringExtra("Username");
                Intent j = new Intent(Menu.this, SearchbyTopic.class);
                j.putExtra("Username", username);
                startActivity(j);

                break;

            case R.id.bCreateDebate:

                username = getIntent().getStringExtra("Username");
                Intent l = new Intent(Menu.this, CreateDebate.class);
                l.putExtra("Username", username);
                startActivity(l);
        }
    }
}
