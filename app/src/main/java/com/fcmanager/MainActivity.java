package com.fcmanager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   public ImageView btn_exit;

    /**
     * New game
     */
    public Button btn_new_game;

    /**
     * Ranking
     */
    public Button btn_paihangbang;
    // Music
    public Button btn_music;
    // Difficulty
    public Button btn_difficulty;
    // Background
    public Button btn_bitmap;
    public RelativeLayout rl_bg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        InitView();

        Boolean isFirstIn = false;
        SharedPreferences pref = getSharedPreferences("myActivityName", 0);
        if (isFirstIn) {
            SharedPreferences sp = getSharedPreferences("bg", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("bg", 10);
            editor.commit();
            SharedPreferences sp2 = getSharedPreferences("music", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sp2.edit();
            editor2.putInt("musicName", 11);
            editor2.commit();

            SharedPreferences pref1 = getSharedPreferences("myActivityName", 0);
            SharedPreferences.Editor editor1 = pref1.edit();
            editor1.putBoolean("isFirstIn", false);
            editor1.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp_bg = getSharedPreferences("bg", Context.MODE_PRIVATE);
        int data = sp_bg.getInt("bg",1);
        if (data==10) {
            rl_bg.setBackgroundResource(R.drawable.picture1);
        }else if (data==0){
            rl_bg.setBackgroundResource(R.drawable.bg1);
        }else if (data==1){
            rl_bg.setBackgroundResource(R.drawable.bg2);
        }else if (data==2){
            rl_bg.setBackgroundResource(R.drawable.bg3);
        }else if (data==3){
            rl_bg.setBackgroundResource(R.drawable.bg4);
        }else{
            rl_bg.setBackgroundResource(R.drawable.bg5);
        }
    }

    private void InitView() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        btn_new_game = (Button) findViewById(R.id.btn_new_game);
        btn_new_game.setOnClickListener(this);
        btn_music = (Button) findViewById(R.id.btn_music);
        btn_music.setOnClickListener(this);
        btn_paihangbang = (Button) findViewById(R.id.btn_paihangbang);
        btn_paihangbang.setOnClickListener(this);
        btn_bitmap = (Button) findViewById(R.id.btn_bitmap);
        btn_bitmap.setOnClickListener(this);
        rl_bg = (RelativeLayout) findViewById(R.id.bg);
        btn_difficulty = (Button) findViewById(R.id.btn_difficulty);
        btn_difficulty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exit:
                MainActivity.this.finish();
                break;
            case R.id.btn_new_game:
                //新游戏
                Intent intent_new_game = new Intent();
                intent_new_game.setClass(MainActivity.this, NewGameActivity.class);
                startActivity(intent_new_game);
                break;
            case R.id.btn_paihangbang:
                Intent intent_paihangbang = new Intent();
                intent_paihangbang.setClass(MainActivity.this, RankingActivity.class);
                startActivity(intent_paihangbang);
                //排行榜
                break;
            case R.id.btn_music:
                Intent intent_music = new Intent();
                intent_music.setClass(MainActivity.this, MusicActivity.class);
                startActivity(intent_music);
                //音乐
                break;
            case R.id.btn_bitmap:
                //背景图片
                Intent intent_bitmap = new Intent();
                intent_bitmap.setClass(MainActivity.this, BitmapBgActivity.class);
                startActivity(intent_bitmap);
                break;
            case R.id.btn_difficulty:
                Intent intent_difficulty = new Intent();
                intent_difficulty.setClass(MainActivity.this, DifficultyActivity.class);
                startActivity(intent_difficulty);
                break;

        }
    }
}
