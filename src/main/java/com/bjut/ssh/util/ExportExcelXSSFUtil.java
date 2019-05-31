package com.bjut.ssh.util;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExportExcelXSSFUtil {
    private XSSFWorkbook wb = null;

    private XSSFSheet sheet = null;

    public ExportExcelXSSFUtil(XSSFWorkbook wb, XSSFSheet sheet) {
        this.wb = wb;
        this.sheet = sheet;
    }
    /**
     * 合并单元格后给合并后的单元格加边框
     *
     * @param region
     * @param cs
     */
    public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs) {

        int toprowNum = region.getFirstRow();
        for (int i = toprowNum; i <= region.getLastRow(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                XSSFCell cell = row.getCell(j);// XSSFCellUtil.getCell(row,
                // (short) j);
                cell.setCellStyle(cs);
            }
        }
    }

    /**
     * 设置表头的单元格样式
     *
     * @return
     */
    public XSSFCellStyle getHeadStyle() {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格的背景颜色为白色
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,255,255)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置单元格水平居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeight((short) 500);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     *
     * @return
     */
    public XSSFCellStyle getBodyStyle() {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     *
     * @return
     */
    public XSSFCellStyle getBodyStyleWithoutBorder() {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        return cellStyle;
    }

}
