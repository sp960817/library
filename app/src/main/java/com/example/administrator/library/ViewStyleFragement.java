package com.example.administrator.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Y.SqlHelper;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ViewStyleFragement extends Fragment {
    private List<String> list =new ArrayList<>();
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewstyle_fragement,container,false);
        listView = (ListView) view.findViewById(R.id.style_list);
        connection = SqlHelper.openConnection();
        list.clear();
        try{
            statement =connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM book_style");
            while (resultSet.next()){
                list.add(new String(resultSet.getString("bookstyle")));
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){

        }
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((LibraryActivity)getActivity()).setStyle(list.get(position));
                ((LibraryActivity)getActivity()).HreplaceFragement(new ViewStyle1Fargement());
            }
        });
        return view;
    }

}
