package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;


/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 根据标签名称查询最新回复列表
     * @return
     */
//    select * from tb_pl,tb_problem where id = problemid and labelid = "1"
//    select * from tb_problem where id in (select problemid from tb_pl where labelid = "1")

//    @Query("select p from Problem p where p.id in (select problemid from Pl where labelid = ?1) order by p.replytime desc ")
//    Page<Problem> findNewListByLabelId(String labelid,Pageable pageable);

    @Query(value = "select * from tb_pl,tb_problem where id = problemid and labelid = ? order by replytime desc ",nativeQuery = true)
    Page<Problem> findNewListByLabelId(String labelid, Pageable pageable);

    /**
     * 根据回复数倒排序获取问题列表
     * @return
     */
    @Query(value = "select * from tb_pl,tb_problem where id = problemid order by reply desc ",nativeQuery = true)
    Page<Problem> findHotListByLabelId(String labelid,Pageable pageable);

    /**
     * 根据标签ID查询等待回答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_pl,tb_problem where id = problemid and reply=0 order by createtime desc ",nativeQuery = true)
    Page<Problem> findWaitListByLabelId(String labelid,Pageable pageable);
}
