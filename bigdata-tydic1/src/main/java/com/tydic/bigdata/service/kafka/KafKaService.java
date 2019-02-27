package com.tydic.bigdata.service.kafka;

import com.jcraft.jsch.ChannelSftp;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.kafka.KafKa;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
import com.tydic.bigdata.repository.kafka.KafKaRepository;
import com.tydic.bigdata.ssh.SSHClient;
import com.tydic.bigdata.utils.FileDelete;
import com.tydic.bigdata.utils.FileRead;
import com.tydic.bigdata.utils.FileWrite;
import com.tydic.bigdata.utils.SFTPTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class KafKaService {
    @Value(value = "${filePath}")
    private String filePath;

    @Value(value = "${fileDownPath}")
    private String fileDownPath;
    @Autowired
    private KafKaRepository kafKaRepository;

    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private HostInfoRepository hostInfoRepository;

    public Page<KafKa> findKafKaByIp(Pageable pageable, KafKa kafKa){
        return  kafKaRepository.findAllByConfigTypeAndIpContainingOrderByUpdateDateDesc(kafKa.getConfigType(),kafKa.getIp(),pageable);
    }

    public List<KafKa> findAllKafKaMonitorGroupBy(){
        List _list=kafKaRepository.findAllKafKaMonitorGroupBy();
        List<KafKa> kafKaList = new ArrayList<>();
        for(Object row:_list){
            Object[] cells = (Object[]) row;
            KafKa kafKa1 = new KafKa();
            kafKa1.setKafkaId((Long) cells[0]);
            kafKa1.setIp((String) cells[1]);
            kafKa1.setStatus((int) cells[2]);
            kafKaList.add(kafKa1);
        }
        return kafKaList;
    }

    /**
     * 保存
     */
    public boolean saveKafKa(KafKa kafKa) throws Exception {
        if(kafKa!=null){
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(kafKa.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            kafKa.setIp(hostInfo.getHost());
            kafKa.setKafkaConfigPath(hostServeInfo.getHostServePath());
                kafKa.setUpdateDate(new Date());
                File file = newConfigFile(kafKa, hostServeInfo);//生成文件
                if (file == null)
                    return false;
                SFTPTool sftpTool = new SFTPTool(kafKa.getIp(), Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
                sftpTool.upload(kafKa.getKafkaConfigPath(), file.getPath());//发送文件给服务器*/
                SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
                String CRChangToLF="cd "+hostServeInfo.getHostServePath()+" \n sed -i 's/\\r//' "+kafKa.getKafkaConfig();//window换行符转换为lunix
            client.execCmdWithBack(CRChangToLF);
            FileDelete.delAllFile(filePath);//上传完成删除本地文件
            return kafKaRepository.save(kafKa)!=null;
        }else{
            return false;
        }
    }

    /**
     * 连接服务器查看配置文件
     * @return
     * @throws Exception
     */
    public List<KafKa> queryKafKaOnlineFile(String serveId) throws Exception {
        List<KafKa> kafKaList=new ArrayList<>();
        if (serveId.equals("")){
            return kafKaList;
        }
        HostServeInfo hostServeInfo= hostServeInfoRepository.findByHostServeId(Long.parseLong(serveId));
           HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
           SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
           Vector<ChannelSftp.LsEntry>  flieVector= sftpTool.listFiles(hostServeInfo.getHostServePath());
           for(ChannelSftp.LsEntry entry:flieVector){
               if(entry.getFilename().equals(".")|| entry.getFilename().equals("..")||entry.getFilename().split("\\.")[0].equals("")){
                   continue;
               }
               KafKa kafKa=new KafKa();
               kafKa.setKafkaConfig(entry.getFilename());
               kafKa.setIp(hostInfo.getHost());
               kafKa.setKafkaConfigPath(hostServeInfo.getHostServePath());
               kafKa.setConfigType("kafka");
               kafKa.setHostServeId(hostServeInfo.getHostServeId());
               SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               kafKa.setKafkaConfigDesc(sf.format(new Date())+"更新");
               kafKa.setHostId(hostInfo.getHostId());
               kafKaList.add(kafKa);
           }
       return kafKaList;
    }

    /**
     * 服务器文件数据批量导入
     * @param kafKas
     * @return
     */
    public boolean kafKaOnlineImport( KafKa[] kafKas) throws Exception {
        for(KafKa kafKa:kafKas){
            HostInfo hostInfo=hostInfoRepository.findByHostId(kafKa.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            sftpTool.download(kafKa.getKafkaConfigPath(),kafKa.getKafkaConfig(),fileDownPath);
            File file=new File(fileDownPath+File.separator+kafKa.getKafkaConfig());
            String str=FileRead.readToString(file);
            kafKa.setKafkaConfigContent(str);
            kafKa.setUpdateDate(new Date());
            List<KafKa> kafKaList=kafKaRepository.findAllByIpAndConfigTypeAndKafkaConfigPathAndKafkaConfig
                    (kafKa.getIp(),kafKa.getConfigType(),kafKa.getKafkaConfigPath(),kafKa.getKafkaConfig());
            List<Long> Ids=new ArrayList<>();
            for(KafKa kafKa1:kafKaList){
                Ids.add(kafKa1.getKafkaId());
            }
            if (Ids.size()>0){
                kafKaRepository.batchDeleteKafKas(Ids);
            }
            kafKaRepository.save(kafKa);
        }
        FileDelete.delAllFile(fileDownPath);//上传完成删除本地文件
        return true;
    }

    /**
     * 配置文件生成
     */
    public File newConfigFile(KafKa kafKa,HostServeInfo hostServeInfo){
        String  strings=kafKa.getKafkaConfigContent();
        String fileName=kafKa.getKafkaConfig();
        File file= FileWrite.fileWrite(strings,filePath,fileName);
        return file;
    }

    /**
     * ftp發送文件
     *//*
    public boolean sendConfigFile(KafKa kafKa,HostInfo hostInfo,File file) throws FileNotFoundException {
        FileInputStream in=new FileInputStream(file);
        return FileTool.uploadFile(kafKa.getIp(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord(),kafKa.getKafkaConfigPath(),kafKa.getKafkaConfig(),in);
    }
*/


    /**
     * 备份//
     */
    public boolean backKafKas(KafKa kafKa) throws Exception {
        if(kafKa==null)
            return false;
        kafKa=kafKaRepository.findByKafkaId(kafKa.getKafkaId());
        //拷贝文件数据，修改文件名，调用新建保存方法上传修改文件名后的文件
        KafKa kafKa1=newKafKa(kafKa);
        return saveKafKa(kafKa1);//保存备份文件
    }

    public KafKa newKafKa(KafKa kafKa){
        KafKa kafKa1=new KafKa();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        kafKa1.setKafkaConfig(kafKa.getKafkaConfig()+".bak"+sf.format(new Date()));
        kafKa1.setKafkaConfigPath(kafKa.getKafkaConfigPath());
        kafKa1.setIp(kafKa.getIp());
        kafKa1.setConfigType(kafKa.getConfigType());
        kafKa1.setKafkaConfigDesc(kafKa.getKafkaConfigDesc());
        kafKa1.setKafkaConfigContent(kafKa.getKafkaConfigContent());
        kafKa1.setHostServeId(kafKa.getHostServeId());
        kafKa1.setHostId(kafKa.getHostId());
        return kafKa1;
    }

    /**
     * 重启
     */
    public boolean resetKafKas(KafKa kafKa){
        if (kafKa==null)
            return false;
        try {
            kafKa=kafKaRepository.findByKafkaId(kafKa.getKafkaId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(kafKa.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String show="ps -ef | grep kafka | grep -v grep | awk -F ' ' '{print $2}'";
            String kill="ps -ef | grep kafka |grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String binPath=kafKa.getKafkaConfigPath().replace("/config","/bin");
            String reset="cd "+binPath+" \n ./kafka-server-start.sh -daemon ../config/server.properties";
            String execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                System.out.println("重启前杀死进程");
                System.out.println("kill:"+kill);
                client.execCmdWithBack(kill);
            }
            System.out.println("---------重启------");
            System.out.println(reset);
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
    public boolean stopKafKas(KafKa kafKa){
        if (kafKa==null)
            return false;
        try {
            kafKa=kafKaRepository.findByKafkaId(kafKa.getKafkaId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(kafKa.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String kill="ps -ef | grep kafka |grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String show="ps -ef | grep kafka | grep -v grep | awk -F ' ' '{print $2}'";
            String execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                System.out.println("kill kafka"+kill);
                System.out.println(kill);
                client.execCmdWithBack(kill);
            }
            execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                //查询有进程
                kafKa.setStatus(1);
                //设置状态开启更新flume
                kafKaRepository.save(kafKa);
            }else{
                //查询无进程
                kafKa.setStatus(0);
                //设置状态未开启更新flume
                kafKaRepository.save(kafKa);
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
    public boolean refreshKafKas(){
        try {
            List<KafKa> kafKaList=kafKaRepository.findAllByConfigType("kafka");
            for(KafKa kafKa :kafKaList){
                HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(kafKa.getHostServeId());
                HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
                SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
                String show="ps -ef | grep kafka | grep -v grep | awk -F ' ' '{print $2}'";
                String execShow=client.execCmdWithBack(show);
                if(!execShow.equals("")){
                    kafKa.setStatus(1);//查询有进程
                    kafKaRepository.save(kafKa);//设置状态开启更新
                }else{
                    kafKa.setStatus(0);//查询无进程
                    kafKaRepository.save(kafKa);//设置状态未开启更新
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 批量删除
     *
     */
    public boolean batchDeleteKafKas(KafKa[] kafKas) throws Exception {
        for(KafKa kafKa : kafKas){
            //删除每个flume主机下的配置文件
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(kafKa.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool=new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
            sftpTool.delete(kafKa.getKafkaConfigPath(),kafKa.getKafkaConfig());//删除服务器下文件
        }
        List<Long> ids = new ArrayList<>();
        Arrays.stream(kafKas).forEach(kafKa ->
                ids.add(kafKa.getKafkaId())
        );
        return kafKaRepository.batchDeleteKafKas(ids)>0;
    }

    /**
     * 批量删除数据库信息
     *
     */
    public boolean batchDeleteKafKasData(KafKa[] kafKas) throws Exception {
        List<Long> ids = new ArrayList<>();
        Arrays.stream(kafKas).forEach(kafKa ->
                ids.add(kafKa.getKafkaId())
        );
        return kafKaRepository.batchDeleteKafKas(ids)>0;
    }

}
