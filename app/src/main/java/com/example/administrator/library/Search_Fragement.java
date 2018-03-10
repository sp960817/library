package com.example.administrator.library;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Search_Fragement extends Fragment implements View.OnClickListener{
    private List<Fruit> fruitList =new ArrayList<>();
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    FruitAdapter adapter;
    ListView listView;
    EditText editText;
    String x;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragement,container,false);
        listView =(ListView) view.findViewById(R.id.list_item1);
        adapter =new FruitAdapter(getContext(),R.layout.itemlayout,fruitList);
        editText=(EditText)view.findViewById(R.id.searcht);
        Button button = (Button)view.findViewById(R.id.searchb);
        button.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    private void initFruit(){
        x = editText.getText().toString();
        connection= SqlHelper.openConnection();
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM system_book WHERE bookname LIKE '%"+x+"%' OR " +
                    "bookauther LIKE '%"+x+"%' OR bookstyle LIKE '%"+x+"%' OR bookpub LIKE '%"+x+"%'");
            while (resultSet.next()){
                fruitList.add(new Fruit(resultSet.getString("bookid"),resultSet.getString("bookname"),resultSet.getString("bookauther"),
                        resultSet.getString("bookstyle")));
            }
        }catch (SQLException e){

        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.searchb:
                fruitList.clear();
                initFruit();
                Log.d("sss",x);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
        }
    }
}
