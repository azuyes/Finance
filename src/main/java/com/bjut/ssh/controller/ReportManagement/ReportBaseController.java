package com.bjut.ssh.controller.ReportManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.ReportManagement.ReportBaseService;
import com.bjut.ssh.util.DatUtil;
import com.bjut.ssh.util.ExportExcelUtil;
import com.bjut.ssh.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ReportBase")
public class ReportBaseController {

    @Autowired
    private ReportBaseService reportBaseService;

    //保存或修改报表
    @RequestMapping(value="/saveOrUpdateRpt")
    @ResponseBody
    public Msg saveOrUpdateRpt(@RequestParam(value="cell_id") String[][] cell_id,
                               @RequestParam(value="cell_data") String[][] cell_data,
                               @RequestParam(value="cell_formula") String[][] cell_formula,
                               @RequestParam(value="formula_range") String[][] formula_range,
                               @RequestParam(value="cell_merge") String[][] cell_merge,
                               @RequestParam(value="font_style") String[][] font_style,
                               @RequestParam(value="cell_className") String[][] cell_className,
                               @RequestParam(value="cell_type") String[][] cell_type,
                               @RequestParam(value="cell_height") String[] cell_height,
                               @RequestParam(value="cell_width") String[] cell_width,
                               @RequestParam(value="rptId") String rptId,
                               @RequestParam(value="rptNo") String rptNo,
                               @RequestParam(value="rptDate") String rptDate,
                               @RequestParam(value="rptName") String rptName,
                               @RequestParam(value="titleRules") String titleRules,
                               @RequestParam(value="headRules") String headRules,
                               @RequestParam(value="bodyRules") String bodyRules,
                               @RequestParam(value="totalRules") String totalRules,
                               @RequestParam(value="rptCol") String rptCol){
        System.out.println("ReportBaseController====saveOrUpdateRpt");
        Lcbzd lcbzd = new Lcbzd();
        if(rptId!=null&&rptId!="")
            lcbzd.setBzdAutoId(Integer.parseInt(rptId));
        lcbzd.setRptNo(rptNo);
        lcbzd.setRptDate(rptDate);
        lcbzd.setRptName(rptName);
        lcbzd.setTitleRules(Integer.parseInt(titleRules));
        lcbzd.setHeadRules(Integer.parseInt(headRules));
        lcbzd.setBodyRules(Integer.parseInt(bodyRules));
        lcbzd.setTotalRules(Integer.parseInt(totalRules));
        lcbzd.setRptCol(Integer.parseInt(rptCol));
        //数据逗号处理
        for (int i = 0; i < cell_data.length; i++) {
            for (int j = 0; j < cell_data[i].length; j++) {
                cell_data[i][j] = cell_data[i][j].replaceAll("#1", ",");
            }
        }
        //公式逗号处理
        for (int i = 0; i < cell_formula.length; i++) {
            for (int j = 0; j < cell_formula[i].length; j++) {
                cell_formula[i][j] = cell_formula[i][j].replaceAll("#1", ",");
            }
        }
        Msg msg = reportBaseService.saveOrUpdateRpt(lcbzd,cell_id,cell_data,cell_formula,formula_range,cell_merge,font_style,cell_className,cell_type,cell_height,cell_width);
        if(msg.getCode()==100) {
            msg.setMsg("保存成功！");
        }else{
            msg.setMsg("保存失败！");
        }
        return msg;
    }

    //检查单个报表是否存在
    @RequestMapping(value="/checkRptByNoAndDate")
    @ResponseBody
    public Msg checkRptByNoAndDate(@RequestParam(value="rptNo") String rptNo,
                               @RequestParam(value="rptDate") String rptDate){
        System.out.println("ReportBaseController====checkRptByNoAndDate");
        Msg msg = new Msg();
        if(reportBaseService.findRptByNoAndDate(rptNo,rptDate)!=null){
            msg.setCode(101);
            msg.setMsg("报表已存在！");
        }else{
            msg.setCode(100);
            msg.setMsg("允许创建报表！");
        }
        return msg;
    }

    //检查多个报表是否存在
    // 返回msg，msg内含存在的报表编号、报表链表，不存在的报表链表
    @RequestMapping(value="/checkRptByNosAndDate")
    @ResponseBody
    public Msg checkRptByNosAndDate(@RequestParam(value="rptNos") String[] rptNos,
                                   @RequestParam(value="rptDate") String rptDate){
        System.out.println("ReportBaseController====checkRptByNosAndDate");
        Msg msg = new Msg();
        String tempRptNos="";
        List<Lcbzd> lcbzdList = new ArrayList<Lcbzd>();
        List<String> unExistRptList = new ArrayList<String>();
        for (int i = 0; i < rptNos.length ; i++) {
            Lcbzd lcbzd = reportBaseService.findRptByNoAndDate(rptNos[i],rptDate);
            if(lcbzd!=null){
                lcbzdList.add(lcbzd);
                tempRptNos=tempRptNos + rptNos[i] + " ";
            }else{
                unExistRptList.add(rptNos[i]);
            }
        }
        if(!tempRptNos.equals("")){
            msg.setCode(101);
            msg.add("lcbzdList",lcbzdList);
            msg.add("unExistRptList",unExistRptList);
            msg.setMsg(tempRptNos);
        }else{
            msg.setCode(100);
            msg.setMsg("允许创建报表！");
        }
        return msg;
    }

    //根据年月查询报表
    @RequestMapping("/findRptByYearAndMonth/{year}/{month}")
    @ResponseBody
    public List<Lcbzd> findRptByDate(@PathVariable("year") String year,@PathVariable("month") String month){
        System.out.println("ReportBaseController====findRptByDate");
        System.out.println("ReportBaseController===="+year+month);
        List<Lcbzd> lcbzdList = reportBaseService.findRptByYearAndMonth(year,month);
        return lcbzdList;
    }

    //打开报表
    @RequestMapping(value="/openRptByNoAndDate")
    @ResponseBody
    public Msg openRptByNoAndDate(@RequestParam(value="rptNo") String rptNo,
                                   @RequestParam(value="rptDate") String rptDate){
        System.out.println("ReportBaseController====openRptByNoAndDate");
        Msg msg = new Msg();
        Lcbzd lcbzd = reportBaseService.findRptByNoAndDate(rptNo,rptDate);
        List<Lcdyzd> lcdyzdList = reportBaseService.findRptDataByNoAndDate(rptNo,rptDate);
        msg.setCode(100);
        msg.add("lcbzd",lcbzd);
        msg.add("lcdyzdList",lcdyzdList);
        return msg;
    }

    //删除报表
    @RequestMapping(value="/delRptByNoAndDate")
    @ResponseBody
    public Msg delRptByNoAndDate(@RequestParam(value="rptNos") String[] rptNos,
                                  @RequestParam(value="rptDate") String rptDate){
        System.out.println("ReportBaseController====delRptByNoAndDate");
        Msg msg = new Msg();
        for(int i=0;i<rptNos.length;i++){
            String rptNo = rptNos[i];
            reportBaseService.delRptByNoAndDate(rptNo,rptDate);
        }
        msg.setCode(100);
        msg.setMsg("删除成功！");
        return msg;
    }

    //转出dat
    @RequestMapping(value="/exportDat")
    @ResponseBody
    public Msg exportDat(@RequestParam(value="rptNos") String[] rptNos,
                         @RequestParam(value="rptDate") String rptDate,
                         @RequestParam(value="rptCont") String[] rptCont,
                         @RequestParam(value="fileName") String fileName,
                         @RequestParam(value="filePath") String filePath){
        System.out.println("ReportBaseController====exportDat");
        Msg msg = new Msg();
        String[] tempPath = filePath.split("\\\\");
        String realPath = "";
        for (int i = 0; i < tempPath.length ; i++) {
            realPath = realPath + tempPath[i]+"\\\\";
        }
        realPath = realPath + fileName + ".dat";
        System.out.println(realPath);
        List<Lcbzd> lcbzdList = reportBaseService.findRptByNoAndDate(rptNos,rptDate);
        List<Lcdyzd> lcdyzdList = reportBaseService.findRptDataByNoAndDate(rptNos,rptDate);
        //Lcbzd导出字段: BZD_AutoId  RptNo  RptDate  RptName  TitleRules  HeadRules  BodyRules  TotalRules  RptCol
        String[][] Rptzd = new String[lcbzdList.size()][9];
        for (int i = 0; i < Rptzd.length ; i++) {
            Lcbzd lcbzd = lcbzdList.get(i);
            Rptzd[i][0] = lcbzd.getBzdAutoId().toString();
            Rptzd[i][1] = lcbzd.getRptNo();
            Rptzd[i][2] = lcbzd.getRptDate();
            Rptzd[i][3] = lcbzd.getRptName();
            Rptzd[i][4] = lcbzd.getTitleRules().toString();
            Rptzd[i][5] = lcbzd.getHeadRules().toString();
            Rptzd[i][6] = lcbzd.getBodyRules().toString();
            Rptzd[i][7] = lcbzd.getTotalRules().toString();
            Rptzd[i][8] = lcbzd.getRptCol().toString();
        }
        //Lcdyzd导出字段:
        // DYZD_AutoId、RptNo、RptDate、RowInfoNo、ColInfoNo、
        // endRow、endCol、cellType、CellContent、Formula、
        // cellClassName、cellWidth、cellHeight、fontStyle、formulaRange
        String[][] data = new String[lcdyzdList.size()][15];
        for (int i = 0; i < data.length; i++) {
            Lcdyzd lcdyzd = lcdyzdList.get(i);
            data[i][0] = lcdyzd.getDyzdAutoId().toString();
            data[i][1] = lcdyzd.getRptNo();
            data[i][2] = lcdyzd.getRptDate();
            data[i][3] = lcdyzd.getRowInfoNo();
            data[i][4] = lcdyzd.getColInfoNo();
            data[i][5] = (lcdyzd.getEndRow() == null || lcdyzd.getEndRow().equals("")) ? "null" : lcdyzd.getEndRow();
            data[i][6] = (lcdyzd.getEndCol() == null || lcdyzd.getEndCol().equals("")) ? "null" : lcdyzd.getEndCol();
            data[i][7] = (lcdyzd.getCellType() == null || lcdyzd.getCellType().equals("")) ? "null" : lcdyzd.getCellType();
            if (lcdyzd.getCellContent() != null && (!lcdyzd.getCellContent().equals("")) &&
                    (lcdyzd.getCellType() == null || lcdyzd.getCellType().indexOf("number") == -1)) {
                data[i][8] = lcdyzd.getCellContent();//报表数据可以赋值并且不是数值列
            } else {
                data[i][8] = "null";
            }
            data[i][9] = "null";
            data[i][10] = (lcdyzd.getCellClassName() == null || lcdyzd.getCellClassName().equals("")) ? "null" : lcdyzd.getCellClassName();
            data[i][11] = lcdyzd.getCellWidth().toString();
            data[i][12] = lcdyzd.getCellHeight().toString();
            data[i][13] = (lcdyzd.getFontStyle() == null || lcdyzd.getFontStyle().equals("")) ? "null" : lcdyzd.getFontStyle();
            data[i][14] = (lcdyzd.getFormulaRange() == null || lcdyzd.getFormulaRange().equals("")) ? "null" : lcdyzd.getFormulaRange();
            for (int j = 0; j < rptCont.length; j++) {
                switch (rptCont[j]) {
                    case "bbsj":
                        if(lcdyzd.getCellContent()!=null && (!lcdyzd.getCellContent().equals("")) )
                            data[i][8] = lcdyzd.getCellContent();
                        break;
                    case "jsgs":
                        if(lcdyzd.getFormula()!=null && (!lcdyzd.getFormula().equals("")) )
                            data[i][9] = lcdyzd.getFormula();
                        break;
                }
            }
        }
        DatUtil.writeDat(rptDate,rptNos,rptCont,Rptzd,data,realPath);
        msg.setCode(100);
        msg.setMsg("转出成功！");
        return msg;
    }

    //导入报表文件dat，前端显示列表
    @RequestMapping(value="/ImportDatList")
    @ResponseBody
    public Msg ImportDatList(@RequestParam(value="datFile") MultipartFile file){
        System.out.println("ReportBaseController====ImportDatList");
        Msg msg = new Msg();
        List<String> fileData = DatUtil.readDat(file);
        String fileName = file.getOriginalFilename();
        int pos = 0;
        if(fileData.size()==0){
            msg.setCode(101);
            msg.setMsg("指定文件错误！");
            return msg;
        }
        List<Lcbzd> lcbzdList = new ArrayList<Lcbzd>();
        List<Lcdyzd> lcdyzdList = new ArrayList<Lcdyzd>();
        String RptDate = fileData.get(pos++);//读取报表日期
        String RptNo = fileData.get(pos++);//读取报表编号
        String[] RptNos = RptNo.split("[ ]");
        String RptCont = fileData.get(pos++);//读取报表内容限制
        for (int i = 0; i < RptNos.length; i++) {
            String RptInfo = fileData.get(pos++);
            String[] RptAttrs = RptInfo.split("[ ]");
            Lcbzd lcbzd = new Lcbzd();
            lcbzd.setBzdAutoId(Integer.parseInt(RptAttrs[0]));
            lcbzd.setRptNo(RptAttrs[1]);
            lcbzd.setRptDate(RptAttrs[2]);
            lcbzd.setRptName(RptAttrs[3]);
            lcbzd.setTitleRules(Integer.parseInt(RptAttrs[4]));
            lcbzd.setHeadRules(Integer.parseInt(RptAttrs[5]));
            lcbzd.setBodyRules(Integer.parseInt(RptAttrs[6]));
            lcbzd.setTotalRules(Integer.parseInt(RptAttrs[7]));
            lcbzd.setRptCol(Integer.parseInt(RptAttrs[8]));
            lcbzdList.add(lcbzd);
        }
        //Lcdyzd导入字段:
        // DYZD_AutoId、RptNo、RptDate、RowInfoNo、ColInfoNo、
        // endRow、endCol、cellType、CellContent、Formula、
        // cellClassName、cellWidth、cellHeight、fontStyle、formulaRange
        while( pos < fileData.size()){
            String RptData = fileData.get(pos++);
            String[] RptDataAttr = RptData.split("[ ]");
            Lcdyzd lcdyzd = new Lcdyzd();
            lcdyzd.setDyzdAutoId(Integer.parseInt(RptDataAttr[0]));
            lcdyzd.setRptNo(RptDataAttr[1]);
            lcdyzd.setRptDate(RptDataAttr[2]);
            lcdyzd.setRowInfoNo(RptDataAttr[3]);
            lcdyzd.setColInfoNo(RptDataAttr[4]);
            if( RptDataAttr[5]!=null && !RptDataAttr[5].equals("null"))
                lcdyzd.setEndRow(RptDataAttr[5]);
            if( RptDataAttr[6]!=null && !RptDataAttr[6].equals("null"))
                lcdyzd.setEndCol(RptDataAttr[6]);
            if( RptDataAttr[7]!=null && !RptDataAttr[7].equals("null"))
                lcdyzd.setCellType(RptDataAttr[7]);
            if( RptDataAttr[8]!=null && !RptDataAttr[8].equals("null"))
                lcdyzd.setCellContent(RptDataAttr[8]);
            if( RptDataAttr[9]!=null && !RptDataAttr[9].equals("null"))
                lcdyzd.setFormula(RptDataAttr[9]);
            if( RptDataAttr[10]!=null && !RptDataAttr[10].equals("null"))
                lcdyzd.setCellClassName(RptDataAttr[10]);
            if( RptDataAttr[11]!=null && !RptDataAttr[11].equals("null"))
                lcdyzd.setCellWidth(Integer.parseInt(RptDataAttr[11]));
            if( RptDataAttr[12]!=null && !RptDataAttr[12].equals("null"))
                lcdyzd.setCellHeight(Integer.parseInt(RptDataAttr[12]));
            if( RptDataAttr[13]!=null && !RptDataAttr[13].equals("null"))
                lcdyzd.setFontStyle(RptDataAttr[13]);
            if( RptDataAttr[14]!=null && !RptDataAttr[14].equals("null"))
                lcdyzd.setFormulaRange(RptDataAttr[14]);
            lcdyzdList.add(lcdyzd);
        }
        msg.add("lcbzdList",lcbzdList);
        msg.add("lcdyzdList",lcdyzdList);
        msg.add("fileName",fileName);
        msg.setCode(100);
        return msg;
    }

    //转入dat数据
    @RequestMapping(value="/ImportDatData")
    @ResponseBody
    public Msg ImportDatData(@RequestParam(value="isExist") String isExist,//已存在报表编号
                             @RequestParam(value="rptInfo") String[][] rptInfo,
                             @RequestParam(value="rptData") String[][] rptData,
                             @RequestParam(value="rptNos") String[] rptNos,
                             @RequestParam(value="rptDate") String rptDate,
                             @RequestParam(value="rptCont") String[] rptCont){
        System.out.println("ReportBaseController====ImportDatData");
        Msg msg = new Msg();
        if(reportBaseService.importDat(isExist,rptInfo,rptData,rptNos,rptDate,rptCont)){
            msg.setCode(100);
            msg.setMsg("转入成功！");
        }else{
            msg.setCode(101);
            msg.setMsg("转入失败！");
        }
        return msg;
    }

    //复制报表--覆盖已存在报表
    @RequestMapping(value="/copyRpt")
    @ResponseBody
    public Msg copyRpt(@RequestParam(value="isExistArr") Boolean[] isExistArr,
                            @RequestParam(value="copyDate") String copyDate,
                            @RequestParam(value="isClearData") Boolean isClearData,
                            @RequestParam(value="fromRptDate") String fromRptDate,
                            @RequestParam(value="fromRptNo") String[] fromRptNo){
        System.out.println("ReportBaseController====copyRpt");
        Msg msg = new Msg();
        boolean suc = true;
        for (int i = 0; isExistArr!=null && i < isExistArr.length; i++) {
            //删除已存在报表
            if(isExistArr[i])
                reportBaseService.delRptByNoAndDate(fromRptNo[i],copyDate);
            //查找报表信息Lcbzd放入list存储
            Lcbzd lcbzd = reportBaseService.findRptByNoAndDate(fromRptNo[i],fromRptDate);
            List<Lcbzd> lcbzdList= new ArrayList<Lcbzd>();
            lcbzdList.add(lcbzd);
            //查找报表数据Lcdyzdlist
            List<Lcdyzd> lcdyzdList = reportBaseService.findRptDataByNoAndDate(fromRptNo[i],fromRptDate);
            //重置id和日期
            lcbzd.setBzdAutoId(null);
            lcbzd.setRptDate(copyDate+"-01");
            for (int j = 0; j < lcdyzdList.size(); j++) {
                lcdyzdList.get(j).setDyzdAutoId(null);
                lcdyzdList.get(j).setRptDate(copyDate+"-01");
                if(isClearData&&lcdyzdList.get(j).getCellType()!=null&&lcdyzdList.get(j).getCellType().indexOf("number")!=-1){
                    lcdyzdList.get(j).setCellContent(null);
                    lcdyzdList.get(j).setFormula(null);
                }
            }
            suc = reportBaseService.saveOrUpdateRpt(lcbzdList,lcdyzdList);
            if(suc==false)
                break;
        }
        reportBaseService.setSystemDate(copyDate);
        //存入数据库
        if(suc){
            msg.setCode(100);
            msg.setMsg("结转成功！");
        }else{
            msg.setCode(101);
            msg.setMsg("结转失败！");
        }
        return msg;
    }

    //修改财务系统日期
    // sysDate:日期、格式：20**-**
    @RequestMapping(value="/updateSystemDate")
    @ResponseBody
    public Msg updateSystemDate(@RequestParam(value="sysDate") String sysDate){
        System.out.println("ReportBaseController====updateSystemDate");
        Msg msg = new Msg();
        reportBaseService.setSystemDate(sysDate);
        msg.setCode(100);
        msg.setMsg("结转成功！");
        return msg;
    }

    //年度结转
    @RequestMapping(value="/yearCopyRpt")
    @ResponseBody
    public Msg yearCopyRpt(@RequestParam(value="copyYear") String copyYear,
                           @RequestParam(value="fromRptNo") String[] fromRptNo,
                           @RequestParam(value="isClearData") Boolean isClearData){
        System.out.println("ReportBaseController====yearCopyRpt");
        Msg msg = new Msg();
        boolean suc;
        suc = reportBaseService.yearCopy(copyYear,fromRptNo,isClearData);
        if(suc){
            msg.setCode(100);
            msg.setMsg("结转成功！");
        }else{
            msg.setCode(101);
            msg.setMsg("结转失败！");
        }
        return msg;
    }

    //导出Excel
    @RequestMapping(value="/ExportRptExcel/{rptDate}/{rptNo}",method = RequestMethod.GET)
    @ResponseBody
    public void ExportRptExcel(@PathVariable("rptDate") String rptDate,@PathVariable("rptNo") String rptNo,HttpServletResponse response){
        try {
            System.out.println("ReportBaseController====ExportRptExcel");
            ServletOutputStream outputStream = response.getOutputStream();
            Lcbzd lcbzd = reportBaseService.findRptByNoAndDate(rptNo,rptDate);
            List<Lcdyzd> lcdyzdList = reportBaseService.findRptDataByNoAndDate(rptNo,rptDate);
            String fileName=lcbzd.getRptName()+"_"+lcbzd.getRptDate().substring(0,7)+".xlsx";
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("utf-8");
            //表头
            String title = lcbzd.getRptName();
            //行列数
            int rows = lcbzd.getTotalRules();
            int cols = lcbzd.getRptCol();
            //表格
            String[][] excelData = new String[rows][cols];
            //将lcdyzdList中的每一条数据取出来按行列放到excelData中
            for (int i = 0; i < lcdyzdList.size() ; i++) {
                Lcdyzd lcdyzd = lcdyzdList.get(i);
                int rowTemp = Integer.parseInt(lcdyzd.getRowInfoNo());
                int colTemp = Integer.parseInt(lcdyzd.getColInfoNo());
                excelData[rowTemp][colTemp]=lcdyzd.getCellContent()==null?"":lcdyzd.getCellContent();
            }
            ExportExcelUtil.ExportSingleHeadExcel(title, excelData, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取报表系统当前日期
    @RequestMapping(value="/getRptDateNow")
    @ResponseBody
    public Msg getRptDateNow(){
        System.out.println("ReportBaseController====getRptDateNow");
        Msg msg = new Msg();
        String rptDate = reportBaseService.getRptDateNow();
        //存入数据库
        if(rptDate!=null){
            msg.setCode(100);
            msg.setMsg(rptDate);
        }else{
            msg.setCode(101);
            msg.setMsg("请设置报表日期！");
        }
        return msg;
    }

    //计算报表公式
    @RequestMapping(value="/calculateFormula")
    @ResponseBody
    public Msg calculateFormula(
                                @RequestParam(value="cell_data") String[][] cell_data,
                                @RequestParam(value="cell_formula") String[][] cell_formula,
                                @RequestParam(value="cell_type") String[][] cell_type,
                                @RequestParam(value="formula_range") String[][] formula_range,
                                @RequestParam(value="rptNo") String rptNo,
                                @RequestParam(value="rptDate") String rptDate,
                                @RequestParam(value="titleRules") String titleRules,
                                @RequestParam(value="headRules") String headRules
                                ){
        System.out.println("ReportBaseController====calculateFormula");
        Msg msg = new Msg();
        int yearNow = Integer.parseInt(rptDate.substring(0, 4));
        int monthNow = Integer.parseInt(rptDate.substring(5, 7));
        int startRow = Integer.parseInt(titleRules) + Integer.parseInt(headRules)-1;
        //公式逗号处理
        for (int i = 0; i < cell_formula.length; i++) {
            for (int j = 0; j < cell_formula[i].length; j++) {
                cell_formula[i][j] = cell_formula[i][j].replaceAll("#1", ",");
            }
        }
        for (int i = 0; i < cell_formula.length; i++) {
            for (int j = 0; j < cell_formula[i].length; j++) {
                if(cell_formula[i][j]==null||cell_formula[i][j].trim().equals("")||cell_type[i][j].trim().equals("project")){
                    continue;
                }else{
                    cell_formula[i][j] = cell_formula[i][j].trim().toUpperCase();//小写转大写
                    //转换SUM（A1：B1）为SUM(BB(A1:B1))
                    for(int k=cell_formula[i][j].indexOf("SUM(");k<cell_formula[i][j].length()-3&&k>=0;){
                        if(cell_formula[i][j].charAt(k+4)>='A'&&cell_formula[i][j].charAt(k+4)<='Z'){
                            String preStr = cell_formula[i][j].substring(0,k+4);
                            String midStr = cell_formula[i][j].substring(k+4).substring(0,cell_formula[i][j].substring(k+4).indexOf(')'));
                            String aftStr = cell_formula[i][j].substring(k+4).substring(cell_formula[i][j].substring(k+4).indexOf(')'));
                            cell_formula[i][j] = preStr + "BB(" + midStr + ")" + aftStr;
                        }
                        k=cell_formula[i][j].substring(k+4).indexOf("SUM(");
                    }
                    String[][] resultArray = null;
                    if(cell_formula[i][j].contains("DATE")){
                        resultArray=new String[1][1];
                        resultArray[0][0]=reportBaseService.getFormulaDate(cell_formula[i][j],yearNow,monthNow);
                    }else{
                        resultArray=reportBaseService.calculateFormula(cell_formula[i][j].trim(),yearNow,monthNow,rptNo,cell_data);
                    }
                    int row_temp = 0, col_temp = 0;
                    if(resultArray!=null&&resultArray.length>0){//给cell_data赋值
                        int[] cellRange = FormulaUtil.getRange(formula_range[i][j]);
                        for (int k = cellRange[0]; k <=cellRange[2] && k<cell_data.length ; k++) {
                            for (int l = cellRange[1]; l <=cellRange[3] && l<cell_data[k].length ; l++) {
                                cell_data[k][l] = null == resultArray[row_temp][col_temp] ? "0" : resultArray[row_temp][col_temp];
                                col_temp = (col_temp + 1) % resultArray[0].length;
                            }
                            row_temp = (row_temp + 1) % resultArray.length;
                        }
                    }else
                        continue;
                }
            }
        }
        msg.add("resultData", cell_data);
        msg.setCode(100);
        msg.setMsg("计算成功！");
        return msg;
    }

    //获取所有核算字典记录
    @RequestMapping("/findAllHszd")
    @ResponseBody
    public List<Lshszd> findAllHszd(){
        System.out.println("ReportBaseController====findAllHszd");
        List<Lshszd> lshszdList = reportBaseService.findAllHszd();
        return lshszdList;
    }

    //获取所有核算字典记录
    @RequestMapping("/findAllWldw")
    @ResponseBody
    public List<Lswldw> findAllWldw(){
        System.out.println("ReportBaseController====findAllWldw");
        List<Lswldw> lswldwList = reportBaseService.findAllWldw();
        return lswldwList;
    }

}
