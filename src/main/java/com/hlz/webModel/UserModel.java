package com.hlz.webModel;

import java.sql.Date;

/**
 *在添加user时，读取参数
 * @author Administrator 2017-3-1
 */
public class UserModel {
    private String name;
    private String username;
    private String password;
    private int style;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    
}
