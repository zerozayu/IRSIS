package com.example.irsis.JDBC;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseActions {
    //实现数据库的连接并完成表user增删改查函数的定义|user(Uid,Uname,Uaccount,Upassword)
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String ip = "cdb-hyfbs7ph.gz.tencentcdb.com";//外网地址
    private static final int port = 10106;
    private static final String dbName = "irsis";
    private static final String user = "root";
    private static final String password = "zy19980412";
    private static final String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    static private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    //构造器
    DatabaseActions() {
        conn = getConnection();
    }

    //连接数据库
    private Connection getConnection() {
        java.sql.Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！");
        }
        return con;
    }

    //关闭连接
    public void close(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
                System.out.println("数据库连接关闭！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //用户注册
    public boolean register(Integer Uid, String Uname, String Uaccount, String Upassword) {
        String sql = "insert into user values(?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Uid);
            pstmt.setString(2, Uname);
            pstmt.setString(3, Uaccount);
            pstmt.setString(4, Upassword);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("插入失败！");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据Uaccount查询用户信息
    public ResultSet selectByUaccount(String Uaccount) {
        //查找信息
        //conn = this.getConnection();
        try {
            //非条件查询直接赋值
            String sql;
            sql = "select * from user where Uaccount = ? ";//order by Uname asc
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uaccount);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("用户名查询错误！");
        }
        return rs;
    }


    //根据id删除用户
    public boolean shanchuyonghu(String Uid) {
        String sql = "delete from user where Uid = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("用户删除失败");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据id更改用户信息
    public boolean genggaiyonghu(Integer Uid, String Uname, String Uaccount, String Upassword) {
        String sql = "update user set Uname =? ,Uaccount = ? ,Upassword = ? where Uid = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uname);
            pstmt.setString(2, Uaccount);
            pstmt.setString(3, Upassword);
            pstmt.setInt(4, Uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("用户更新失败！");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //查询所有用户
    public ResultSet chaxun(String userame, String id, String password, Integer ischild) {
        //查找信息
        //  conn = this.getConnection();
        try {
            //非条件查询直接赋值
            String sql;
            sql = "select * from user order by username asc ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("用户名查询错误！");
        }
        return rs;
    }

}

