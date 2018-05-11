package com.pdtrong.final_android.UserDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userInfo";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String create_users_table = String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_USERNAME ,KEY_PASSWORD ,
                KEY_FULLNAME,KEY_BIRTHDAY,KEY_PHONE);
        db.execSQL(create_users_table);

    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        String drop_users_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_users_table);
        onCreate(db);
    }

    //them 1 user moi
    public void addUser(UserInfo userInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME,userInfo.getUsername());
        values.put(KEY_PASSWORD,userInfo.getPassword());
        values.put(KEY_FULLNAME,userInfo.getFullname());
        values.put(KEY_BIRTHDAY,userInfo.getBirthday());
        values.put(KEY_PHONE,userInfo.getPhone());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    //tra ve 1 user
    public UserInfo getUser(String username ) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_USERNAME + " = ?", new String[] {username},null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        UserInfo userInfo = new UserInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return userInfo;
    }

    //tra ve danh sach tat ca user
    public List<UserInfo> getAllUser(){
        List<UserInfo>  userInfoList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            UserInfo userInfo = new UserInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            userInfoList.add(userInfo);
            cursor.moveToNext();
        }
        return userInfoList;

    }
    //update danh sach User
    public void updateStudent(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME,userInfo.getUsername());
        values.put(KEY_PASSWORD,userInfo.getPassword());
        values.put(KEY_FULLNAME,userInfo.getFullname());
        values.put(KEY_BIRTHDAY,userInfo.getBirthday());
        values.put(KEY_PHONE,userInfo.getPhone());

        db.update(TABLE_NAME, values, KEY_USERNAME + " = ?", new String[] {userInfo.getUsername()});
        db.close();
    }

    //xoa 1 user
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_USERNAME + " = ?", new String[] { username });
        db.close();
    }

    //kiem tra 1 phan tu ton tai
    public boolean checkUserExisted(UserInfo userInfoCheck){

        String query = "SELECT * FROM " + TABLE_NAME;
        boolean userExisted =false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            UserInfo userInfo = new UserInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            if(userInfo.getUsername().equals(userInfoCheck.getUsername())
                    && userInfo.getPassword().equals(userInfoCheck.getPassword())){
                userExisted = true;
                break;
            }
            cursor.moveToNext();
        }
        return userExisted;
    }

    public boolean checkUserLogin(String username,String password){
        String query = "SELECT * FROM " + TABLE_NAME;
        boolean userExisted =false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            UserInfo userInfo = new UserInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            if(userInfo.getUsername().equals(username)
                    && userInfo.getPassword().equals(password)){
                    userExisted = true;
                break;
            }
            cursor.moveToNext();
        }
        return userExisted;
    }

    public int getNumUser(){
        int n=0;
        String query = "SELECT * FROM " + TABLE_NAME;
        boolean userExisted =false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
           n++;
            cursor.moveToNext();
        }
        return n;
    }

//    public int getUsersCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        // return count
//        return cursor.getCount();
//    }
}
