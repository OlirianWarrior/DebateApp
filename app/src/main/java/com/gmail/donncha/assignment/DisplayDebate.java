package com.gmail.donncha.assignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by donncha on 11/23/2015.
 */
public class DisplayDebate extends AppCompatActivity implements View.OnClickListener {


    TextView txtQuestion;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydebate);

        String Id = getIntent().getStringExtra("Id");

        TextView tv = (TextView)findViewById(R.id.txtQuestion);
        tv.setText(Id);
    }


        @Override
    public void onClick(View v) {

    }
}
