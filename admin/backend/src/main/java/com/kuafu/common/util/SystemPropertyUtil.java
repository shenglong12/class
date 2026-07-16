package com.kuafu.common.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class SystemPropertyUtil {

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(final String key, String def) {

        String value = null;
        try {
            if (System.getSecurityManager() == null) {
                value = System.getProperty(key);
            } else {
                value = AccessController.doPrivileged(new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return System.getProperty(key);
                    }
                });
            }
        } catch (SecurityException e) {
        }

        if (value == null) {
            return def;
        }

        return value;
    }
}
