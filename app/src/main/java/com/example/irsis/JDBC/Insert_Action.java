package com.example.irsis.JDBC;

import java.sql.ResultSet;

public class Insert_Action {
    DatabaseActions action;
    ResultSet rs = null;
    boolean flag;
    //新用户注册添加
    public boolean insertUser(final Integer Uid, final String Uname, final String Uaccount, final String Upassword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                action = new DatabaseActions();
                flag = action.register(Uid,Uname,Uaccount,Upassword);
            }
        }).start();
        return flag;
    }
}
