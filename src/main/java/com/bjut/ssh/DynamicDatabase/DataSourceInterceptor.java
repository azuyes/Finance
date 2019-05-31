package com.bjut.ssh.DynamicDatabase;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Title: DataSourceInterceptor
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/10/9 10:22
 * @Version: 1.0
 */

//切换数据源
public class DataSourceInterceptor {

    public void setdataSource(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSource");
    }

    public void setdataSource1(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSource1");
    }
}
