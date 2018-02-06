package com.fcmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2016/11/21.
 */
public class NewGameActivity extends Activity {
     public FrameLayout rl_bg;
    private static final String TAG = Changliang.DEBUG_TAG + "GameActivity";
    private GameField field;
    private Worker worker;
    private Thread workerThread;
    /**
     * 音乐
     */
    private MediaPlayer mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgame);
        InitView();

        SharedPreferences sp1 = getSharedPreferences("music", Context.MODE_PRIVATE);
        int data2 = sp1.getInt("musicName",1);

         if (data2==11){
             mp3 = MediaPlayer.create(NewGameActivity.this,R.raw.eggs);
             mp3.setLooping(true);
             mp3.start();
         }else if(data2==0){
             mp3 = MediaPlayer.create(NewGameActivity.this,R.raw.ppp);
             mp3.setLooping(true);
             mp3.start();
         }else if (data2==1){
             mp3 = MediaPlayer.create(NewGameActivity.this,R.raw.ds);
             mp3.setLooping(true);
             mp3.start();
         }else if (data2==2){
             mp3 = MediaPlayer.create(NewGameActivity.this,R.raw.weizhongjiang);
             mp3.setLooping(true);
             mp3.start();
         }else{
             mp3 = MediaPlayer.create(NewGameActivity.this,R.raw.zhongjiang);
             mp3.setLooping(true);
             mp3.start();
         }


       // Set layout parameters.
        //获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Changliang.setParams(dm.widthPixels, dm.heightPixels);
        //
        field = new GameField(this);
        worker = new Worker(this, field);
        //操作按钮下方
        findViewById(R.id.image_view_speed_background).setOnTouchListener(worker);
        findViewById(R.id.image_view_direction_background).setOnTouchListener(worker);
    }

    private void InitView() {
        rl_bg = (FrameLayout) findViewById(R.id.bg);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        workerThread = new Thread(worker);
        worker.setRunning(true);
        workerThread.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        worker.setRunning(false);
        try
        {
            workerThread.join();
        } catch (InterruptedException e)
        {
            Log.e(TAG, "onStop: ", e);
        }
        workerThread = null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp3.stop();
        mp3.release();
        worker = null;
        field = null;
        finish();

    }
}
