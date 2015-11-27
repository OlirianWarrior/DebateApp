package com.gmail.donncha.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by donncha on 11/27/2015.
 */

// splash screen that is to display for 3 seconds
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread myThread = new Thread()
        {
            @Override
        // splash screen run code from youtube.com/watch?v=p_aDv7Mbs3g
        public void run()
            {
                try {
                    // sleep for 3 seconds
                    sleep(3000);
                    // open main intent
                    Intent startMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startMainScreen);
                    // close screen
                    finish();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}

