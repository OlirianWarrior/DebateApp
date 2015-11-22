package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by donncha on 11/14/2015.
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper helper = new DatabaseHelper(this);

    Button regRegister;
    EditText regUsername, regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = (EditText) findViewById(R.id.regUsername);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regRegister = (Button) findViewById(R.id.regRegister);

        regRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.regRegister:


                EditText username = (EditText)findViewById(R.id.regUsername);
                EditText password = (EditText)findViewById(R.id.regPassword);

                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                UserInfo c = new UserInfo();

                c.setUsername(usernameStr);
                c.setPassword(passwordStr);

                helper.insertUser(c);

                 //helped from:
                 // http://code.tutsplus.com/tutorials/android-user-interface-design-password-confirmation--mobile-7428

                /*if(!pass1str.equals(pass2str))
                {
                    Toast.makeText(getApplicationContext(),
                            "Passwords did not match!", Toast.LENGTH_LONG).show();
                }*/


                //String username =regUsername.getText().toString();
                //String password =regPassword.getText().toString();

              //  User registeredData = new User(usernameStr, passwordStr);

                Toast.makeText(getApplicationContext(),
                        "Registered Successfully", Toast.LENGTH_LONG).show();



                startActivity(new Intent(Register.this, MainActivity.class));
             break;
        }
    }
}
