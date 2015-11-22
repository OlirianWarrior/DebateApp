package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by donncha on 11/22/2015.
 */
public class DebateList extends AppCompatActivity implements View.OnClickListener {

    Button bBack;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debatelist);

        bBack = (Button) findViewById(R.id.bBack);

        bBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bBack:
                String username = getIntent().getStringExtra("Username");
                Intent i = new Intent(DebateList.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);

                //startActivity(new Intent(DebateList.this, Menu.class));
                break;

        }
    }
}
