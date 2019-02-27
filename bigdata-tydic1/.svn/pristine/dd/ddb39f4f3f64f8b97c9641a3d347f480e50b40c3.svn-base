package com.tydic.bigdata.service.spark;

import com.jcraft.jsch.ChannelSftp;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.spark.Spark;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
import com.tydic.bigdata.repository.spark.SparkRepository;
import com.tydic.bigdata.ssh.SSHClient;
import com.tydic.bigdata.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SparkService {
    @Value(value = "${filePath}")
    private String filePath;

    @Value(value = "${fileDownPath}")
    private String fileDownPath;
    @Autowired
    private SparkRepository sparkRepository;

    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private HostInfoRepository hostInfoRepository;

    /**
     * 查询spark配置文件列表,应用名不为空
     * @param pageable
     * @param spark
     * @return
     */
    public Page<Spark> findSparkByIpAndConfigNameAndAppNameIsNotNull(Pageable pageable, Spark spark){
        return  sparkRepository.findAllByConfigTypeAndIpContainingAndAppNameContainingAndSparkConfigContainingAndAppNameIsNotNullOrderByUpdateDateDesc(spark.getConfigType(),spark.getIp(),spark.getAppName(),spark.getSparkConfig(),pageable);
    }

    /**
     * 查询spark配置文件列表,应用名为空
     * @param pageable
     * @param spark
     * @return
     */
    public Page<Spark> findSparkByIpAndConfigTypeAndAppNameIsNull(Pageable pageable, Spark spark){
        return  sparkRepository.findAllByConfigTypeAndIpContainingAndAppNameIsNullOrderByUpdateDateDesc(spark.getConfigType(),spark.getIp(),pageable);
    }
    /**
     * 查询spark监控列表
     */
    public List<Spark> findSparkAndGroupByIp(){
        return sparkRepository.findAllByConfigTypeAndIp();
    }


    /**
     * 保存服务及配置
     */
    public boolean saveSpark(Spark spark) throws Exception {
        if(spark!=null){
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            spark.setIp(hostInfo.getHost());
            spark.setSparkConfigPath(hostServeInfo.getHostServePath());
            spark.setRent(hostServeInfo.getRent());
                spark.setUpdateDate(new Date());
                File file = newConfigFile(spark);//生成文件
                SFTPTool sftpTool = new SFTPTool(spark.getIp(), Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
                sftpTool.upload(spark.getSparkConfigPath(), file.getPath());//发送文件给服务器*/
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String CRChangToLF="cd "+hostServeInfo.getHostServePath()+" \n sed -i 's/\\r//' "+spark.getSparkConfig();//window换行符转换为lunix
            client.execCmdWithBack(CRChangToLF);
            FileDelete.delAllFile(filePath);//上传完成删除本地文件
            return sparkRepository.save(spark)!=null;
        }else{
            return false;
        }
    }

    /**
     * 保存app级配置文件，文件为shell脚本
     */
    public boolean saveAppSpark(Spark spark) throws Exception {
        if(spark!=null){
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            spark.setIp(hostInfo.getHost());
            spark.setSparkConfigPath(hostServeInfo.getHostServePath());
            spark.setRent(hostServeInfo.getRent());
                File file = newConfigFile(spark);//生成文件
                spark.setUpdateDate(new Date());
                SFTPTool sftpTool = new SFTPTool(spark.getIp(), Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
                sftpTool.upload(spark.getSparkConfigPath(), file.getPath());//发送文件给服务器*/
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String CRChangToLF="cd "+hostServeInfo.getHostServePath()+" \n sed -i 's/\\r//' "+spark.getSparkConfig();//window换行符转换为lunix
            String getAbleExcute="cd "+hostServeInfo.getHostServePath()+" \n chmod +x "+spark.getSparkConfig();//给文件添加执行权限
            client.execCmdWithBack(CRChangToLF);
            client.execCmdWithBack(getAbleExcute);
            FileDelete.delAllFile(filePath);//上传完成删除本地文件
            return sparkRepository.save(spark)!=null;
        }else{
            return false;
        }
    }

    /**
     * 配置文件生成
     */
    public File newConfigFile(Spark spark){
        String  strings=spark.getSparkConfigContent();
        String fileName=spark.getSparkConfig();
        File file= FileWrite.fileWrite(strings,filePath,fileName);
        return file;
    }


    /**
     * 备份
     */
    public boolean backSparks(Spark spark) throws Exception {
        if(spark==null)
            return false;
        spark=sparkRepository.findBySparkId(spark.getSparkId());
        //拷贝文件数据，修改文件名，调用新建保存方法上传修改文件名后的文件
        Spark spark1=newSpark(spark);
        return spark.getAppName()==null?saveSpark(spark1):saveAppSpark(spark1);//保存备份文件
    }
    public Spark newSpark(Spark spark){
        Spark spark1=new Spark();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        spark1.setSparkConfig(spark.getSparkConfig()+".bak"+sf.format(new Date()));
        spark1.setSparkConfigPath(spark.getSparkConfigPath());
        spark1.setIp(spark.getIp());
        spark1.setAppName(spark.getAppName());
        spark1.setConfigType(spark.getConfigType());
        spark1.setSparkConfigDesc(spark.getSparkConfigDesc());
        spark1.setSparkConfigContent(spark.getSparkConfigContent());
        spark1.setHostServeId(spark.getHostServeId());
        spark1.setHostId(spark.getHostId());
        return spark1;
    }

    /**
     * 重启
     */
    public boolean resetSparks(Spark spark){
        if(spark==null)
            return false;
        try {
            spark=sparkRepository.findBySparkId(spark.getSparkId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            String appName=spark.getAppName();
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String showAppId="sh "+hostServeInfo.getHostServePath()+"/base/app-get-appid.sh "+appName;//执行路径下sh文件产生一个AppID文本
            client.execCmdWithBack(showAppId);
           /* String readAppId="cat app-get-appid.result ";//读取产生AppID的文件*/
            String AppID=getDownFileContent(hostInfo,spark);
            if(!AppID.equals("")){//线程号不为空返回 杀死
                String kill="sh "+hostServeInfo.getHostServePath()+"/base/app-kill.sh "+AppID;
                client.execCmdWithBack(kill);//杀死该进程
            }
            String reset=spark.getSparkConfigPath()+"/"+spark.getSparkConfig();//重启脚本
            client.execCmdWithBack(reset);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止
     */
    public boolean stopSparks(Spark spark){
        if (spark==null)
            return false;
        try {
            spark=sparkRepository.findBySparkId(spark.getSparkId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            String appName=spark.getAppName();
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            //执行路径下sh文件产生一个AppID文本
            String showAppId="sh "+hostServeInfo.getHostServePath()+"/base/app-get-appid.sh "+appName;
            client.execCmdWithBack(showAppId);
           /* String readAppId="cat app-get-appid.result ";//读取产生AppID的文件*/
            String AppID=getDownFileContent(hostInfo,spark);
            if(AppID.equals("")){//线程号为空返回 false
                spark.setStatus(0);//查询无进程
                sparkRepository.save(spark);//设置状态未开启更新Spark
                return  true;
            }
             String kill="sh "+hostServeInfo.getHostServePath()+"/base/app-kill.sh "+AppID;
             client.execCmdWithBack(kill);//杀死该进程
             client.execCmdWithBack(showAppId);//查询AppID
             AppID=getDownFileContent(hostInfo,spark);
            if(!AppID.equals("")){
                spark.setStatus(1);//查询有进程
                sparkRepository.save(spark);//设置状态开启更新Spark
                return false;
            }else{
                spark.setStatus(0);//查询无进程
                sparkRepository.save(spark);//设置状态未开启更新Spark
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getLastAppNameString (String []appNameArry){
        String str="";
        if(appNameArry.length<2)
            return str;
        for(int i=2;i<appNameArry.length;i++){
            str+="_"+appNameArry[i];
        }
        return str;
    }

    /**
     * 更新状态
     */
    public boolean refreshSparks(){
        try {
            List<Spark> sparkList=sparkRepository.findAllByConfigTypeAndAppNameIsNotNull("spark");
            for(Spark spark :sparkList){
                HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
                HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
                String appName=spark.getAppName();
                SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
                String showAppId="sh "+hostServeInfo.getHostServePath()+"/base/app-get-appid.sh "+appName;//执行路径下sh文件产生一个AppID文本
                System.out.println(client.execCmdWithBack(showAppId));
                /*String readAppId="cat "+hostServeInfo.getHostServePath()+"/base/ app-get-appid.result ";//读取产生AppID的文件*/
                String AppID=getDownFileContent(hostInfo,spark);
                if(!AppID.equals("")){
                    spark.setStatus(1);//查询有进程
                    sparkRepository.save(spark);//设置状态开启更新Spark
                }else{
                    spark.setStatus(0);//查询无进程
                    sparkRepository.save(spark);//设置状态未开启更新Spark
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String getDownFileContent(HostInfo hostInfo,Spark spark) throws Exception {
        SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
        sftpTool.download(spark.getSparkConfigPath()+"/base","app-get-appid.result",fileDownPath);
        File file=new File(fileDownPath+File.separator+"app-get-appid.result");
        String str= FileRead.readToString(file);
        return str;
    }

    /**
     * 批量删除文件和数据
     *
     */
    //批量删除
    public boolean batchDeleteSparks(Spark[] sparks) throws Exception {
        for(Spark spark : sparks){
            //删除每个Spark主机下的配置文件
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(spark.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool=new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
            sftpTool.delete(spark.getSparkConfigPath(),spark.getSparkConfig());//删除服务器下文件
        }
        List<Long> ids = new ArrayList<>();
        Arrays.stream(sparks).forEach(spark ->
                ids.add(spark.getSparkId())
        );
        return sparkRepository.batchDeleteSparks(ids)>0;
    }

    /**
     * 批量删除数据层
     *
     */
    //批量删除
    public boolean batchDeleteSparksData(Spark[] sparks) throws Exception {
        List<Long> ids = new ArrayList<>();
        Arrays.stream(sparks).forEach(spark ->
                ids.add(spark.getSparkId())
        );
        return sparkRepository.batchDeleteSparks(ids)>0;
    }

    /**
     * 连接服务器查看配置文件
     * @return
     * @throws
     */
    public List<Spark> querySparkOnlineFile(String serveId,String isApp) throws Exception {
        List<Spark> sparkList=new ArrayList<>();
        if (serveId.equals(""))
            return sparkList;
       HostServeInfo hostServeInfo= hostServeInfoRepository.findByHostServeId(Long.parseLong(serveId));
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            Vector<ChannelSftp.LsEntry>  flieVector= sftpTool.listFiles(hostServeInfo.getHostServePath());
            for(ChannelSftp.LsEntry entry:flieVector){
                if(entry.getFilename().equals(".")|| entry.getFilename().equals("..")||entry.getFilename().split("\\.")[0].equals("")){
                    continue;
                }
                Spark spark=new Spark();
                spark.setRent(hostInfo.getUserName());
                spark.setSparkConfig(entry.getFilename());
                spark.setIp(hostInfo.getHost());
                spark.setSparkConfigPath(hostServeInfo.getHostServePath());
                spark.setConfigType("spark");
                spark.setHostServeId(hostServeInfo.getHostServeId());
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                spark.setSparkConfigDesc(sf.format(new Date())+"更新");
                spark.setHostId(hostInfo.getHostId());
                sparkList.add(spark);
            }
        return sparkList;
    }


    /**
     * 服务器文件数据批量导入
     * @param sparks
     * @return
     */
    public boolean sparkOnlineImport( Spark[] sparks,String isApp) throws Exception {
        for(Spark spark:sparks){
            HostInfo hostInfo=hostInfoRepository.findByHostId(spark.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            sftpTool.download(spark.getSparkConfigPath(),spark.getSparkConfig(),fileDownPath);
            File file=new File(fileDownPath+File.separator+spark.getSparkConfig());
            String str= FileRead.readToString(file);
            spark.setSparkConfigContent(str);
            spark.setUpdateDate(new Date());
            if(isApp.equals("app")){
                String appName=getAppName(str);
                spark.setAppName(appName);
            }
            List<Spark> sparkList=sparkRepository.findAllByIpAndConfigTypeAndSparkConfigPathAndSparkConfig
                    (spark.getIp(),spark.getConfigType(),spark.getSparkConfigPath(),spark.getSparkConfig());
            List<Long> Ids=new ArrayList<>();
            for(Spark spark1:sparkList){
                Ids.add(spark1.getSparkId());
            }
            if (Ids.size()>0){
                sparkRepository.batchDeleteSparks(Ids);
            }
            sparkRepository.save(spark);
        }
        FileDelete.delAllFile(fileDownPath);//上传完成删除本地文件
        return true;
    }

    public String getAppName(String content){
        if(content.equals(""))
            return "";
        String []contents=content.split("--");
        String name="";
        for(String str :contents){
            if(str.indexOf("name")!=-1){
                String []appNameArry=str.split("_");
                if(appNameArry.length>0){
                    name=appNameArry[appNameArry.length-1];
                    name=name.replace("name","");
                }
                String appName=getLastAppNameString(appNameArry);
                if (!appName.equals("")){
                    if(appName.indexOf("@")!=-1){
                        name=appName.split("@")[0];
                        name=name+"@";
                        break;
                    }
                }
            }
        }
        return name;
    }

}
