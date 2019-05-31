package com.bjut.ssh.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatUtil {

    public static void writeDat(String[][] data,String filePath){
        File file = new File(filePath);
        //判断文件夹是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
            }
        }
        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bufw = new BufferedWriter(fw);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    bufw.write(data[i][j]+" ");
                }
                bufw.newLine();
            }
            bufw.close();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeDat(String BBDate,String[] BBnos,String[] BBcont,String[][] bbzd,String[][] data,String filePath){
        File file = new File(filePath);
        //判断文件夹是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
            }
        }
        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bufw = new BufferedWriter(fw);
            bufw.write(BBDate);//打印日期
            bufw.newLine();
            //打印报表编号
            for (int i = 0; i < BBnos.length; i++) {
                bufw.write(BBnos[i]+" ");
            }
            bufw.newLine();
            //打印报表内容要求
            for (int i = 0; i < BBcont.length; i++) {
                bufw.write(BBcont[i]+" ");
            }
            bufw.newLine();
            //打印报表定义 lcbzd
            for (int i = 0; i < bbzd.length; i++) {
                for (int j = 0; j < bbzd[i].length; j++) {
                    bufw.write(bbzd[i][j]+" ");
                }
                bufw.newLine();
            }
            //打印数据 lcdyzd
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    bufw.write(data[i][j]+" ");
                }
                bufw.newLine();
            }
            bufw.close();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static List<String> readDat(MultipartFile file){
        List<String> list = new ArrayList<String>();
        try {
            //判断文件是否为空
            if(file == null){
                return null;
            }
            String name = file.getOriginalFilename();
            long size = file.getSize();
            if(name == null || "".equals(name) && size==0){
                return null;
            }
            //得到字节流
            InputStream in = file.getInputStream();
            //将字节流转化成字符流
            InputStreamReader isr = new InputStreamReader(in);
            //将字符流以缓存的形式一行一行输出
            BufferedReader bf = new BufferedReader(isr);
            String newLine = bf.readLine();
            while(newLine != null && !newLine.equals("")){
                list.add(newLine);
                newLine = bf.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
