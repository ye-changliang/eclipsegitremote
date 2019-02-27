package com.tydic.bigdata.utils;

import java.io.*;

public class FileBack {
    public static String BACKUP_SUFFIX =".bak";

    /**
     * 实现文件复制的函数
     *
     * 采用二进制流的形式来实现文件的读写
     */
    public static void fileCopy(File srcFile, File destFile) throws Exception{
        InputStream src = new BufferedInputStream(new FileInputStream(srcFile));
        OutputStream dest = new BufferedOutputStream(new FileOutputStream(destFile));

        byte[] trans = new byte[1024];

        int count = -1;

        while((count = src.read(trans)) != -1){
            dest.write(trans, 0, count);
        }

        dest.flush();
        src.close();
        dest.close();
    }

    /**
     * 备份文件，在原文件目录下创建备份文件，命名为 原文件名.bak
     * @param templateFile 需要备份的文件
     * @return true 成功，false 失败
     */
    public static File backupTemplateFile(String templateFile,String fileSuffix){
        File srcFile = new File(templateFile+fileSuffix);
        if(!srcFile.exists()){
            System.out.println("模板文件不存在");
            return null;
        }

        //创建备份文件
        File backUpFile = new File(templateFile+BACKUP_SUFFIX+fileSuffix);
        try {
            if(backUpFile.createNewFile()){
                //创建备份文件成功，进行文件复制
                fileCopy(srcFile, backUpFile);
            }
        } catch (Exception e) {
            System.out.println("备份文件失败");
            return null;
        }
        return backUpFile;
    }
    public static void main(String[] args) {
        /*FileBack.backupTemplateFile("E:\\configFile\\测试.txt");*/
    }
}
