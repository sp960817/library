package com.example.administrator.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class ViewReturnFragement extends Fragment {
    private List<Re_Fruit> fruitList =new ArrayList<>();
    Re_FruitAdapter adapter ;
    ListView listView;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    READERID readerid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.return_fragement,container,false);
        initFruit();
        listView =(ListView)view.findViewById(R.id.list_item4);
        adapter = new Re_FruitAdapter(view.getContext(),R.layout.re_itemlayout,fruitList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    } private void initFruit(){
        try{
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            readerid=(READERID)getActivity().getApplication();
            String idid =readerid.getReaderid();
            Log.d("sss", idid);
            resultSet =statement.executeQuery("SELECT bookid,bookname,returndate FROM return_record WHERE readerid ='"+idid+"'");
            while(resultSet.next()){
                fruitList.add(new Re_Fruit(resultSet.getString("bookid"),resultSet.getString("bookname"),resultSet.getString("returndate")));
                Log.d("sss", "duqi");
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
            Log.d("sss", "err 1");
        }
    }

}
