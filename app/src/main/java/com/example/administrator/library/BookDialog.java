package com.example.administrator.library;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Y.Fruit;
import Y.FruitAdapter;
import Y.FruitAllAdapter;
import Y.Fruit_all;
import Y.SqlHelper;

public class BookDialog extends AppCompatActivity implements View.OnClickListener{
    private List<Fruit_all> fruitAlls = new ArrayList<>();
    ResultSet resultSet;
    FruitAllAdapter adapter;
    Statement statement;
    Connection connection;
    ListView Listview;
    String id,idid;
    String TAG = "sss";
    Button borrow;
    Button b_retrun,see_recommend;
    private READERID readerid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_dialog);
        initFruit();
        Listview = (ListView)findViewById(R.id.dialog_list);
        adapter = new FruitAllAdapter(this,R.layout.dialoglayout,fruitAlls);
        Listview.setAdapter(adapter);
        borrow =(Button)findViewById(R.id.borrow);
        borrow_if();
        borrow.setOnClickListener(this);
        b_retrun = (Button)findViewById(R.id.b_return);
        b_retrun.setOnClickListener(this);
        see_recommend =(Button)findViewById(R.id.viewrecommmend);
        see_recommend.setOnClickListener(this);
    }
    private void borrow_if(){
        connection =SqlHelper.openConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM re_borrow_record WHERE readerid ='"+idid+"'AND " +
                    "bookid='"+id+"'");
            if(resultSet.next()){
                borrow.setEnabled(false);
                borrow.setText("已借阅");
            }
        }catch (SQLException e){

        }
    }
    private void initFruit(){
        try{
            Intent intent =getIntent();
            id=intent.getStringExtra("extra_bookid");
            readerid = (READERID)getApplication();
            idid=readerid.getReaderid();
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            resultSet =statement.executeQuery("SELECT * FROM system_book WHERE bookid = '"+id+"'");
            if (resultSet.next()){
                Log.d("TAG", "ok ");
                fruitAlls.add(new Fruit_all(resultSet.getString("bookname"),resultSet.getString("bookauther"),
                        resultSet.getString("bookstyle"),resultSet.getString("bookpub"),resultSet.getString("bookpubdate")));
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.borrow:
                AlertDialog.Builder dialog =new AlertDialog.Builder(BookDialog.this);
                dialog.setTitle("确认借阅么？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String bookname =fruitAlls.get(0).getBookname();
                        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy-MM-dd");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String   str   =   formatter.format(curDate);
                        readerid = (READERID)getApplication();
                        String idid=readerid.getReaderid();
                        Connection connection =SqlHelper.openConnection();
                        try {
                            Log.d(TAG, id+bookname+idid+str);
                            statement =connection.createStatement();
                            int count =statement.executeUpdate("INSERT INTO re_borrow_record (bookid,bookname,readerid,re_borrowdate) VALUES ('"+id+"','"+bookname+"','"+idid+"','"+str+"')");
                            if(count >0){
                                int count1 =statement.executeUpdate("UPDATE system_book SET bookstock = bookstock-1 WHERE bookid = '"+id+"'");
                                if(count1>0){
                                    borrow.setEnabled(false);
                                    borrow.setText("已借阅");
                                    Toast.makeText(BookDialog.this,"借阅成功",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "12121");
                                }else{
                                    Toast.makeText(BookDialog.this,"借阅失败",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(BookDialog.this,"借阅失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.show();

                break;
            case R.id.b_return:
                finish();
                break;
            case R.id.viewrecommmend:
            Intent intent = new Intent(this,ViewRecommend.class);
            intent.putExtra("extra_id",id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            break;
            default:
                    break;
        }
    }
}
