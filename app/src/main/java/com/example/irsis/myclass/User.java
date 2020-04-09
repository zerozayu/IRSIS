package com.example.irsis.myclass;

import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {
    private int uId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private boolean limit;

    public User() {
    }
    public User(int uId, String userAccount, String userPassword, String userName, boolean limit) {
        this.uId = uId;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.limit = limit;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }
}
