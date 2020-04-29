package com.example.irsis.JDBC;


import java.sql.ResultSet;
import java.sql.SQLException;

public class get_information {
    private DatabaseActions action=new DatabaseActions();
    private ResultSet rs=null;
    String ans_string;
    int ans_int;
    boolean isExist = false;

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


//    public boolean checkUserAction(final String accountText, final String passwordText) {
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                action = new DatabaseActions();
//                rs = action.selectUser();
//
//                try {
//                    while (rs.next()) {
//                        String Uaccount = rs.getString("Uaccount");
//                        String Upassword = rs.getString("Upassword");
//                        if (accountText.equals(Uaccount)&&passwordText.equals(Upassword))
//                        {
//                            isRight=true;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return isRight;
//    }
}
