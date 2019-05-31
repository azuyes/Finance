package com.bjut.ssh.controller.OtherSettings;

import com.bjut.ssh.entity.Accrecord;
import com.bjut.ssh.entity.Lsgnbh;
import com.bjut.ssh.entity.Lspass;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.OtherSettings.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Title: UserManagementController
 * @Description: 用户管理
 * @Author: LYH
 * @CreateDate: 2018/3/29 11:15
 * @Version: 1.0
 */
@Controller

@RequestMapping("/UserManagement")
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;

    /**
    *@author LYH
    *@Description   用户显示
    *@Date 2018/4/8 16:17
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lspass>
    **/

    @RequestMapping("/findUser")
    @ResponseBody //将lspassList转换成json输出
    public List<Lspass> findUser()throws Exception{

        List<Lspass> lspassList = userManagementService.findUser() ;
        return  lspassList;
    }
    /**
    *@author LYH
    *@Description 修改用户信息
    *@Date 2018/4/8 16:18
    *@Param [lspass]
    *@return java.lang.String
    **/

    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateLspass(@RequestBody Lspass lspass){
        userManagementService.updateInfo(lspass);
        return "1";
    }

    @RequestMapping("/addInfo")
    @ResponseBody
    /**
    *@author LYH
    *@Description 添加新用户
    *@Date 2018/4/9 16:47
    *@Param [lspass]
    *@return java.lang.Boolean
    **/

    public Boolean addLspass(@RequestBody Lspass lspass){

        return userManagementService.addInfo(lspass);
    }

    @RequestMapping("/delInfo/{userNo}")
    @ResponseBody
    /**
    *@author LYH
    *@Description 删除用户信息
    *@Date 2018/4/9 16:49
    *@Param [lspass]
    *@return java.lang.Boolean
    **/
    public Boolean delLspass(@PathVariable("userNo") String userNo){

        return userManagementService.delInfo(userNo);
    }

    @RequestMapping("/defaultPassword/{userNo}")
    @ResponseBody
    public Boolean defaultPassword(@PathVariable("userNo") String userNo){

        return userManagementService.defaultPassword(userNo);
    }



    @RequestMapping("/saveAuthority/{userNo}/{FunctionAuthority}")
    @ResponseBody
    public String saveAuthority(@PathVariable("userNo")String userNo,@PathVariable("FunctionAuthority")String FunctionAuthority ){

        userManagementService.saveAuthority(userNo,FunctionAuthority);
        return "1";
    }

    @RequestMapping("/getAuthority/{userNo}")
    @ResponseBody
    public String getAuthority(@PathVariable("userNo")String userNo){
        return userManagementService.getAuthority(userNo);
    }

    @RequestMapping("/login/{userNo}/{userPass}")
    @ResponseBody
    public Msg findByUsernameAndPassword(@PathVariable("userNo")String userNo,@PathVariable("userPass")String userPass,HttpSession session ){

        session.setAttribute("userNo",userNo);
        String result = this.userManagementService.findByUsernameAndPassword(userNo,userPass);
        if (!result.equals(" ")){
            return Msg.success().add("userName",result);

        }else {
            return Msg.fail();
        }


    }
    @RequestMapping("/getUserInfo/{userNo}")
    @ResponseBody
    public Msg getUserInfo(@PathVariable("userNo")String userNo ){

        Lspass lspass = userManagementService.getUserInfo(userNo);

        return Msg.success().add("user",lspass);
    }


    /**
    *@author LYH
    *@Description 读取数据库中权限信息
    *@Date 2018/4/23 12:25
    *@Param
    *@return
    **/
    @RequestMapping("/findFunctionAuthority")
    @ResponseBody
    public List<Lsgnbh> findFunctionAuthority(String id){

        return userManagementService.findFunctionAuthority(id);
    }

    /**
    *@author LYH
    *@Description 用户修改个人信息（密码等）
    *@Date 2018/4/27 10:21
    *@Param [lspass]
    *@return java.lang.String
    **/

    @RequestMapping("/updateMsg")
    @ResponseBody
    public String updateMsg(@RequestBody Lspass lspass){
        userManagementService.updateMsg(lspass);
        return "1";
    }


    @RequestMapping("/findAcc")
    @ResponseBody
    public List<Accrecord> findAcc()throws Exception{

        List<Accrecord> accrecords = userManagementService.findAcc() ;
        return  accrecords;
    }

    @RequestMapping("/switchAcc/{id}")
    @ResponseBody
    public String switchAcc(@PathVariable("id") String id)throws Exception{

        userManagementService.switchAcc(id);
        return "1";
    }

    @RequestMapping("/addAcc")
    @ResponseBody
    public Msg addAcc(@RequestBody Accrecord accrecord){

        return userManagementService.addAcc(accrecord);
    }


    @RequestMapping("/updateAcc")
    @ResponseBody
    public Msg updateAcc(@RequestBody Accrecord accrecord){
        return userManagementService.updateAcc(accrecord);
    }

}