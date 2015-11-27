package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by donncha on 11/14/2015.
 */

// allow a new user to fill in relivant information and have their account be created
public class Register extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button regRegister, bBack;
    EditText regUsername, regName, regEmail, regPassword, regConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = (EditText) findViewById(R.id.regUsername);
        regName = (EditText) findViewById(R.id.regName);
        regEmail = (EditText) findViewById(R.id.regEmail);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regConfPassword = (EditText) findViewById(R.id.regConfPassword);
        regRegister = (Button) findViewById(R.id.regRegister);
        bBack = (Button) findViewById(R.id.bBack);

        // set buttons to click listeners
        regRegister.setOnClickListener(this);
        bBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.regRegister:

                // take data eneted into each field
                EditText username = (EditText)findViewById(R.id.regUsername);
                EditText name = (EditText)findViewById(R.id.regName);
                EditText email = (EditText)findViewById(R.id.regEmail);
                EditText password = (EditText)findViewById(R.id.regPassword);
                EditText confpassword = (EditText)findViewById(R.id.regConfPassword);

                // convert the data from each field into a string
                String usernameStr = username.getText().toString();
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String confpasswordStr = confpassword.getText().toString();

                ArrayList<String> checkUser = new ArrayList<String>();

                // check if a user with that name already exists, it must be unique so
                // present a toast if and do not proceed if a user with the entered username
                // already exists
                checkUser = helper.queryColumnWhere("username", "users", usernameStr, "username");
                if(checkUser.isEmpty()) {

                    // check to make sure all the fields have information entered in them,
                    // none can be left blank to present a toast prompting the user to finish
                    // filling in the information if any or left blank, if not proceed below
                    if (!usernameStr.equals("") && !nameStr.equals("") && !emailStr.equals("") &&
                            !passwordStr.equals("") && !confpasswordStr.equals("")) {

                        // check if the entered password and confirmation password are both
                        // the same, prompt the user that they both do not match if not, proceed
                        // to eneter the information into the userdata and create a new user
                        if (passwordStr.equals(confpasswordStr)) {
                            UserInfo c = new UserInfo();

                            // set the user data
                            c.setUsername(usernameStr);
                            c.setName(nameStr);
                            c.setEmail(emailStr);
                            c.setUsername(usernameStr);
                            c.setPassword(passwordStr);
                            c.setConfpassword(passwordStr);

                            // insert into the data base using the interUser method
                            helper.insertUser(c);

                            Toast.makeText(getApplicationContext(),
                                    "Registered Successfully", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Passwords did not match!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please don't leave any of the fields blank", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Username is already taken", Toast.LENGTH_LONG).show();
                }

             break;

            case R.id.bBack:
                Intent j = new Intent(Register.this, MainActivity.class);
                startActivity(j);
                finish();
                break;

        }
    }
}
