package com.example.irsis.JDBC;


import java.sql.ResultSet;
import java.sql.SQLException;

public class get_information {
    private DatabaseActions action=new DatabaseActions();
    private ResultSet rs=null;
    private String ans_string;
    int ans_int;

    //通过Uaccount查询Uaccount
    public String getUaccountByUaccount(final String Uaccount){
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs=action.selectByUaccount(Uaccount);
                try {
                    ans_string=rs.getString("Uaccount");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return ans_string;
    }

    //根据Uaccount查询Upassword
    public String getUpasswordByUaccount(final String Uaccount){
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs=action.selectByUaccount(Uaccount);
                try {
                    ans_string=rs.getString("Upassword");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return ans_string;
    }

}
