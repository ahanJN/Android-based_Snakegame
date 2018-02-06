package com.fcmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fcmanager.bean.UserInfo;
import com.fcmanager.db.UserInfoManager;
import com.fcmanager.utils.MyToast;

import java.util.List;


/**
 * Created by Administrator on 2016/11/20.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_reg;
    public ImageView btn_exit;
    public EditText et_username,et_pwd;
    public String userName,pwd;
    public UserInfoManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.register);
        InitView();
        manager = new UserInfoManager(RegisterActivity.this);
    }


    private void InitView() {
        et_username = (EditText) findViewById(R.id.et_username);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(this);
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);

        et_pwd = (EditText) findViewById(R.id.et_pwd);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reg:
                userName = et_username.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if (userName.length()>0&&!userName.equals("")){
                   if(pwd.length()>0&&!pwd.equals("")){
                                          List<UserInfo> userInfos = manager.queryById(userName);
                                          if(userInfos.size()<=0) {
                                              manager.add(userName, pwd);
                                              MyToast.getToast(getApplicationContext(), "Register succeed!").show();
                                              RegisterActivity.this.finish();
                                          }else{
                                              MyToast.getToast(getApplicationContext(), "This username has been registered!").show();
                                          }

                   }else{
                       MyToast.getToast(getApplicationContext(), "Password can not be empty!").show();
                   }
                }else{
                    MyToast.getToast(getApplicationContext(), "Username can not be empty!").show();
                }
                break;
            case R.id.btn_exit:
                RegisterActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterActivity.this.finish();
    }
}
