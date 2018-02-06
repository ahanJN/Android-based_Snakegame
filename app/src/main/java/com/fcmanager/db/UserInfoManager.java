package com.fcmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fcmanager.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/28.
 * Add, delete, change, check
 */
public class UserInfoManager {
    public DBManager manager;
    //add
    public UserInfoManager(Context context){
        manager = new DBManager(context);
    }
    public void add(String username, String pwd){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("pwd", pwd);
        db.insert("UserInfo", null, values);
    }
    //query
    public List<UserInfo> queryById(String userName) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from UserInfo where username='" + userName+"'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor
                    .getColumnIndex("username"));
            String pwd = cursor.getString(cursor
                    .getColumnIndex("pwd"));
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(username);
            userInfo.setPwd(pwd);
            list.add(userInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     *
     * @return
     * queryall
     */
    public List<UserInfo> queryById() {
        List<UserInfo> list = new ArrayList<UserInfo>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from UserInfo";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor
                    .getColumnIndex("username"));
            String pwd = cursor.getString(cursor
                    .getColumnIndex("pwd"));
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(username);
            userInfo.setPwd(pwd);
            list.add(userInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     *
     * @param userName
     * @param pwd
     * @return
     * According to the user name, password query
     */
    public List<UserInfo> queryById(String userName, String pwd) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from UserInfo where username='"+userName+"' and pwd ='"+pwd+"'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor
                    .getColumnIndex("username"));
            String pwd1 = cursor.getString(cursor
                    .getColumnIndex("pwd"));
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(username);
            userInfo.setPwd(pwd1);
            list.add(userInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    //delect
    public void delectById(String username) {
        SQLiteDatabase db = manager.getWritableDatabase();
        String sql = "delete from UserInfo where username = '" + username+"'";
        db.execSQL(sql, new String[] {});
        db.close();
    }
}
