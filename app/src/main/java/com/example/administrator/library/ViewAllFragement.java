package com.example.administrator.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Y.Fruit;
import Y.FruitAdapter;
import Y.SqlHelper;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ViewAllFragement extends Fragment {
    private List<Fruit> fruitList =new ArrayList<>();
    ResultSet resultSet;
    FruitAdapter adapter;
    Statement statement;
    Connection connection;
    ListView Listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewall_fragement, container, false);
        initFruit();
        Listview = (ListView) view.findViewById(R.id.list_item1);
        adapter = new FruitAdapter(view.getContext(), R.layout.itemlayout, fruitList);
        Listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit =fruitList.get(position);
                String bookid =fruit.getBookid();
                Intent intent =new Intent(view.getContext(),BookDialog.class);
                intent.putExtra("extra_bookid",bookid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;


    }
    public void initFruit(){
        try{
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            resultSet =statement.executeQuery("SELECT * FROM system_book");
            while(resultSet.next()){
                fruitList.add(new Fruit(resultSet.getString("bookid"),resultSet.getString("bookname"),resultSet.getString("bookauther"),
                        resultSet.getString("bookstyle")));
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
        }
    }
}
