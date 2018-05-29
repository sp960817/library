package com.example.administrator.library;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Y.Fruit;
import Y.FruitAdapter;
import Y.SqlHelper;

public class LibraryActivity extends AppCompatActivity {
    private List<Fruit> fruitList =new ArrayList<>();
    ResultSet resultSet;
    FruitAdapter adapter;
    Statement statement;
    Connection connection;
    ListView Listview;
    String style;
    READERID readerid;
    private DrawerLayout mDrawerLayout;
    final String[] str=new String[]{"按照书名查找","按照分类查找","按照作者查找"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        android.support.v7.widget.Toolbar toolbar =(android.support.v7.widget.Toolbar)findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        final NavigationView navView =(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar =getSupportActionBar();
        String username =getUsername();
        if(username.equals("")){
        }else {
            View hh = navView.getHeaderView(0);
            TextView textview = (TextView)hh.findViewById(R.id.username);
            textview.setText(username);
        }
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_seach:
                        replaceFragement(new Search_Fragement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                    break;
                    case R.id.nav_recommend:
                        replaceFragement(new ViewRecommendFragment());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_viewall:
                        replaceFragement(new ViewAllFragement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_record:
                        replaceFragement(new ViewReFragement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_borrow_record:
                        replaceFragement(new ViewBorrowFargement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_return_record:
                        replaceFragement(new ViewReturnFragement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_viewstyle:
                        replaceFragement(new ViewStyleFragement());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_exit:
                        finish();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(LibraryActivity.this,"作者：孙鹏\n邮箱：402151646@qq.com",Toast.LENGTH_LONG).show();
                    default:
                        break;
                }
                return false;
            }
        });

    }
    public void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout,fragment);
        transaction.commit();
        }
    public void HreplaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
                    break;
        }
        return true;
    }
    public String getStyle(){
        return style;
    }
    public void setStyle(String style){
        this.style =style;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume");
    }
    private String getUsername(){
        String username = "";
        connection=SqlHelper.openConnection();
        try{
            readerid=(READERID)getApplication();
            String idid = readerid.getReaderid();
            statement =connection.createStatement();
            resultSet = statement.executeQuery("SELECT readername FROM system_readers WHERE readerid = '"+idid+"'");
            if(resultSet.next()){
                username = resultSet.getString("readername");
            }

        }catch (SQLException e){

        }
        return username;

    }

}
