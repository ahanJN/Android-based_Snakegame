package com.fcmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/9.
 * 难度设置
 */
public class DifficultyActivity extends Activity implements View.OnClickListener {
    private ImageView btn_exit;
    private Button btn_simple,btn_commonly,btn_Hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty);
        InitView();
    }

    private void InitView() {
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        btn_simple = (Button) findViewById(R.id.btn_simple);
        btn_simple.setOnClickListener(this);
        btn_commonly = (Button) findViewById(R.id.btn_commonly);
        btn_commonly.setOnClickListener(this);
        btn_Hard = (Button) findViewById(R.id.btn_Hard);
        btn_Hard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exit:
                DifficultyActivity.this.finish();
                break;
            case R.id.btn_simple:
                AlertDialog.Builder builder = new AlertDialog.Builder(DifficultyActivity.this);
                builder.setTitle("Info");
                builder.setMessage("You confirm to change the difficulty to simple？");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getSharedPreferences("diff", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("diff", 1);
                        editor.commit();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
            case R.id.btn_commonly:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DifficultyActivity.this);
                builder1.setTitle("Info");
                builder1.setMessage("You confirm to change the difficulty to common？");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getSharedPreferences("diff", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("diff", 2);
                        editor.commit();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder1.show();
                break;
            case R.id.btn_Hard:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(DifficultyActivity.this);
                builder2.setTitle("Info");
                builder2.setMessage("You confirm to change the difficulty to hard？");
                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getSharedPreferences("diff", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("diff", 3);
                        editor.commit();
                    }
                });
                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder2.show();
                break;
        }
    }
}
