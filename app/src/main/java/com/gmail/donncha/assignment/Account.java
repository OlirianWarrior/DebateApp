package com.gmail.donncha.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by donncha on 11/14/2015.
 */

public class Account extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button bLogout, bBack;
    EditText editUsername, editPassword;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        TextView tv = (TextView)findViewById(R.id.tvUserview);
        TextView txtName = (TextView)findViewById(R.id.txtName);
        TextView txtEmail = (TextView)findViewById(R.id.txtEmail);

        tv.setText("Welcome " + username);

        bLogout.setOnClickListener(this);
        bBack.setOnClickListener(this);

        //give new local store contents
        userLocalStore = new UserLocalStore(this);

        //Cursor cursor = helper.select("select * from users");

        name = helper.searchUser(username).name;
        email = helper.searchUser(username).email;

        txtName.setText(name);
        txtEmail.setText(email);
    }

    public void displayUserDetails()
    {
        //if authenticate is correct, display that user's details
        User user = userLocalStore.getLoggedInUser();

        editUsername.setText(user.username);
        editPassword.setText(user.password);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(Account.this, MainActivity.class));
            break;

            case R.id.bBack:
                String username = getIntent().getStringExtra("Username");
                Intent i = new Intent(Account.this, Menu.class);
                i.putExtra("Username", username);
                startActivity(i);
            break;
        }
    }
}
