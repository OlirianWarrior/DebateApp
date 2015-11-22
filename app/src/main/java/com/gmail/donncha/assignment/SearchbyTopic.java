package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by donncha on 11/22/2015.
 */
public class SearchbyTopic extends AppCompatActivity implements View.OnClickListener {

    Button bBack;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbytopic);

        bBack = (Button) findViewById(R.id.bBack);

        bBack.setOnClickListener(this);
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
        }
    }
}
