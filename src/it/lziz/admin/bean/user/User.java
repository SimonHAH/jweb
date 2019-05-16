package it.lziz.admin.bean.user;

import java.sql.Timestamp;

public class User {
    private Integer s_uid;
    private String s_username;
    private String s_nickname;
    private String s_password;
    private String s_email;
    private Timestamp s_birthday;
    private Timestamp s_updatetime;

    @Override
    public String toString() {
        return "User{" +
                "s_uid=" + s_uid +
                ", s_username='" + s_username + '\'' +
                ", s_nickname='" + s_nickname + '\'' +
                ", s_password='" + s_password + '\'' +
                ", s_email='" + s_email + '\'' +
                ", s_birthday=" + s_birthday +
                ", s_updatetime=" + s_updatetime +
                '}';
    }

    public User(Integer s_uid, String s_username, String s_nickname, String s_password, String s_email, Timestamp s_birthday, Timestamp s_updatetime) {
        this.s_uid = s_uid;
        this.s_username = s_username;
        this.s_nickname = s_nickname;
        this.s_password = s_password;
        this.s_email = s_email;
        this.s_birthday = s_birthday;
        this.s_updatetime = s_updatetime;
    }

    public User() {
    }

    public Integer getS_uid() {
        return s_uid;
    }

    public void setS_uid(Integer s_uid) {
        this.s_uid = s_uid;
    }

    public String getS_username() {
        return s_username;
    }

    public void setS_username(String s_username) {
        this.s_username = s_username;
    }

    public String getS_nickname() {
        return s_nickname;
    }

    public void setS_nickname(String s_nickname) {
        this.s_nickname = s_nickname;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    public String getS_email() {
        return s_email;
    }

    public void setS_email(String s_email) {
        this.s_email = s_email;
    }

    public Timestamp getS_birthday() {
        return s_birthday;
    }

    public void setS_birthday(Timestamp s_birthday) {
        this.s_birthday = s_birthday;
    }

    public Timestamp getS_updatetime() {
        return s_updatetime;
    }

    public void setS_updatetime(Timestamp s_updatetime) {
        this.s_updatetime = s_updatetime;
    }
}
