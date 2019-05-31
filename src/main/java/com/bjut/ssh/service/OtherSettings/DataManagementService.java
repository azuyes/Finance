package com.bjut.ssh.service.OtherSettings;

import com.bjut.ssh.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: DataManagementService
 * @Description: 数据管理
 * @Author: LYH
 * @CreateDate: 2018/4/13 10:54
 * @Version: 1.0
 */

public interface DataManagementService {
    public void changeDatabase(String datasource);

    public void createDatabase(String name, HttpServletRequest request, HttpServletResponse response);

        //转入数据表
    public void addLswlfl(Lswlfl lswlfl);

    public void addLsconf(Lsconf lsconf);

    public void addLspzbh(Lspzbh lspzbh);

    public void addLskmzd(Lskmzd lskmzd);

    public void addLskmsl(Lskmsl lskmsl);

    public void addLszczd(Lszczd lszczd);

    public void addLcbzd(Lcbzd lcbzd);

    public void addLszcdy(Lszcdy lszcdy);

    public void addLcdyzd(Lcdyzd lcdyzd);

    public void addLsszzd(Lsszzd lsszzd);

    public void addLspzk1(Lspzk1 lspzk1);

    public void addLsyspz(Lsyspz lsyspz);

    public void addLshspz(Lshspz lshspz);

    public void addLshssl(Lshssl lshssl);

    public void addLshsje(Lshsje lshsje);

    public void addLshsfl(Lshsfl lshsfl);

    public void addLshszd(Lshszd lshszd);

    public void addLswlje(Lswlje lswlje);

    public void addLswlsl(Lswlsl lswlsl);

    public void addLswldw(Lswldw lswldw);
}