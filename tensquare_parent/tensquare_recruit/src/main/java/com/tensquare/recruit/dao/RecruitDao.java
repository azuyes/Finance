package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 获取推荐职位的前4名称，按照创建时间倒排序
     * @param state
     * @return
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state); //where state = ? order by createtime

    /**
     * 获取最新职位列表
     * @param state
     * @return
     *
     * 查询的条件是
     *  state != 0
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state); //where state != 0 order by createtime
}
