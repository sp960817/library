package com.example.administrator.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;

import Y.SqlHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username,password ;
    private Button bt_username_clear,bt_pwd_clear;
    private Button login;
    private Button register;
    private Button forgive_pwd;
    private READERID readerid;
    private CheckBox rememberpass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        android.support.v7.widget.Toolbar toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        initView();
        boolean isRemember =pref.getBoolean("remember_pass",false);
        if(isRemember){
            String pusername =pref.getString("username","");
            String ppassword = pref.getString("password","");
            username.setText(pusername);
            password.setText(ppassword);
            rememberpass.setChecked(true);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.out:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
                default:
                    break;
        }return true;
    }

    private void initView() {
        username = (EditText) findViewById(R.id.adminname);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = username.getText().toString().trim();
                if("".equals(user)){
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }else {
                    bt_username_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password = (EditText)findViewById(R.id.adminpd);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = password.getText().toString().trim();
                if ("".equals(pwd)) {
// 用户名为空,设置按钮不可见
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                } else {
// 用户名不为空，设置按钮可见
                    bt_pwd_clear.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_clear.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        register =(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        forgive_pwd =(Button)findViewById(R.id.forgive_pwd);
        forgive_pwd.setOnClickListener(this);
        rememberpass =(CheckBox)findViewById(R.id.remember_pass);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_username_clear:
                    username.setText("");
                    break;
                case R.id.bt_pwd_clear:
                    password.setText("");
                    break;
                case R.id.login:
                    final String user = username.getText().toString().trim();
                    final String userpw = password.getText().toString().trim();
                            ResultSet rs;
                            Statement stm;
                            try {
                                Connection connection= SqlHelper.openConnection();
                                stm =connection.createStatement();
                                 rs = stm.executeQuery("SELECT * FROM system_readers WHERE readerid = '"+user+"'");
                                if(rs.next()){
                                        String id = rs.getString("readerid");
                                        String pw = rs.getString("readerpw");
                                        Log.d("sss", user);
                                        Log.d("sss", userpw);
                                        Log.d("sss", pw);
                                        if(userpw.equals(pw)){
                                            editor=pref.edit();
                                            if(rememberpass.isChecked()){
                                                editor.putBoolean("remember_pass",true);
                                                editor.putString("username",user);
                                                editor.putString("password",userpw);
                                            }else {
                                                editor.clear();
                                            }
                                            editor.apply();
                                            Intent intent =new Intent(MainActivity.this,LibraryActivity.class);
                                            startActivity(intent);
                                            readerid=(READERID)getApplication();
                                            readerid.setLable(id);

                                        }else{
                                            Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                    Toast.makeText(MainActivity.this,"用户名不存在",Toast.LENGTH_LONG).show();
                                }
                                connection.close();

                                }catch (SQLException e){
                            }
                            break;

                case R.id.register:
// 注册按钮
                    Intent intent = new Intent(MainActivity.this,Register_Activity.class);
                    startActivity(intent);
                    break;
                case R.id.forgive_pwd:
// 忘记密码按钮
                    Toast.makeText(MainActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
    }



}



