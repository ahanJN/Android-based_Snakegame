package com.fcmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fcmanager.bean.UserInfo;
import com.fcmanager.db.UserInfoManager;
import com.fcmanager.utils.MyToast;

import java.util.List;


/**
 * Created by Administrator on 2016/11/21.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText et_phone, et_pwd;
    public Button btn_login, btn_register;
    public String userName,pwd;
    public UserInfoManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        InitView();
        manager = new UserInfoManager(LoginActivity.this);
    }

    private void InitView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_reg);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                userName = et_phone.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if (userName.length()>0&&!userName.equals("")){
                    if (pwd.length()>0&&!pwd.equals("")){
                        List<UserInfo> userInfos = manager.queryById(userName, pwd);
                        if(userInfos.size()>0) {
                            Intent intent_login = new Intent();
                            Bundle bundle = new Bundle();
                            intent_login.setClass(LoginActivity.this, MainActivity.class);
                            bundle.putString("userName", et_phone.getText().toString());
                            intent_login.putExtras(bundle);
                            startActivity(intent_login);
                            SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("name",  et_phone.getText().toString());
                            editor.commit();
                        }else{
                            MyToast.getToast(getApplicationContext(), "Username or Password incorrect!").show();
                        }
                    }else{
                        MyToast.getToast(getApplicationContext(), "Password can not be empty!").show();
                    }
                }else{
                    MyToast.getToast(getApplicationContext(), "Username can not be empty!").show();
                }
                break;
            case R.id.btn_reg:
                Intent intent_reg = new Intent();
                intent_reg.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_reg);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
