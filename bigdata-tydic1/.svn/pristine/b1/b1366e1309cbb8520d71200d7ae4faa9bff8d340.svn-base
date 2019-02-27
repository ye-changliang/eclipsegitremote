package com.tydic.bigdata.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWrite {
    /**
     * 写文件到磁盘，
     * @param strings
     * @param path 文件所在文件夹路径
     * @param fileName 文件名
     * @return
     */
    public static File fileWrite(String strings,String path,String fileName){
        File dirs=new File(path);
        if(!dirs.exists()){
            dirs.mkdirs();//创建目录
        }
        OutputStreamWriter osw=null;
        File f=new File(path+File.separator+fileName);
        try{
            if(f.exists()){
                System.out.println("文件存在");
                f.createNewFile();
            }
            FileOutputStream fos=null;
            //把java程序和aa.TXT间搭建管道
            /*fos=new FileOutputStream(f);*/
            osw=new OutputStreamWriter(new  FileOutputStream(f),"UTF-8");
            osw.write(strings);
            osw.close();
            //定义一个字符串
            /*fos.write(strings.getBytes());*/
            /*fos.close();*/
        } catch (Exception e) {
            System.out.println("文件生成失敗");
            e.printStackTrace();
        }finally{
            System.out.println("文件生成成功");
        }
        return f;
    }
}
