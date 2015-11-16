package com.gmail.donncha.assignment;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by donncha on 11/15/2015.
 */
public class UserLocalStore {

    public static final String SP_Name = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name, 0);

    }

    public void storeUserData(User user)
    {
        // allow editing of the sharedpreferance database
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getLoggedInUser()
    {
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(username, password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn)
    {
        //if the user is logged in set to true if not set to false
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn()
    {
        if(userLocalDatabase.getBoolean("loggedIn", false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void clearUserData()
    {
        //clear everything that is inside the shared preferance
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
