package com.tydic.bigdata.utils;

        import java.io.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Properties;
        import java.util.Vector;

        import com.jcraft.jsch.*;

/**
 * sftp相关工具类
 */
public class SFTPTool {
    private static Session session;
    private static Channel channel;
    private static ChannelSftp sftp;// sftp操作类

    /**
     * 连接sftp
     * 构造函数
     * @param host
     * @param port
     * @param username
     * @param password
     * @throws Exception
     */
    public SFTPTool(String host, int port, String username, String password) throws Exception {
        getConnect(host, port, username, password);
    }


    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 密码
     * @return
     * @throws Exception
     */
    private static ChannelSftp getConnect(String host, int port, String username, String password) throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
        session.setConfig(config);
        try {
            session.connect();
        } catch (Exception e) {
            if (session.isConnected())
                session.disconnect();
            throw new Exception("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + username + "],端口[" + port
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");
        }
        channel = session.openChannel("sftp");
        try {
            channel.connect();
        } catch (Exception e) {
            if (channel.isConnected())
                channel.disconnect();
            throw new Exception("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + username + "],密码[" + password
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");
        }
        sftp = (ChannelSftp) channel;

        return sftp;
    }

    /**
     * 断开连接
     */
    private static void disConn(){
        if(null != sftp){
            sftp.disconnect();
            sftp.exit();
            sftp = null;
        }
        if(null != channel){
            channel.disconnect();
            channel = null;
        }
        if(null != session){
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件,同时断开连接
     * @param directory  上传指定的路径
     * @param uploadFile 需要上传的文件的路径级文件名
     * @throws Exception
     */
    public void upload(String directory,String uploadFile) throws Exception {
        try {
            if (!directory.equals("") && directory.trim() != "") {
                try{
                    sftp.cd(directory);
                }catch(SftpException sException){
                    if(sftp.SSH_FX_NO_SUCH_FILE == sException.id){ //指定上传路径不存在
                        sftp.mkdir(directory);
                        sftp.cd(directory);
                    }
                }
            }
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e);
        } finally{
            disConn();
        }
    }


    /**
     * 获得文件流
     *
     * @Title: get
     * @param directory
     * @return
     * @throws Exception
     */
    public InputStream getInputStream(String directory,String fileName) throws Exception {
        sftp.cd(directory);
        InputStream streatm = sftp.get(fileName);
        disConn();
        return streatm;
    }

    /**
     * 下载文件,同时断开连接
     * @param directory 下载目录
     * @param downloadFile 下载的文件名
     * @param saveFile 存在本地的全路径
     * @throws Exception
     */
    public void download(String directory, String downloadFile,String saveFile) throws Exception {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            boolean bFile;
            bFile = false;
            bFile = file.exists();
            if (!bFile) {
                bFile = file.mkdirs();
            }
            sftp.get(downloadFile, new FileOutputStream(new File(saveFile,downloadFile)));
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e);
        } finally{
            disConn();
        }
    }
    public static final String NO_FILE = "No such file";
    /**
     * 载单个文件
     * @param directory       ：远程下载目录(以路径符号结束)
     * @param remoteFileName  FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     * @param localFile       本地文件路径 如 D:\\xxx.txt
     * @return
     * @throws
     */
    public File downloadFile(String directory, String remoteFileName,String localFile) throws Exception {
        File file = null;
        OutputStream output = null;
        try {
            file = new File(localFile);
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            sftp.cd(directory);
            output = new FileOutputStream(file);
            sftp.get(remoteFileName, output);
        }
        catch (SftpException e) {
            if (e.toString().equals(NO_FILE)) {
                throw new Exception("FtpUtil-->downloadFile--ftp下载文件失败" + directory +remoteFileName + "不存在");
            }
            throw new Exception("ftp目录或者文件异常，检查ftp目录和文件" + e.toString());
        }
        catch (FileNotFoundException e) {
            throw new Exception("本地目录异常，请检查" + file.getPath() + e.getMessage());
        }
        catch (IOException e) {
            throw new Exception("创建本地文件失败" + file.getPath() + e.getMessage());
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (IOException e) {
                    throw new Exception("Close stream error."+ e.getMessage());
                }
            }
            disConn();
        }
        return file;
    }


    /**
     * 列出目录下的文件
     * @param directory 要列出的文件
     * @return
     * @throws SftpException
     */
    public Vector<ChannelSftp.LsEntry>  listFiles(String directory)throws SftpException {
        Vector<ChannelSftp.LsEntry>  vec = sftp.ls(directory);
        disConn();
        return vec;
    }

    /**
     * 删除文件,同时断开连接
     * @param directory 要删除文件所在目录
     * @param deleteFile  要删除的文件名
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws Exception {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e);
        } finally{
            disConn();
        }
    }

    /**
     * get SFTP Server file list .
     *@param
     *@return List<ChannelSftp.LsEntry>
     * @throws SftpException
     * @throws JSchException
     * @throws InterruptedException
     */
    public  List<ChannelSftp.LsEntry> getFiles(String directory) throws Exception {
        List<ChannelSftp.LsEntry> files = new ArrayList<ChannelSftp.LsEntry>();
                Vector<ChannelSftp.LsEntry> fs = null;
                fs = sftp.ls(directory);
                for (ChannelSftp.LsEntry entry : fs) {
                    if (!entry.getAttrs().isDir()) {
                        files.add(entry);
                    }
                }
        return files;
    }
    /**
     * 用于查询某个远程目录下的文件名称
     * @param directory 远程目录
     * @param valiStr
     * @return
     * @throws SftpException
     */
    public Vector<String> listFileNames(String directory, String valiStr)
            throws SftpException {
        Vector<String> ret = new Vector<String>();
        System.out.println("------");
        for (Object obj : sftp.ls(directory)) {
            String curStr = obj.toString();
            curStr = curStr.substring(curStr.lastIndexOf(" ")+1);

            if(curStr.contains(valiStr)){
                ret.add(curStr);
            }
        }
        disConn();
        return ret;
    }
}

