package com.fcmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fcmanager.bean.Ranking;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/28.
 * Add, delete, change, check
 */
public class RankingManager {
    public DBManager manager;
    //add
    public RankingManager(Context context){
        manager = new DBManager(context);
    }

    /**
     *
     * @param userName 用户名
     * @param Fraction
     * @param Ranking
     */
    public void add(String userName, String Fraction,String Ranking){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        values.put("Fraction", Fraction);
        values.put("Ranking", Ranking);
        db.insert("Ranking", null, values);
    }
    //query
    public List<Ranking> queryById(String userName) {
        List<Ranking> list = new ArrayList<Ranking>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from Ranking where username='" + userName+"'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor
                    .getColumnIndex("userName"));
            String Fraction = cursor.getString(cursor
                    .getColumnIndex("Fraction"));
            String Ranking = cursor.getString(cursor
                    .getColumnIndex("Ranking"));
            Ranking ranking = new Ranking();
            ranking.setId(id);
            ranking.setUserName(username);
            ranking.setFraction(Fraction);
            ranking.setRanking(Ranking);
            list.add(ranking);
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
    public List<Ranking> queryById() {
        List<Ranking> list = new ArrayList<Ranking>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from Ranking order by Fraction desc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor
                    .getColumnIndex("userName"));
            String Fraction = cursor.getString(cursor
                    .getColumnIndex("Fraction"));
            String Ranking = cursor.getString(cursor
                    .getColumnIndex("Ranking"));
            Ranking ranking = new Ranking();
            ranking.setId(id);
            ranking.setUserName(username);
            ranking.setFraction(Fraction);
            ranking.setRanking(Ranking);
            list.add(ranking);
        }
        cursor.close();
        db.close();
        return list;
    }



    //delect
    public void delectById(String username) {
        SQLiteDatabase db = manager.getWritableDatabase();
        String sql = "delete from Ranking where userName = '" + username+"'";
        db.execSQL(sql, new String[] {});
        db.close();
    }

    //delect
    public void updateRank(String Fraction,String id) {
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Fraction", Fraction);//key为字段名，value为值
        db.update("Ranking", values, "id=?", new String[]{id});
        db.close();
    }
}
