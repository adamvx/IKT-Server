package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User extends BaseObject {
    private int id;
    private String email;
    private String password;
    private Date lastLogin;
    private String token;

    public User(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        email = rs.getString("email");
        password = rs.getString("password");
        lastLogin = rs.getDate("last_login");
        token = rs.getString("token");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

}
