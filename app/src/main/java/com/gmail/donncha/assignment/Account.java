package com.gmail.donncha.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by donncha on 11/14/2015.
 */
// account screen to display any information that is stored on the user

public class Account extends AppCompatActivity implements View.OnClickListener {

    // create database object
    DatabaseHelper helper = new DatabaseHelper(this);

    Button bLogout, bBack;
    EditText editUsername, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // initalise variables
        String name = null;
        String email = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Create new string and initialise it with the value of the "Username" declared earlier
        String username = getIntent().getStringExtra("Username");

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        bLogout = (Button) findViewById(R.id.bLogout);
        bBack = (Button) findViewById(R.id.bBack);
        TextView txtName = (TextView)findViewById(R.id.txtName);
        TextView txtEmail = (TextView)findViewById(R.id.txtEmail);
        TextView txtUsername = (TextView)findViewById(R.id.txtUsername);

        // set text to the currently logged in username
        txtUsername.setText(username);

        // set button listeners
        bLogout.setOnClickListener(this);
        bBack.setOnClickListener(this);

        // query to find to retrieve information relivant to the user
        DatabaseHelper.QueryInfo info = helper.searchUser("users", username);

        // retrieve infromation from the arraylist
        name = info.name;
        email = info.email;

        txtName.setText(name);
        txtEmail.setText(email);

    }


    @Override
    public void onClick(View v)
    {
        // get the logged in username
        String username = getIntent().getStringExtra("Username");

        switch (v.getId()){

            //  bring back to login screen
            case R.id.bLogout:

                Intent i = new Intent(Account.this, MainActivity.class);
                startActivity(i);
            break;

            // bring back to previous screen (menu)
            case R.id.bBack:

                Intent j = new Intent(Account.this, Menu.class);
                j.putExtra("Username", username);
                startActivity(j);
            break;
        }
    }
}
