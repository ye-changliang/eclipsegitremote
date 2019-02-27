package com.tydic.bigdata.utils;

import java.io.File;

public class FileDelet {
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    public static void main(String[] args) {
          // 删除单个文件
          String file = "E:\\configFile\\222";
          FileDelet.deleteFile(file);
          System.out.println();
    }

}
