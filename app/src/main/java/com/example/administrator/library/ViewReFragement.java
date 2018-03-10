package com.example.administrator.library;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Y.Fruit;
import Y.FruitAdapter;
import Y.Re_Fruit;
import Y.Re_FruitAdapter;
import Y.SqlHelper;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ViewReFragement extends Fragment{
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
        View view =inflater.inflate(R.layout.viewre_fragement,container,false);
        initFruit();
        listview =(ListView)view.findViewById(R.id.list_item2);
        adapter = new Re_FruitAdapter(view.getContext(), R.layout.re_itemlayout, fruitList);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Re_Fruit fruit =fruitList.get(position);
                final String re_id =fruit.getId();
                final String bookid =getbookid(re_id);
                Log.d("sss", bookid);
                AlertDialog.Builder dialog =new AlertDialog.Builder(view.getContext());
                dialog.setTitle("确认取消借阅么？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Connection connection =SqlHelper.openConnection();
                        try {
                            statement =connection.createStatement();
                            int count =statement.executeUpdate("DELETE FROM re_borrow_record WHERE id = '"+re_id+"'");
                            if(count >0){
                                int count1 =statement.executeUpdate("UPDATE system_book SET bookstock = bookstock+1 WHERE bookid = '"+bookid+"'");
                                if(count1>0){
                                    Toast.makeText(view.getContext(),"取消成功",Toast.LENGTH_SHORT).show();
                                    fruitList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(view.getContext(),"取消失败",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(view.getContext(),"取消失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
    private String getbookid(String re_id){
        String bookid="0";
        try{
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            readerid=(READERID)getActivity().getApplication();
            resultSet =statement.executeQuery("SELECT bookid FROM re_borrow_record WHERE id ='"+re_id+"'");
            if(resultSet.next()){
            bookid = resultSet.getString("bookid");
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
        }
        return bookid;
    }
    private void initFruit(){
        try{
            connection = SqlHelper.openConnection();
            statement =connection.createStatement();
            readerid=(READERID)getActivity().getApplication();
            String idid =readerid.getReaderid();
            Log.d("sss", idid);
            resultSet =statement.executeQuery("SELECT id,bookname,re_borrowdate FROM re_borrow_record WHERE readerid ='"+idid+"'");
            while(resultSet.next()){
                fruitList.add(new Re_Fruit(resultSet.getString("id"),resultSet.getString("bookname"),resultSet.getString("re_borrowdate")));
                Log.d("sss", "initFruit: ");
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
        }
    }

}
