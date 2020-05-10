package com.example.irsis.JDBC;

import android.util.Log;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseActions {
    //实现数据库的连接并完成表user增删改查函数的定义|user(Uid,Uname,Uaccount,Upassword)
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "zhangyu";//root
    private static final String password = "ZHANGyu123..";//zy19980412
    private static final String url = "jdbc:mysql://39.106.169.97:3506/zhangyu?useUnicode=true&characterEncoding=utf-8&useSSL=false&&allowPublicKeyRetrieval=true";

    static private Connection conn = null;
    private PreparedStatement pstmt=null;
    private ResultSet rs;

    //构造器
    public DatabaseActions() {
        conn = getConnection();
    }

    //连接数据库
    private Connection getConnection() {
        Connection con = null;
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
    public void close() {
        if (null != conn) {
            try {
                conn.close();
                System.out.println("数据库连接关闭！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //插入用户（Uname,Uaccount,Upassword）
    public boolean register(String Uname, String Uaccount, String Upassword,String Uphone,String Ulimit) {
        String sql = "insert into user values(?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uname);
            pstmt.setString(2, Uaccount);
            pstmt.setString(3, Upassword);
            pstmt.setString(4,Uphone);
            pstmt.setString(5,Ulimit);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据Uaccount查询用户信息
    public ResultSet selectByUaccount(String Uaccount) {
        conn = this.getConnection();
        try {
            //非条件查询直接赋值
            String sql= "select * from user where Uaccount = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uaccount);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("account查询错误！");
            e.printStackTrace();
        }
        return rs;
    }

    //根据account删除用户
    public boolean deleteUserByAccount(String Uaccount) {
        String sql = "delete from user where Uaccount = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Uaccount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据Uaccount查询用户信息
    public ResultSet selectProblem() {
        conn = this.getConnection();
        try {
            //非条件查询直接赋值
            String sql= "select * from problem";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("account查询错误！");
            e.printStackTrace();
        }
        return rs;
    }

    //插入问题
    public boolean insertProblem(String Pname, String Pcontent, String Pimage) {
        String sql = "insert into problem values(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Pname);
            pstmt.setString(2, Pcontent);
            pstmt.setString(3,Pimage);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //查询所有用户
    public ResultSet selectAll() {
        //  conn = this.getConnection();
        try {
            String sql;
            sql = "select * from user order by Uaccount asc ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("用户名查询错误！");
        }
        return rs;
    }

}

