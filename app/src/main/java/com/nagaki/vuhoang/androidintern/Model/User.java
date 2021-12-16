package com.nagaki.vuhoang.androidintern.Model;

public class User {
    private String password;
    private String birthday;
    private String email;

    public User() {
    }

    public User(String password, String birthday, String email) {
        this.password = password;
        this.birthday = birthday;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
