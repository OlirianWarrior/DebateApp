package com.gmail.donncha.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donncha on 11/16/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 16;
    private static final String DATABASE_NAME = "DebateApp.db";

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONFPASSWORD = "confpassword";

    private static final String TABLE_DEBATE = "debate";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_TOPIC = "topic";
    private static final String COLUMN_YES = "yes";
    private static final String COLUMN_NO = "no";

    private static final String TABLE_COMMENT = "comment";
    private static final String COLUMN_COMMENTDATA = "commentdata";
    private static final String COLUMN_COMMENT_ID = "comment_id";

    private static final String TABLE_HASVOTED = "hasvoted";
    private static final String COLUMN_VOTE_ID = "vote_id";

    SQLiteDatabase db;

    // set strings to create and populate the databases
    private static final String TABLE_CREATE_USERS = "create table if not exists "+TABLE_NAME+"  " +
            "("+COLUMN_USERNAME+" text primary key not null , " +
            ""+COLUMN_NAME+" text not null ," +
            ""+COLUMN_EMAIL+" text not null , " +
            ""+COLUMN_PASSWORD+" text not null , " +
            ""+COLUMN_CONFPASSWORD+" text not null);";

    private static final String TABLE_CREATE_DEBATE = "create table if not exists " +
            ""+TABLE_DEBATE+"" +
            " ("+COLUMN_QUESTION+" text primary key not null , " +
            ""+COLUMN_TOPIC+" text not null , " +
            ""+COLUMN_YES+" int null, " +
            ""+COLUMN_NO+" int null , " +
            ""+COLUMN_USERNAME+" text not null , " +
            "FOREIGN KEY ("+COLUMN_USERNAME+") REFERENCES "+TABLE_NAME+"("+COLUMN_USERNAME+"));";

    private static final String TABLE_CREATE_COMMENTS = "create table if not exists " +
            ""+TABLE_COMMENT+" " +
            "("+COLUMN_COMMENT_ID+" int primary key not null , " +
            ""+COLUMN_COMMENTDATA+" text not null , " +
            ""+COLUMN_USERNAME+" text not null , " +
            ""+COLUMN_QUESTION+" text not null , " +
            "FOREIGN KEY ("+COLUMN_USERNAME+") REFERENCES "+TABLE_NAME+"("+COLUMN_USERNAME+") , " +
            "FOREIGN KEY ("+COLUMN_QUESTION+") REFERENCES "+TABLE_DEBATE+"("+COLUMN_USERNAME+"));";

    private static final String TABLE_CREATE_HASVOTED = "create table if not exists " +
            ""+TABLE_HASVOTED+" " +
            "("+COLUMN_VOTE_ID+" int primary key not null , " +
            ""+COLUMN_USERNAME+" text not null , " +
            ""+COLUMN_QUESTION+" text not null ," +
            "FOREIGN KEY ("+COLUMN_USERNAME+") REFERENCES "+TABLE_NAME+"("+COLUMN_USERNAME+") , " +
            "FOREIGN KEY ("+COLUMN_QUESTION+") REFERENCES "+TABLE_DEBATE+"("+COLUMN_USERNAME+"));";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // using strings above create through execSQL
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_DEBATE);
        db.execSQL(TABLE_CREATE_COMMENTS);
        db.execSQL(TABLE_CREATE_HASVOTED);
        this.db = db;
    }

    // method for inserting a user into the appropriate database taking in strings from the
    // userinfo class
    public void insertUser(UserInfo c)
    {
        // allow data to be written into the database
        db = this.getWritableDatabase();
        // used for storing the set of the values that are going to be inserted into the database
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);

        // take in values to insert into the database
        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_CONFPASSWORD, c.getConfpassword());

        // insert into the database
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // method for inserting a comment into the appropriate database taking in strings from the
    // CommentInfo data structure
    public void insertComment(CommentInfo c)
    {
        db = this.getReadableDatabase();
        // used for storing the set of the values that are going to be inserted into the database
        ContentValues values = new ContentValues();

        String query = "select * from comment";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_COMMENT_ID, count);
        values.put(COLUMN_COMMENTDATA, c.getCommentData());
        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_QUESTION, c.getQuestion());

        db.insert(TABLE_COMMENT, null, values);
    }

    // method for inserting a debate into the appropriate database taking in strings from the
    // DebateInfo data structure
    public void insertDebate(DebateInfo c)
    {
        db = this.getWritableDatabase();
        // used for storing the set of the values that are going to be inserted into the database
        ContentValues values = new ContentValues();

        String query = "select * from debate";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        //values.put(COLUMN_ID, count);
        values.put(COLUMN_QUESTION, c.getQuestion());
        values.put(COLUMN_TOPIC, c.getTopic());
        values.put(COLUMN_YES, c.getYesVote());
        values.put(COLUMN_NO, c.getNoVote());
        values.put(COLUMN_USERNAME, c.getUsername());

        db.insert(TABLE_DEBATE, null, values);
        db.close();
    }

    // method for inserting a vote into the appropriate database taking in strings from the
    // DebateInfo data structure, this was reused as they already share the same fields
    public void insertVote(DebateInfo c)
    {
        db = this.getWritableDatabase();
        // used for storing the set of the values that are going to be inserted into the database
        ContentValues values = new ContentValues();

        String query = "select * from hasvoted";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_VOTE_ID, count);
        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_QUESTION, c.getQuestion());

        db.insert(TABLE_HASVOTED, null, values);
        db.close();
    }

    // method for inserting a vote into a debate taking in the vote value and the debate in which
    // to be updated
    public void insertVoteToDebate(String vote, String changeTo, String question)
    {
        db = this.getWritableDatabase();
        // used for storing the set of the values that are going to be inserted into the database
        ContentValues values = new ContentValues();

        String query = String.format("update debate set " + vote + "=" + changeTo + "" +
                " where question = '%s';", question);

        db.execSQL(query);
    }

    // method to search if the correct password has been entered by matching the entered one
    // with the correct one for the specificed user
    public String searchPass(String username)
    {
        db = this.getReadableDatabase();
        String query = "select username, password from "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        String pass1, pass2;
        pass2 = "invalid username or password";
        if(cursor.moveToFirst())
        {
            do{
                pass1 = cursor.getString(0);

                if(pass1.equals(username))
                {
                    pass2 = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return pass2;
    }

    // query a specific Column, an String Arraylist is used as many fields are expected to be
    // returned
    public ArrayList<String> queryColumn(String column, String table)
    {
        db = this.getReadableDatabase();

        ArrayList<String> arrayList = new ArrayList<String>();

        String query = "select "+column+" from "+table;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String question = cursor.getString(cursor.getColumnIndex(column));
            arrayList.add(question);
        }
        return arrayList;
    }

    // query a column using a where clause, taking in parameters of the column to be returned,
    // the table and the column to be compared against the desired data
    public ArrayList<String> queryColumnWhere(String column, String from, String topic, String compare)
    {
        db = this.getReadableDatabase();

        ArrayList<String> arrayList = new ArrayList<String>();

        String query = String.format("select "+column+" from "+from+" where "+compare+" ='%s'", topic);
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String question = cursor.getString(cursor.getColumnIndex(column));
            arrayList.add(question);
        }
        return arrayList;
    }

    // query a column using a where and an and clause, taking in parameters of the column to be returned,
    // the table and the column to be compared against the desired data
    public ArrayList<String> queryColumnWhereAnd(String column, String column2, String from,
                                                 String compare, String and)
    {
        db = this.getReadableDatabase();

        ArrayList<String> arrayList = new ArrayList<String>();

        String query = String.format("select " + column + ", " + column2 + " from " + from + " where " +
                "" + column + " ='%s' and " + column2 + " ='%s'", compare, and);
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            String question = cursor.getString(cursor.getColumnIndex(column));
            arrayList.add(question);
        }
        return arrayList;
    }

    // method to query for a user, returning all the information from the specific user and return
    // each column of that user using the QueryInfo subclass
    public QueryInfo searchUser(String from, String username)
    {
        db = this.getReadableDatabase();
        String query = String.format("select %s, %s, %s from %s WHERE username='%s'",
                COLUMN_USERNAME, COLUMN_NAME, COLUMN_EMAIL, from, username);
        Cursor cursor = db.rawQuery(query, null);
        Log.w("count =", cursor.getCount() +"");

        if(cursor == null || cursor.getCount() == 0){
            Log.w("leaving", "ok");
            return null;
        }
        cursor.moveToFirst();
        QueryInfo info = new QueryInfo(
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
        );

        return info;
    }

    // invoked when the database is open to check if the classes already exists
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerison)
    {
        // drop the following tables if they exist
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

        String query2 = "DROP TABLE IF EXISTS "+TABLE_DEBATE;
        db.execSQL(query2);
        this.onCreate(db);

        String query3 = "DROP TABLE IF EXISTS "+TABLE_COMMENT;
        db.execSQL(query3);
        this.onCreate(db);

        String query4 = "DROP TABLE IF EXISTS "+TABLE_HASVOTED;
        db.execSQL(query4);
        this.onCreate(db);
    }

    // subclass to for storing data of the returned user
    public class QueryInfo
    {
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
