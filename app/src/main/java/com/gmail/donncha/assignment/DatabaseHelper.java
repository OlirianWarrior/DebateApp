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

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "DebateApp.db";

    private static final String TABLE_NAME = "users";
    //private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONFPASSWORD = "confpassword";

    private static final String TABLE_DEBATE = "debate";
    private static final String COLUMN_ID = "debate_id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_TOPIC = "topic";
    private static final String COLUMN_YES = "yes";
    private static final String COLUMN_NO = "no";
    //private static final String COLUMN_DUSERNAME = "username";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table if not exists users  (username text primary key not null , " +
            "name text not null , email text not null , password text not null , confpassword);";

    private static final String TABLE_CREATE2 = "create table if not exists debate (debate_id int primary key not null , " +
            "question text not null , topic text not null , yes int null, no int null , username text not null , " +
            "FOREIGN KEY ("+COLUMN_USERNAME+") REFERENCES "+TABLE_NAME+"("+COLUMN_USERNAME+"));";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db = db;
    }

    public void insertUser(UserInfo c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);

        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_CONFPASSWORD, c.getConfpassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertDebate(DebateInfo c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from debate";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_QUESTION, c.getQuestion());
        values.put(COLUMN_TOPIC, c.getTopic());
        values.put(COLUMN_YES, c.getYesVote());
        values.put(COLUMN_NO, c.getNoVote());
        values.put(COLUMN_USERNAME, c.getUsername());

        db.insert(TABLE_DEBATE, null, values);
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

    public ArrayList<String> queryColumn()
    {
        db = this.getReadableDatabase();

        ArrayList<String> arrayList = new ArrayList<String>();

        String query = "select question from debate";
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
            arrayList.add(question);
        }
        return arrayList;
    }

    public ArrayList<String> queryColumnWhere(String topic)
    {
        db = this.getReadableDatabase();
        //String query = String.format("select username, name, email from %s WHERE username='%s'", TABLE_NAME, topic);

        ArrayList<String> arrayList = new ArrayList<String>();

        String query = String.format("select question from debate where topic ='%s'", topic);
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
            arrayList.add(question);
        }
        return arrayList;
    }

    public String searchUser(String username) throws SQLException
    {
        db = this.getReadableDatabase();
        //String query = "select username, name, email from "+TABLE_NAME+" WHERE username='"+username+"'";
        String query = String.format("select username, name, email from %s WHERE username='%s'", TABLE_NAME, username);


        Cursor cursor = db.rawQuery(query, null);
        return cursor.toString();
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

        String query2 = "DROP TABLE IF EXISTS "+TABLE_DEBATE;
        db.execSQL(query2);
        this.onCreate(db);
    }

    public class QueryInfo {
        public String username;
        public String name;
        public String email;

        public QueryInfo(String username, String name, String email){
            this.username = username;
            this.name = name;
            this.email = email;
        }
    }
}
