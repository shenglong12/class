package com.kuafu.login.config;

/**
 * 指定与登陆表关联的业务表
 */
public class LoginRelevanceConfig {
    private final static ThreadLocal<String> loginRelevanceTable = new ThreadLocal<>();

    public static String getLoginRelevanceTable() {
        return loginRelevanceTable.get();
    }

    public static void setLoginRelevanceTable(String loginRelevanceTable) {
        LoginRelevanceConfig.loginRelevanceTable.set(loginRelevanceTable);
    }

    public static void remove() {
        loginRelevanceTable.remove();
    }


}
