package com.example.irsis.myclass;

public class User{
    private String userName;
    private String userAccount;
    private String userPassword;
    private String userPhone;
    private String userLimit;

    public User(){}

    public User(String userName, String userAccount, String userPassword, String userPhone, String userLimit) {
        this.userName = userName;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userLimit = userLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserEmail() {
        return userPhone;
    }

    public void setUserEmail(String userEmail) {
        this.userPhone = userEmail;
    }

    public String getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(String userLimit) {
        this.userLimit = userLimit;
    }
}
