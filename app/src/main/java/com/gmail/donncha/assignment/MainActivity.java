package com.gmail.donncha.assignment;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout, bRegister, bLogin;
    EditText editUsername, editPassword;
    public static String loginUsername;

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        // cast to Button and assign to specified variable
        bRegister = (Button) findViewById(R.id.bRegister);
        bLogin = (Button) findViewById(R.id.bLogin);

        // assign newly assigned buttons to click listeners
        bRegister.setOnClickListener(this);
        bLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bLogin:

                EditText a = (EditText)findViewById(R.id.editUsername);
                String str = a.getText().toString();
                EditText b = (EditText)findViewById(R.id.editPassword);
                String pass = b.getText().toString();

                // query database to fetch the password of the enetered password and the password
                // that matches the entered user, if not then a toast will trigger telling the
                // user that they have not enetered the correct information
                String password = helper.searchPass(str);
                if(pass.equals(password))
                {
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    i.putExtra("Username", str);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Password or Username invalid!", Toast.LENGTH_LONG).show();
                    editUsername.setText("");
                    editPassword.setText("");
                }
                break;

            case R.id.bRegister:

                // open intent to allow user to proceed to register
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
                break;
        }
    }
}
