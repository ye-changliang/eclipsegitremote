package com.tydic.bigdata.utils;

/**
 * Created by guanchao on 2017/9/6.
 */

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

import static java.lang.System.out;

public class FileTool {
    protected final static Log logger = LogFactory.getLog(FileTool.class);

    /**
     * Description: 向FTP服务器上传文件 add by guancao
     * @Version      1.0
     * @param url FTP服务器hostname
     * @param port  FTP服务器端口
     * @param username FTP登录账号
     * @param password  FTP登录密码
     * @param path  FTP服务器保存目录
     * @param filename  上传到FTP服务器上的文件名
     * @param input   输入流
     * @return 成功返回true，否则返回false *
     */
    public static boolean uploadFile(String url,// FTP服务器hostname
                                     int port,// FTP服务器端口
                                     String username, // FTP登录账号
                                     String password, // FTP登录密码
                                     String path, // FTP服务器保存目录
                                     String filename, // 上传到FTP服务器上的文件名
                                     InputStream input // 输入流
    ){
        boolean success = false;

        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 将本地文件上传到FTP服务器上 * add by guancao
     */
    public static void upLoadFromProduction(String url,// FTP服务器hostname
                                            int port,// FTP服务器端口
                                            String username, // FTP登录账号
                                            String password, // FTP登录密码
                                            String path, // FTP服务器保存目录
                                            String filename, // 上传到FTP服务器上的文件名
                                            String orginfilename // 输入流文件名
    ) {
        try {
            FileInputStream in = new FileInputStream(new File(orginfilename));
            boolean flag = uploadFile(url, port, username, password, path,filename, in);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件分割 add by jy
     * @param src 源文件路径
     * @param fileSize 分割后每个文件的大小，单位是MB
     * @param dest 目标文件路径
     */
    public static void splitFile(String src, int fileSize, String dest){
        logger.info("开始分割文件");

        if("".equals(src)||src==null||fileSize==0||"".equals(dest)||dest==null){
            out.println("分割失败");
        }

        File srcFile = new File(src);//源文件

        long srcSize = srcFile.length();//源文件的大小
        long destSize = 1024*1024*fileSize;//目标文件的大小（分割后每个文件的大小）

        int number = (int)(srcSize/destSize);
        number = srcSize%destSize==0?number:number+1;//分割后文件的数目

        String fileName = src.substring(src.lastIndexOf("\\"));//源文件名

        InputStream in = null;//输入字节流
        BufferedInputStream bis = null;//输入缓冲流
        byte[] bytes = new byte[1024*1024];//每次读取文件的大小为1MB
        int len = -1;//每次读取的长度值
        try {
            in = new FileInputStream(srcFile);
            bis = new BufferedInputStream(in);
            for(int i=0;i<number;i++){

                String destName = dest+ File.separator+fileName+"-"+i+".dat";
                OutputStream out = new FileOutputStream(destName);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int count = 0;
                while((len = bis.read(bytes))!=-1){
                    bos.write(bytes, 0, len);//把字节数据写入目标文件中
                    count+=len;
                    if(count>=destSize){
                        break;
                    }
                }
                bos.flush();//刷新
                bos.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭流
            try {
                if(bis!=null)bis.close();
                if(in!=null)in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件分割,按行读取  add by jy
     * @param src 源文件路径
     * @param fileSize 分割后每个文件的大小，单位是MB
     * @param dest 目标文件路径
     */
    public static void splitFileByLine(String src, int fileSize, String dest){
        logger.info("开始分割文件");

        if("".equals(src)||src==null||fileSize==0||"".equals(dest)||dest==null){
            out.println("分割失败");
        }
        String enter = "\r\n";

        BufferedReader reader=null;
        File srcFile = new File(src);//源文件

        long srcSize = srcFile.length();//源文件的大小
        long destSize = 1024*1024*fileSize;//目标文件的大小（分割后每个文件的大小）

        int number = (int)(srcSize/destSize);
        number = srcSize%destSize==0?number:number+1;//分割后文件的数目

//        String fileName = src.substring(src.lastIndexOf("\\"));//源文件名
        String name = srcFile.getName();
        String fileName = name.substring(0,name.lastIndexOf("."));

        byte[] bytes = new byte[1024*1024];//每次读取文件的大小为1MB
        int len = -1;//每次读取的大小
        try {
            reader=new BufferedReader(new FileReader(srcFile));
            for(int i=0;i<number;i++){

                String destName = dest+ File.separator+fileName+"-"+i+".dat";
                FileWriter fw = new FileWriter(destName);
                BufferedWriter bos = new BufferedWriter(fw);
                int count = 0;
                String temp;
                while((temp=reader.readLine())!=null){
                    bos.write(temp+enter);//把字节数据写入目标文件中
                    len = temp.length();
                    count+=len;
                    if(count>=destSize){
                        break;
                    }
                }
                bos.flush();//刷新
                bos.close();
                fw.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭流
            try {
                if(reader!=null)reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接sftp服务器 add by jy
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static ChannelSftp connect(String host, int port, String username,
                                      String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
        return sftp;
    }

    /**
     * 上传文件 add by jy
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public static boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
        logger.info("上传文件路径:"+directory);

        try {
            logger.info("当前路径:"+sftp.pwd());
            if(!"".equals(directory)){
                sftp.cd(directory);
            }
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
            return true;
        } catch (Exception e) {
            exit(sftp);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件夹是否存在.
     * @param dir 文件夹路径， /xxx/xxx/
     * @param sftp sftp协议
     * @return 是否存在
     */
    public static boolean dirExist(final String dir, final ChannelSftp sftp) {
        try {
            Vector<?> vector = sftp.ls(dir);
            return null != vector;
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 根据路径创建文件夹.
     * @param dir 路径 必须是 /xxx/xxx/xxx/ 不能就单独一个/
     * @param sftp sftp连接
     * @throws Exception 异常
     */
    public static boolean mkdir(final String dir, final ChannelSftp sftp) throws Exception {
        logger.info("创建文件夹:"+dir);
        try {
            if (StringUtils.isBlank(dir))
                return false;
            String md = dir.replaceAll("\\\\", "/");
            if ( md.length() == 1)
                return false;
            return mkdirs(md, sftp);
        } catch (Exception e) {
            logger.info("创建文件夹失败111："+e.getMessage());
            exit(sftp);
            throw e;
        }
    }

    /**
     * 递归创建文件夹.
     * @param dir 路径
     * @param sftp sftp连接
     * @return 是否创建成功
     * @throws SftpException 异常
     */
    private static boolean mkdirs(final String dir, final ChannelSftp sftp) throws SftpException {
        String dirs = dir.substring(1, dir.length() - 1);
        String[] dirArr = dirs.split("/");
        String base = "";
        for (String d : dirArr) {
            base += "/" + d;
            if (dirExist(base + "/", sftp)) {
                continue;
            } else {
                sftp.mkdir(base + "/");
            }
        }
        return true;
    }

    /**
     * 关闭协议-sftp协议.
     * @param sftp sftp连接
     */
    public static void exit(final ChannelSftp sftp) {
        sftp.exit();
    }

    //测试
    public static void main(String[] args) {

        upLoadFromProduction("192.168.13.32", 21, "hanshibo", "han", "韩士波测试", "hanshibo.doc", "E:/temp/H2数据库使用.doc");
    }
}
