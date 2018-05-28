package com.example.administrator.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Y.SqlHelper;

public class ViewRecommend extends AppCompatActivity {
    TextView textView,textlv;
    Button button_return;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String id,text_recommend,text_recommendlv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recommend);
        intview();
        Intent intent =getIntent();
        id=intent.getStringExtra("extra_id");
        connection = SqlHelper.openConnection();
        try{
            statement =connection.createStatement();
            resultSet = statement.executeQuery("SELECT bookrecommend,booklv FROM system_book WHERE bookid ='"+id+"'");
            if(resultSet.next()){
                text_recommend = resultSet.getString("bookrecommend");
                text_recommendlv =resultSet.getString("booklv");
            }
            textlv.setText("推荐等级:"+text_recommendlv);
            textView.setText("推荐语:"+text_recommend);
        }catch (SQLException e){}
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void intview(){
        textView=(TextView)findViewById(R.id.recommend_see);
        textlv=(TextView)findViewById(R.id.recommend_seelv);
        button_return =(Button)findViewById(R.id.r_return);

    }
}
