package com.example.irsis.JDBC;

import java.sql.Blob;

public class Action {
    private DatabaseActions action;
    private boolean flag;

    //新用户注册添加
    public boolean insertUser(final String Uname, final String Uaccount, final String Upassword,
                              final String Uphone, final String Ulimit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                action = new DatabaseActions();
                flag = action.register(Uname, Uaccount, Upassword, Uphone, Ulimit);
            }
        }).start();
        return flag;
    }

    //删除用户
    public void deleteUserByAccount(String account){
        new Thread(new Runnable() {
            @Override
            public void run() {
                action = new DatabaseActions();
                action.deleteUserByAccount(account);
            }
        }).start();
    }

    //新增问题
    public void insertProblem(String Pname, String Pcontent, String Pimage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                action=new DatabaseActions();
                action.insertProblem(Pname,Pcontent,Pimage);
            }
        }).start();
    }
}
