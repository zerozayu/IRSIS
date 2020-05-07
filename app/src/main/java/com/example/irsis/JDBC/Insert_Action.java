package com.example.irsis.JDBC;

import java.sql.ResultSet;

public class Insert_Action {
    private DatabaseActions action;
    private boolean flag = false;

    //新用户注册添加
    public boolean insertUser( final String Uname, final String Uaccount, final String Upassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                action = new DatabaseActions();
                flag = action.register(Uname, Uaccount, Upassword);
            }
        }).start();
        return flag;
    }
}
