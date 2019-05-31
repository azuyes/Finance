package com.bjut.ssh.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcelUtil {
    /**
     * 导出多标题的EXCEL
     * @param titles
     * @param list
     * @param outputStream
     */
    public static void ExportMultiHeadExcel(String[] titles, ArrayList<List<Object>> list, ServletOutputStream outputStream) {
        // 创建一个workbook 对应一个excel应用文件
        Workbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        //Sheet名称，可以自定义中文名称
        XSSFSheet sheet = (XSSFSheet) workBook.createSheet("Sheet1");
        ExportExcelXSSFUtil exportUtil = new ExportExcelXSSFUtil((XSSFWorkbook) workBook, sheet);
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;

        // 输出标题
        for (int i = 0; i < titles.length; i++) {
            //设置列宽
            sheet.setColumnWidth(i, 4000);
            //自动列宽
//        	sheet.autoSizeColumn(i,true);
            cell = headRow.createCell(i);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(titles[i]);
        }
        // 构建表体数据
        for (int j = 0; j < list.size(); j++) {
            XSSFRow bodyRow = sheet.createRow(j + 1);
            List<Object> rowList = list.get(j);
            for (int k = 0; k < rowList.size(); k++) {
                //设置列宽自适应
//            	sheet.autoSizeColumn(k,true);
                cell = bodyRow.createCell(k);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowList.get(k)+"");
            }
        }
        try {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出单标题的EXCEL
     * @param title
     * @param list
     * @param outputStream
     */
    public static void ExportSingleHeadExcel(String title, ArrayList<List<Object>> list, ServletOutputStream outputStream) {
        // 创建一个workbook 对应一个excel应用文件
        Workbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        //Sheet名称，可以自定义中文名称
        XSSFSheet sheet = (XSSFSheet) workBook.createSheet("Sheet1");
        ExportExcelXSSFUtil exportUtil = new ExportExcelXSSFUtil((XSSFWorkbook) workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        //构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        //输出标题
        //设置列宽
        for (int i = 0; i < list.get(0).size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        //创建(0，0)单元格
        cell = headRow.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue(title);
        sheet.addMergedRegion(new CellRangeAddress(
            0,//第一行（基于0）
            0,//最后一行（从0开始）
            0,//第一列（基于0）
            list.get(0).size()-1 //最后一列（基于0）
        ));
        for (int j = 0; j < list.size(); j++) {
            XSSFRow bodyRow = sheet.createRow(j + 1);
            List<Object> rowList = list.get(j);
            for (int k = 0; k < rowList.size(); k++) {
                //设置列宽自适应
            	sheet.autoSizeColumn(k,true);
                cell = bodyRow.createCell(k);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(rowList.get(k)+"");
            }
        }
        try {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 导出单标题的EXCEL
     * @param title
     * @param excelData
     * @param outputStream
     */
    public static void ExportSingleHeadExcel(String title, String[][] excelData, ServletOutputStream outputStream) {
        // 创建一个workbook 对应一个excel应用文件
        Workbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        //Sheet名称，可以自定义中文名称
        XSSFSheet sheet = (XSSFSheet) workBook.createSheet("Sheet1");
        ExportExcelXSSFUtil exportUtil = new ExportExcelXSSFUtil((XSSFWorkbook) workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        //构建表头
        XSSFRow headRow = sheet.createRow(0);
        headRow.setHeight((short)600);
        XSSFCell cell = null;
        //设置列宽
        for (int i = 0; i < excelData[0].length; i++) {
            sheet.setColumnWidth(i, 4000);
        }
        //创建(0，0)单元格
        cell = headRow.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue(title);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(
                0,//第一行（基于0）
                0,//最后一行（从0开始）
                0,//第一列（基于0）
                excelData[0].length-1 //最后一列（基于0）
        ));
        for (int j = 1; j < excelData.length; j++) {
            XSSFRow bodyRow = sheet.createRow(j);
            for (int k = 0; k < excelData[j].length; k++) {
                cell = bodyRow.createCell(k);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(excelData[j][k]+"");
            }
        }
        try {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}