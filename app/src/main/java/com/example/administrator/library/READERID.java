package com.example.administrator.library;

import android.app.Application;

/**
 * Created by sp on 2018/3/9.
 */

public class READERID extends Application{
    private String readerid;
    public String getReaderid(){
        return readerid;
    }
    public void setLable(String s){
        readerid =s;
    }
}
