package it.lziz.admin.bean.admin;

/**
 * 管理员类
 */
public class Admin {
    private Integer s_aid;
    private String s_username;
    private String s_password;

    public Admin(Integer s_aid, String s_username, String s_password) {
        this.s_aid = s_aid;
        this.s_username = s_username;
        this.s_password = s_password;
    }

    public Admin() {
    }

    public Integer getS_aid() {
        return s_aid;
    }

    public void setS_aid(Integer s_aid) {
        this.s_aid = s_aid;
    }

    public String getS_username() {
        return s_username;
    }

    public void setS_username(String s_username) {
        this.s_username = s_username;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "s_aid=" + s_aid +
                ", s_username='" + s_username + '\'' +
                ", s_password='" + s_password + '\'' +
                '}';
    }
}
