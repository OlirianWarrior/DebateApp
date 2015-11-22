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

    Button bAccount, bSearch, bSearch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bAccount = (Button) findViewById(R.id.bAccount);
        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch2 = (Button) findViewById(R.id.bSearch2);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.bAccount:

                startActivity(new Intent(Menu.this, Account.class));

                /*Intent i = new Intent(Menu.this, Account.class);
                //i.putExtra("Username", str);
                startActivity(i);*/

                break;

            case R.id.bSearch:
                startActivity(new Intent(Menu.this, Account.class));

                /*Intent j = new Intent(Menu.this, Account.class);
                //i.putExtra("Username", str);
                startActivity(j);*/

                break;

            case R.id.bSearch2:

                startActivity(new Intent(Menu.this, SearchbyTopic.class));

                /*Intent k = new Intent(Menu.this, SearchbyTopic.class);
                //i.putExtra("Username", str);
                startActivity(k);*/

                break;
        }
    }
}
