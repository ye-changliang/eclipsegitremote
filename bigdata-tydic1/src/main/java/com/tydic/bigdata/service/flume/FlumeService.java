package com.tydic.bigdata.service.flume;

import com.jcraft.jsch.ChannelSftp;
import com.tydic.bigdata.domain.flume.Flume;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.repository.flume.FlumeRepository;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
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
public class FlumeService {
    @Value(value = "${filePath}")
    private String filePath;

    @Value(value = "${fileDownPath}")
    private String fileDownPath;

    @Autowired
    private FlumeRepository flumeRepository;

    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private HostInfoRepository hostInfoRepository;

    public Page<Flume> findFlumeByIp(Pageable pageable, Flume flume){
        return  flumeRepository.findAllByConfigTypeAndIpContainingAndFlumeConfigContainingOrderByUpdateDateDesc(flume.getConfigType(),flume.getIp(),flume.getFlumeConfig(),pageable);
    }

    /**
     * 保存
     */
    public boolean saveFlume(Flume flume) throws Exception {
        if(flume!=null){
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(flume.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            flume.setIp(hostInfo.getHost());
            flume.setFlumeConfigPath(hostServeInfo.getHostServePath());
            String org=getOrgStr(flume.getFlumeConfigContent());
            flume.setOrg(org);
                flume.setUpdateDate(new Date());
            File file=newConfigFile(flume,hostServeInfo);//生成文件
            if(file==null)
                return false;
            SFTPTool sftpTool=new SFTPTool(flume.getIp(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
            sftpTool.upload(flume.getFlumeConfigPath(),file.getPath());//发送文件给服务器*/
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String CRChangToLF="cd "+hostServeInfo.getHostServePath()+" \n sed -i 's/\\r//' "+flume.getFlumeConfig();//window换行符转换为lunix
            client.execCmdWithBack(CRChangToLF);
            FileDelete.delAllFile(filePath);//上传完成删除本地文件
            return flumeRepository.save(flume)!=null;
        }else{
            return false;
        }
    }

    public String getOrgStr(String org){
        String orgName="";
        boolean f=false;
        if(!org.equals("")){
            String []orgs=org.split("\n");
            for(String str:orgs){
                if(str.indexOf("#")!=-1)
                    continue;
                String []strArray=str.split("\\.");
                for(String string:strArray){
                    if (string.equals(""))
                        continue;
                    orgName=string;
                    f=true;
                    break;
                }
                if (f){
                    break;
                }
            }
        }
        return orgName;
    }

    /**
     * 配置文件生成
     */
    public File newConfigFile(Flume flume,HostServeInfo hostServeInfo){
        String  strings=flume.getFlumeConfigContent();
        String fileName=flume.getFlumeConfig();
        File file=FileWrite.fileWrite(strings,filePath,fileName);
        return file;
    }



    /**
     * 批量删除数据和文件
     *
     */
    public boolean batchDeleteFlumes(Flume[] flumes) throws Exception {
        for(Flume flume : flumes){
            //删除每个flume主机下的配置文件
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(flume.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool=new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
            sftpTool.delete(flume.getFlumeConfigPath(),flume.getFlumeConfig());//删除服务器下文件
        }
        List<Long> ids = new ArrayList<>();
        Arrays.stream(flumes).forEach(flume ->
                ids.add(flume.getFlumeId())
        );
        return flumeRepository.batchDeleteFlumes(ids)>0;
    }

    /**
     * 批量删除数据库层
     *
     */
    public boolean batchDeleteFlumesData(Flume[] flumes) throws Exception {
        List<Long> ids = new ArrayList<>();
        Arrays.stream(flumes).forEach(flume ->
                ids.add(flume.getFlumeId())
        );
        return flumeRepository.batchDeleteFlumes(ids)>0;
    }

    /**
     * 备份
     */
    public boolean backFlumes(Flume flume) throws Exception {
        if(flume==null)
            return false;
        flume=flumeRepository.findByFlumeId(flume.getFlumeId());
        //拷贝文件数据，修改文件名，调用新建保存方法上传修改文件名后的文件
        Flume flume1=newFlume(flume);
        return saveFlume(flume1);//保存备份文件
    }

    public Flume newFlume(Flume flume){
        Flume flume1=new Flume();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        flume1.setFlumeConfig(flume.getFlumeConfig()+".bak"+sf.format(new Date()));
        flume1.setOrg(flume.getOrg());
        flume1.setFlumeConfigPath(flume.getFlumeConfigPath());
        flume1.setIp(flume.getIp());
        flume1.setAppName(flume.getAppName()+"备份");
        flume1.setConfigType(flume.getConfigType());
        flume1.setFlumeConfigDesc(flume.getFlumeConfigDesc());
        flume1.setFlumeConfigContent(flume.getFlumeConfigContent());
        flume1.setHostServeId(flume.getHostServeId());
        flume1.setHostId(flume.getHostId());
        return flume1;
    }

    /**
     * 重启
     */
    public boolean resetFlumes(Flume flume){
        if (flume==null)
            return false;
        try {
            flume=flumeRepository.findByFlumeId(flume.getFlumeId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(flume.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            //重启测试
            String show="ps -ef | grep "+flume.getFlumeConfig()+" | grep -v grep | awk -F ' ' '{print $2}'";
            String kill="ps -ef | grep "+flume.getFlumeConfig()+"|grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String reset="cd /hadoop/cloud_platform/cloud_service/flume/current \n" +
                    " nohup bin/flume-ng agent --conf conf  --conf-file conf/"+flume.getFlumeConfig()+" --name "+flume.getOrg()+" -Dflume.root.logger=ERROR,console -Dflume.monitoring.type=http -Dflume.monitoring.port="+(int) ((Math.random()*9+1)*10000)+" > logs/flume-monitor-"+flume.getFlumeConfig()+".log 2>&1 &";
            String execShow=client.execCmdWithBack(show);
            String execKill=client.execCmdWithBack(kill);
            String execReset=client.execCmdWithBack(reset);
            System.out.println(execShow);
            System.out.println(execKill);//杀死
            System.out.println(execReset);//重启
             show="ps -ef | grep "+flume.getFlumeConfig()+" | grep -v grep | awk -F ' ' '{print $2}'";
             execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                flume.setStatus(1);//查询有进程
                flumeRepository.save(flume);//设置状态开启更新flume
            }else{
                flume.setStatus(0);//查询无进程
                flumeRepository.save(flume);//设置状态未开启更新flume
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    /**
     * 停止
     */
    public boolean stopFlumes(Flume flume){
        if (flume==null)
            return false;
        try {
            flume=flumeRepository.findByFlumeId(flume.getFlumeId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(flume.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String kill="ps -ef | grep "+flume.getFlumeConfig()+"|grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String execKill=client.execCmdWithBack(kill);
            System.out.println(execKill);
            String show="ps -ef | grep "+flume.getFlumeConfig()+" | grep -v grep | awk -F ' ' '{print $2}'";
            String execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                flume.setStatus(1);//查询有进程
                flumeRepository.save(flume);//设置状态开启更新flume
            }else{
                flume.setStatus(0);//查询无进程
                flumeRepository.save(flume);//设置状态未开启更新flume
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新状态
     */
    public boolean refreshFlumes(){
        try {
            List<Flume> flumeList=flumeRepository.findAllByConfigType("flume");
            for(Flume flume :flumeList){
                HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(flume.getHostServeId());
                HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
                SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
                String show="ps -ef | grep "+flume.getFlumeConfig()+" | grep -v grep | awk -F ' ' '{print $2}'";
                String execShow=client.execCmdWithBack(show);
                if(!execShow.equals("")){
                    flume.setStatus(1);//查询有进程
                    flumeRepository.save(flume);//设置状态开启更新flume
                }else{
                    flume.setStatus(0);//查询无进程
                    flumeRepository.save(flume);//设置状态未开启更新flume
                }
                System.out.println(execShow);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkFlumesByConfigName(String configName){
        if (configName.equals(""))
            return true;
        List flumeList=flumeRepository.findAllByFlumeConfigAndConfigType(configName,"flume");
        return flumeList.size()>0?true:false;
    }

    /**
     * 连接服务器查看配置文件
     * @return
     * @throws Exception
     */
    public List<Flume> queryFlumeOnlineFile(String serveId) throws Exception {
        List<Flume> flumeList=new ArrayList<>();
        if (serveId.equals(""))
            return flumeList;
        HostServeInfo hostServeInfo= hostServeInfoRepository.findByHostServeId(Long.parseLong(serveId));
        HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
        SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
        Vector<ChannelSftp.LsEntry> flieVector= sftpTool.listFiles(hostServeInfo.getHostServePath());
        for(ChannelSftp.LsEntry entry:flieVector){
            if(entry.getFilename().equals(".")|| entry.getFilename().equals("..")||entry.getFilename().split("\\.")[0].equals("")){
                continue;
            }
            Flume flume=new Flume();
            flume.setFlumeConfig(entry.getFilename());
            flume.setIp(hostInfo.getHost());
            flume.setFlumeConfigPath(hostServeInfo.getHostServePath());
            flume.setConfigType("flume");
            flume.setHostServeId(hostServeInfo.getHostServeId());
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            flume.setFlumeConfigDesc(sf.format(new Date())+"更新");
            flume.setHostId(hostInfo.getHostId());
            flumeList.add(flume);
        }
        return flumeList;
    }

    /**
     * 服务器文件数据批量导入
     * @param flumes
     * @return
     */
    public boolean flumeOnlineImport( Flume[] flumes) throws Exception {
        for(Flume flume:flumes){
            HostInfo hostInfo=hostInfoRepository.findByHostId(flume.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            sftpTool.download(flume.getFlumeConfigPath(),flume.getFlumeConfig(),fileDownPath);
            File file=new File(fileDownPath+File.separator+flume.getFlumeConfig());
            String str= FileRead.readToString(file);
            flume.setFlumeConfigContent(str);
            String org=getOrgStr(flume.getFlumeConfigContent());
            flume.setOrg(org);
            flume.setUpdateDate(new Date());
            List<Flume> flumeList=flumeRepository.findAllByIpAndConfigTypeAndFlumeConfigPathAndFlumeConfig
                    (flume.getIp(),flume.getConfigType(),flume.getFlumeConfigPath(),flume.getFlumeConfig());
            List<Long> Ids=new ArrayList<>();
            for(Flume flume1:flumeList){
                Ids.add(flume1.getFlumeId());
            }
            if (Ids.size()>0){
                flumeRepository.batchDeleteFlumes(Ids);
            }
            flumeRepository.save(flume);
        }
        FileDelete.delAllFile(fileDownPath);//上传完成删除本地文件
        return true;
    }


}
