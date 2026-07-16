package com.kuafu.web.event;

import lombok.Data;

@Data
public class LoginTableVo {
    /**
     * 对象名
     */
    private String name;
    /**
     * id的key
     */
    private String primaryKey;
    /**
     * 用户名key
     */
    private String userNameKey;
    /**
     * 密码key
     */
    private String passwordKey;
//    /**
//     * 手机号key
//     */
//    private String phoneKey;

    public LoginTableVo() {
    }

    public LoginTableVo(String name, String primaryKey, String userNameKey, String passwordKey) {
        this.name = name;
        this.primaryKey = primaryKey;
        this.userNameKey = userNameKey;
        this.passwordKey = passwordKey;
    }


//
//    public LoginTableVo(String name, String primaryKey, String phoneKey) {
//        this.name = name;
//        this.primaryKey = primaryKey;
//        this.phoneKey = phoneKey;
//    }

//    public LoginTableVo(String name, String primaryKey, String userNameKey, String passwordKey, String phoneKey) {
//        this.name = name;
//        this.primaryKey = primaryKey;
//        this.userNameKey = userNameKey;
//        this.passwordKey = passwordKey;
//        this.phoneKey = phoneKey;
//    }
}
