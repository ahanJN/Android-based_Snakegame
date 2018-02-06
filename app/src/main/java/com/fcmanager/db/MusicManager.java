package com.fcmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fcmanager.bean.Music;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/28.
 * Add, delete, change, check
 */
public class MusicManager {
    public DBManager manager;
    //add
    public MusicManager(Context context){
        manager = new DBManager(context);
    }
    public void add(String musicName, String music){
        SQLiteDatabase db = manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("musicName", musicName);
        values.put("music", music);
        db.insert("Music", null, values);
    }
    //query
    public List<Music> queryById(String musicName) {
        List<Music> list = new ArrayList<Music>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from Music where musicName='" + musicName+"'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String musicName1 = cursor.getString(cursor
                    .getColumnIndex("musicName"));
            String music1 = cursor.getString(cursor
                    .getColumnIndex("music"));
            Music music = new Music();
            music.setMusic(music1);
            music.setMusicName(musicName1);
            list.add(music);
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
    public List<Music> queryById() {
        List<Music> list = new ArrayList<Music>();
        SQLiteDatabase db = manager.getReadableDatabase();
        String sql = "select * from Music";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String musicName1 = cursor.getString(cursor
                    .getColumnIndex("musicName"));
            String music1 = cursor.getString(cursor
                    .getColumnIndex("music"));
            Music music = new Music();
            music.setMusic(music1);
            music.setMusicName(musicName1);
            list.add(music);
        }
        cursor.close();
        db.close();
        return list;
    }



    //delect
    public void delectById(String musicName) {
        SQLiteDatabase db = manager.getWritableDatabase();
        String sql = "delete from Music where musicName = '" + musicName+"'";
        db.execSQL(sql, new String[] {});
        db.close();
    }
}
