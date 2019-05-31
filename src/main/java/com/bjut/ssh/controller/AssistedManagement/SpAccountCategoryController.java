package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.SpAccountCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Title: SpAccountCategory
 * @Description: 专项核算类别定义
 * @Author: lz
 * @CreateDate: 2018/5/2 9:30
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountCategory")
public class SpAccountCategoryController {

    @Autowired
    private SpAccountCategoryService spAccountCategoryService;

    /**
    *@author lz
    *@Description 获取lshsfl表中的数据
    *@Date 2018/5/3 8:27
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lshsfl>
    **/
    @RequestMapping("/getSpAccountCategory")
    @ResponseBody
    public List<Lshsfl> getSpAccountCategory(){
        List<Lshsfl> lshsfls = spAccountCategoryService.getSpAccountCategory();
        return lshsfls;
    }

    /**
    *@author lz
    *@Description 保存核算分类
    *@Date 2018/5/3 8:28
    *@Param [lshsfl]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/saveSpAccountCategory")
    @ResponseBody
    public Msg saveSpAccountCategory(@RequestBody Lshsfl lshsfl){
        return  spAccountCategoryService.saveSpAccountCategory(lshsfl);
    }

    /**
    *@author lz
    *@Description 根据id删除核算分类
    *@Date 2018/5/3 8:28
    *@Param [id]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/delSpAccountCategoryById/{id}")
    @ResponseBody
    public Msg delSpAccountCategoryById(@PathVariable("id") String id){

        return spAccountCategoryService.delSpAccountCategoryById(id);

    }
}
