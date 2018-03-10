package com.example.administrator.library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Y.Re_Fruit;
import Y.Re_FruitAdapter;
import Y.SqlHelper;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ViewBorrowFargement extends Fragment {
    Statement statement;
    Connection connection;
    ResultSet resultSet;
    private List<Re_Fruit> fruitList= new ArrayList<>();
    Re_FruitAdapter adapter;
    ListView listview;
    private READERID readerid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.viewborrow_fragement,container,false);
        initFruit();
        listview =(ListView)view.findViewById(R.id.list_item3);
        adapter = new Re_FruitAdapter(view.getContext(),R.layout.borrow_itemlayout, fruitList);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
    private void initFruit(){
        try{
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            readerid=(READERID)getActivity().getApplication();
            String idid =readerid.getReaderid();
            Log.d("sss", idid);
            resultSet =statement.executeQuery("SELECT bookid,bookname,borrowdate FROM borrow_record WHERE readerid ='"+idid+"'AND isreturn='否'");
            while(resultSet.next()){
                fruitList.add(new Re_Fruit(resultSet.getString("bookid"),resultSet.getString("bookname"),resultSet.getString("borrowdate")));
                Log.d("sss", "duqi");
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
            Log.d("sss", "err 1");
        }
    }

}
