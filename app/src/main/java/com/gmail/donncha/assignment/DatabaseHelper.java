package com.gmail.donncha.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donncha on 11/16/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "DebateApp.db";
    private static final String TABLE_NAME = "users";
    //private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONFPASSWORD = "confpassword";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (username text primary key not null , " +
            "name text not null , email text not null , password text not null , confpassword);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertUser(UserInfo c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        //int count = 1000;

        //values.put(COLUMN_ID, count);
        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_CONFPASSWORD, c.getConfpassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchPass(String username)
    {
        db = this.getReadableDatabase();
        String query = "select username, password from "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "invalid username or password";
        if(cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);

                if(a.equals(username))
                {
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return b;
    }

    public UserDataInfo searchUser(String username) throws SQLException
    {
        db = this.getReadableDatabase();
        //String query = "select username, name, email from "+TABLE_NAME+" WHERE username='"+username+"'";
        String query = String.format("select username, name, email from %s WHERE username='%s'", TABLE_NAME, username);


        Cursor cursor = db.rawQuery(query, null);

        //cursor.get

        //String[i] data = new String[i];
        //int i = 0;

        String rows[] = new String[cursor.getCount()];
        for(int i = 0; i < cursor.getCount(); i++)
        {
            rows[i] = cursor.getString(0);
        }

        //while(cursor.moveToNext())
        //{

        //}

        /*String data;
        data = "no data found";
        if(cursor.moveToFirst())
        {
                data = cursor.getString(index_col);
        }*/
        return rows;
    }

    public Cursor select(String query)
    {
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor select(String query, List<String> params)
    {
        Cursor cursor = db.rawQuery(query, (String[])params.toArray());
        cursor.moveToFirst();
        return  cursor;
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerison)
    {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public class UserDataInfo{
        public String username;
        public String name;
        public String email;

        public UserDataInfo(String username, String name, String email){
            this.username = username;
            this.name = name;
            this.email = email;
        }
    }
}
