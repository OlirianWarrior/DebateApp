package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by donncha on 11/14/2015.
 */

public class Account extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button bLogout;
    EditText editUsername, editPassword;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Create new string and initialise it with the value of the "Username" declared earlier
        String username = getIntent().getStringExtra("Username");

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        bLogout = (Button) findViewById(R.id.bLogout);
        TextView tv = (TextView)findViewById(R.id.tvUserview);
        tv.setText("Welcome " + username);

        bLogout.setOnClickListener(this);

        //give new local store contents
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(authenticate() == true)
        {

        }

    }

    private boolean authenticate()
    {
        //tell us if a user is logged in or out
        return userLocalStore.getUserLoggedIn();

    }

    public void displayUserDetails()
    {
        //if authenticate is correct, display that user's details
        User user = userLocalStore.getLoggedInUser();

        editUsername.setText(user.username);
        editPassword.setText(user.password);

    }

    public void getQuery()
    {

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
        }
    }
}
