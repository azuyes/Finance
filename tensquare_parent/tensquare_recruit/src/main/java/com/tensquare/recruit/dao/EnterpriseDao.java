package com.tensquare.recruit.dao;


import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 *  方法命名查询：
 *        find实体类名称By实体类的属性名称（要求必须按照驼峰命名规范）
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise> {

    /**
     * 查询热门企业列表
     * @param ishot
     * @return
     */
    List<Enterprise> findEnterpriseByIshot(String ishot); //where ishost = ?

}
