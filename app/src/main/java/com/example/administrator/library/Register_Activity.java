package com.example.administrator.library;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Y.SqlHelper;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText username,userpassword,userrealname;
    private Button register,l_return;
    private String TAG="TAG";
    private static final int UPDATE_TEXT=1,YY=2;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    Toast.makeText(Register_Activity.this,"用户名已存在",Toast.LENGTH_LONG).show();
                    break;
                case YY:
                    Toast.makeText(Register_Activity.this,"添加成功",Toast.LENGTH_LONG).show();
                    break;
                    default:
                        break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        username =(EditText)findViewById(R.id.l_id);
        userpassword=(EditText)findViewById(R.id.l_password);
        userrealname=(EditText)findViewById(R.id.l_name);
        register =(Button)findViewById(R.id.ok);
        register.setOnClickListener(this);
        l_return=(Button)findViewById(R.id.l_return);
        l_return.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ok:
                final String user = username.getText().toString().trim();
                final String password = userpassword.getText().toString().trim();
                final String realname = userrealname.getText().toString().trim();
                if (user.equals("")) {
                    Toast.makeText(Register_Activity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }
                if(password.equals("")) {
                    Toast.makeText(Register_Activity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }
                if(realname.equals("")){
                    Toast.makeText(Register_Activity.this,"姓名不能为空",Toast.LENGTH_LONG).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection connection = SqlHelper.openConnection();
                            Statement statement = connection.createStatement();
                            Log.d(TAG, user);
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM system_readers WHERE readerid = '"+user+"'");
                            if(resultSet.next()){
                                Message message =new Message();
                                message.what = UPDATE_TEXT;
                                handler.sendMessage(message);
                            }else{
                                Log.d(TAG, "2");
                                int count = statement.executeUpdate("INSERT INTO system_readers VALUES('"+user+"','"+password+"','"+realname+"')");
                                if (count>0){
                                    Message message =new Message();
                                    message.what = YY;
                                    handler.sendMessage(message);
                                }
                            }
                            connection.close();
                        }catch (SQLException e) {

                        }
                    }
                }).start();
                break;
            case R.id.l_return:
                finish();
                break;
            default:
                break;
        }
    }
}
