package com.bjut.ssh.DynamicDatabase;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Title: DynamicDataSource
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/10/9 10:22
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // TODO 自动生成的方法存根
        return DatabaseContextHolder.getCustomerType();
    }
}
