package com.fcmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fcmanager.adapter.MusicAdapter;
import com.fcmanager.bean.Music;
import com.fcmanager.bean.UserInfo;
import com.fcmanager.db.MusicManager;
import com.fcmanager.db.UserInfoManager;
import com.fcmanager.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/11/21.
 */
public class MusicActivity extends AppCompatActivity {
    //list
    public ListView listView;
    //list
    public List<Map<String,String>> list=null;
    //manager
    public MusicManager manager;
    //adapter
    public MusicAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.music);
        InitView();
        manager = new MusicManager(MusicActivity.this);
        List<Music> music1 = manager.queryById("music1");
        if (music1.size()<=0){
            manager.add("music1","music1");
        }
        List<Music> music2 = manager.queryById("music2");
        if (music2.size()<=0){
            manager.add("music2","music2");
        }
        List<Music> music3 = manager.queryById("music3");
        if (music3.size()<=0){
            manager.add("music3","music3");
        }
        adapter = new MusicAdapter(this,manager.queryById());
        listView.setAdapter(adapter);
        /***
         *点击弹出音乐提示款的信息
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
                builder.setTitle("Sound");
                builder.setMessage("Do you confirm to select this sound？");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /**
                         * 存储需要播放的音乐文件得到本地
                         */
                        SharedPreferences sp = getSharedPreferences("music", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("musicName", position);
                        editor.commit();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void InitView() {
     listView = (ListView) findViewById(R.id.listview);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
