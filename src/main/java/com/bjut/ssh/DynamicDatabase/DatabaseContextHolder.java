package com.bjut.ssh.DynamicDatabase;

/**
 * @Title: DatabaseContextHolder
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/10/9 10:21
 * @Version: 1.0
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }
    public static String getCustomerType() {
        return contextHolder.get();
    }
    public static void clearCustomerType() {
        contextHolder.remove();
    }

}
