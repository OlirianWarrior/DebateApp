package com.gmail.donncha.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout, bRegister, bLogin;
    EditText editUsername, editPassword;

    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // cast to EditText and assign to specified variable
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        // cast to Button and assign to specified variable
        bRegister = (Button) findViewById(R.id.bRegister);
        bLogin = (Button) findViewById(R.id.bLogin);
        // assign newly assigned buttons to click listeners
        bRegister.setOnClickListener(this);
        bLogin.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bLogin:

                User user = new User(null, null);
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);

                startActivity(new Intent(MainActivity.this, Login.class));
                //Intent intent = new Intent(MainActivity.this, Login.class);
                break;

            case R.id.bRegister:
                //Toast.makeText(getApplicationContext(),
                        //"Top Button clicked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, Register.class));
                break;
        }
    }
}
