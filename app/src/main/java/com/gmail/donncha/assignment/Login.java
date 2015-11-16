package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by donncha on 11/14/2015.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText editUsername, editPassword;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        bLogout = (Button) findViewById(R.id.bLogout);

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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(Login.this, MainActivity.class));
            break;
        }
    }
}
