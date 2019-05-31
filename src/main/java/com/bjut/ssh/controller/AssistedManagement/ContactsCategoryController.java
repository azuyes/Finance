package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.Lswlfl;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.ContactsCategoryService;
import com.bjut.ssh.util.ExportExcelUtil;
import com.bjut.ssh.util.ImportExcelReadUtil;
import com.bjut.ssh.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ContactsCategoryController
 * @Description: 往来单位类别定义
 * @Author: lz
 * @CreateDate: 2018/3/28 16:22
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ContactsCategory")
public class ContactsCategoryController {

    @Autowired
    private ContactsCategoryService contactsCategoryService;

    /**
    *@author lz
    *@Description 根据级数，获取当前级数的分类单位信息
    *@Date 2018/4/8 9:40
    *@Param [id] 级数
    *@return java.util.List<com.bjut.ssh.entity.Lswlfl>
    **/
    @RequestMapping("/getContactsCategory/{id}")
    @ResponseBody
    public List<Lswlfl> getContactsCategory(@PathVariable("id") String id){
        List<Lswlfl> lswlflList = contactsCategoryService.getContactsCategory(id);
        return lswlflList;
    }

    /**
    *@author lz
    *@Description 保存往来单位信息
    *@Date 2018/4/2 9:32
    *@Param [lswlfl] 从前台获取用户输入的往来分类单位的信息，包括编号，名称，级数（自动获取）
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/saveContactsCategory")
    @ResponseBody
    public Msg saveContactsCategory(@RequestBody Lswlfl lswlfl){
        return  contactsCategoryService.saveContactsCategory(lswlfl);
    }

    /**
    *@author lz
    *@Description 根据编号删除一个分类信息
    *@Date 2018/4/8 9:43
    *@Param [ids] 要删除的往来分类单位的编号
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/delContactsCategory/{ids}/{catLevel}")
    @ResponseBody
    public Msg delContactsCategory(@PathVariable("ids") String ids,@PathVariable("catLevel") String catLevel){

        return  contactsCategoryService.delContactsCategoryById(ids,catLevel);

    }

    /**
    *@author lz
    *@Description 获取往里单位的分类信息
    *@Date 2018/4/8 9:44
    *@Param [id, levelFLag]  levelFLag为2，id为catNo1的前两位；levelFLag为3，id为catNo1的前四位；
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/queryContactsCategoryByLevel/{id}/{levelFLag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryContactsCategoryByLevel(@PathVariable("id") String id ,@PathVariable("levelFLag")String levelFLag){
        List<Lswlfl> lswlflList = contactsCategoryService.queryContactsCategoryByLevel(id,levelFLag);
        return Msg.success().add("lswlflList",lswlflList);
    }

    @RequestMapping(value="/ExportExcelContactsCategoryByLevel/{id}",method = RequestMethod.GET)
    @ResponseBody
    public void ExportExcelContactsCategory(@PathVariable("id") String id,HttpServletResponse response){
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            //FileOutputStream outputStream = new FileOutputStream("workbook.xls");
            String fileName="转出表.xlsx";
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("utf-8");
            List<Lswlfl> lswlflList = contactsCategoryService.getContactsCategory(id);
            //表头
            String[] titles = {"CatNo1","CatName1","CatLevel","FinLevel","Tid","State"};
            //表格
            ArrayList<List<Object>> excelList = new ArrayList<List<Object>>();
            //将listData中的没一条数据取出来按表头字段放到表格的每一行中
            for (int i = 0; i < lswlflList.size(); i++) {
                Lswlfl lswlfl = lswlflList.get(i);
                if(lswlfl==null){
                    continue;
                }
                //listRow表格的每一行
                List<Object> listRow = new ArrayList<>();
                listRow.add(lswlfl.getCatNo1()==null?" ":lswlfl.getCatNo1());
                listRow.add(lswlfl.getCatName1()==null?" ":lswlfl.getCatName1());
                listRow.add(lswlfl.getCatLevel()==null?" ":lswlfl.getCatLevel());
                listRow.add(lswlfl.getFinLevel()==null?" ":lswlfl.getFinLevel());
                excelList.add(listRow);
            }
            ExportExcelUtil.ExportMultiHeadExcel(titles, excelList, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value="/ImportExcelContactsCategoryByLevel")
    public ModelAndView ImportExcelContactsCategory(@RequestParam(value="excelFile") MultipartFile file){
        System.out.println("111111111111111111111111111");
        ModelAndView mv = new ModelAndView();
        try {
            //判断文件是否为空
            if(file == null){
                mv.addObject("msg", "failed");
                mv.setViewName("Contacts_Category");
                return mv;
            }
            String name = file.getOriginalFilename();
            long size = file.getSize();
            if(name == null || ImportExcelUtil.EMPTY.equals(name) && size==0){
                mv.addObject("msg", "failed");
                mv.setViewName("Contacts_Category");
                return mv;
            }
            //读取Excel数据到List中
            List<ArrayList<String>> list = new ImportExcelReadUtil().readExcel(file);
            //list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
            Lswlfl lswlfl = null;
            List<Lswlfl> listLswlfl = new ArrayList<Lswlfl>();
            for(int i = 0; i < list.size(); i++){
                lswlfl = new Lswlfl();
                lswlfl.setCatNo1(list.get(i).get(0));
                lswlfl.setCatName1(list.get(i).get(1));
                lswlfl.setCatLevel(list.get(i).get(2));
                lswlfl.setFinLevel(list.get(i).get(3));
                listLswlfl.add(lswlfl);
            }
            mv.addObject("msg", contactsCategoryService.saveContactsCategory(listLswlfl.get(0)));
            mv.setViewName("Contacts_Category");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

}
