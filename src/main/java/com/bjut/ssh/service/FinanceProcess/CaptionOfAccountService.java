package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.*;

import java.util.List;

/**
 * @Title: CaptionOfAccountService
 * @Description: 科目字典维护服务
 * @Author: czh
 * @CreateDate: 2018/4/10 13:40
 * @Version: 1.0
 */
public interface CaptionOfAccountService {
    public String getSubjectStructure();
    public Msg queryAllCaptionOfAccountByLevel(String id, String levelFlag);
    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag);
    public Msg addAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo);
    public Msg addCaptionOfAccount(Lskmzd lskmzd);
    public Msg delCaptionOfAccount(String itemNo, String level, String is_quan);
    public Msg addCaptionOfAccountQuantity(Lskmsl lskmsl);
    public Msg getConfigsForCap();
    public String getBeginMonth();
    public List<String> getPrecisions();
    public List<Lshsfl> querySpecialAcc();
    public Msg editAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo);
    public Msg editCaptionOfAccount(Lskmzd lskmzd);
    public Msg editCaptionOfAccountQty(Lskmsl lskmsl);
    public List<LskmzdQueryVo> getCaptionWithSpAcc(String id, String levelFlag);
}
