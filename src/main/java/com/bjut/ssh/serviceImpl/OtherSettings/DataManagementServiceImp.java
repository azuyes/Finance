package com.bjut.ssh.serviceImpl.OtherSettings;

import com.bjut.ssh.dao.OtherSettings.DataManagementDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.OtherSettings.DataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 * @Title: DataManagementServiceImp
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/4/13 10:55
 * @Version: 1.0
 */
@Service
@Transactional
public class DataManagementServiceImp implements DataManagementService {

    @Autowired
    private DataManagementDao dataManagementDao;

    @Override
    public void changeDatabase(String datasource){
        dataManagementDao.changeDatabase(datasource);
    }

    @Override
    public void createDatabase(String name, HttpServletRequest request, HttpServletResponse response){
        dataManagementDao.createDatabase(name, request, response);
    }

    @Override
    public void addLswlfl(Lswlfl lswlfl) {
        dataManagementDao.addLswlfl(lswlfl);
    }

    @Override
    public void addLsconf(Lsconf lsconf) {
        dataManagementDao.addLsconf(lsconf);
    }

    @Override
    public void addLspzbh(Lspzbh lspzbh) {
        dataManagementDao.addLspzbh(lspzbh);
    }

    @Override
    public void addLskmzd(Lskmzd lskmzd) {
        dataManagementDao.addLskmzd(lskmzd);
    }

    @Override
    public void addLskmsl(Lskmsl lskmsl) {
        dataManagementDao.addLskmsl(lskmsl);
    }

    @Override
    public void addLszczd(Lszczd lszczd) {
        dataManagementDao.addLszczd(lszczd);
    }

    @Override
    public void addLcbzd(Lcbzd lcbzd) {
        dataManagementDao.addLcbzd(lcbzd);
    }

    @Override
    public void addLszcdy(Lszcdy lszcdy) {
        dataManagementDao.addLszcdy(lszcdy);
    }

    @Override
    public void addLcdyzd(Lcdyzd lcdyzd) {
        dataManagementDao.addLcdyzd(lcdyzd);
    }

    @Override
    public void addLsszzd(Lsszzd lsszzd) {
        dataManagementDao.addLsszzd(lsszzd);

    }

    @Override
    public void addLspzk1(Lspzk1 lspzk1) {
        dataManagementDao.addLspzk1(lspzk1);
    }

    @Override
    public void addLsyspz(Lsyspz lsyspz) {
        dataManagementDao.addLsyspz(lsyspz);
    }

    @Override
    public void addLshspz(Lshspz lshspz) {
        dataManagementDao.addLshspz(lshspz);
    }

    @Override
    public void addLshssl(Lshssl lshssl) {
        dataManagementDao.addLshssl(lshssl);
    }

    @Override
    public void addLshsje(Lshsje lshsje) {
        dataManagementDao.addLshsje(lshsje);
    }

    @Override
    public void addLshsfl(Lshsfl lshsfl) {
        dataManagementDao.addLshsfl(lshsfl);
    }

    @Override
    public void addLshszd(Lshszd lshszd) {
        dataManagementDao.addLshszd(lshszd);
    }

    @Override
    public void addLswlje(Lswlje lswlje) {
        dataManagementDao.addLswlje(lswlje);
    }

    @Override
    public void addLswlsl(Lswlsl lswlsl) {
        dataManagementDao.addLswlsl(lswlsl);
    }

    @Override
    public void addLswldw(Lswldw lswldw) {
        dataManagementDao.addLswldw(lswldw);
    }

}