package com.tydic.bigdata.service.redis;

import com.jcraft.jsch.ChannelSftp;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.redis.Redis;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
import com.tydic.bigdata.repository.redis.RedisRepository;
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
public class RedisService {
    @Value(value = "${filePath}")
    private String filePath;

    @Value(value = "${fileDownPath}")
    private String fileDownPath;
    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private HostInfoRepository hostInfoRepository;

    /**
     * 查询redis配置文件列表
     * @param pageable
     * @param redis
     * @return
     */
    public Page<Redis> findRedisByIp(Pageable pageable, Redis redis){
        return  redisRepository.findAllByConfigTypeAndIpContainingOrderByUpdateDateDesc(redis.getConfigType(),redis.getIp(),pageable);
    }
    /**
     * 查询redis监控列表
     */
    public List<Redis> findRedisAndGroupByIp(){
        return redisRepository.findAllByConfigTypeAndIp();
    }



    /**
     * 保存
     */
    public boolean saveRedis(Redis redis) throws Exception {
        if(redis!=null){
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(redis.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            redis.setIp(hostInfo.getHost());
            redis.setRedisConfigPath(hostServeInfo.getHostServePath());
                redis.setUpdateDate(new Date());
                File file = newConfigFile(redis, hostServeInfo);//生成文件
                SFTPTool sftpTool = new SFTPTool(redis.getIp(), Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
                sftpTool.upload(redis.getRedisConfigPath(), file.getPath());//发送文件给服务器*/
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String CRChangToLF="cd "+hostServeInfo.getHostServePath()+" \n sed -i 's/\\r//' "+redis.getRedisConfig();//window换行符转换为lunix
            client.execCmdWithBack(CRChangToLF);
            FileDelete.delAllFile(filePath);//上传完成删除本地文件
            return redisRepository.save(redis)!=null;
        }else{
            return false;
        }
    }

    /**
     * 配置文件生成
     */
    public File newConfigFile(Redis redis,HostServeInfo hostServeInfo){
        String  strings=redis.getRedisConfigContent();
        String fileName=redis.getRedisConfig();
        /*String filePath="E:configFile";*//*redis.getIp()+redis.getRedisConfigPath()*//*;*/
        File file= FileWrite.fileWrite(strings,filePath,fileName);
        return file;
    }


    /**
     * 备份
     */
    public boolean backRediss(Redis redis) throws Exception {
        if(redis==null)
            return false;
        redis=redisRepository.findByRedisId(redis.getRedisId());
        //拷贝文件数据，修改文件名，调用新建保存方法上传修改文件名后的文件
        Redis redis1=newRedis(redis);
        return saveRedis(redis1);//保存备份文件
    }
    public Redis newRedis(Redis redis){
        Redis redis1=new Redis();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        redis1.setRedisConfig(redis.getRedisConfig()+".bak"+sf.format(new Date()));
        redis1.setRedisConfigPath(redis.getRedisConfigPath());
        redis1.setIp(redis.getIp());
        redis1.setConfigType(redis.getConfigType());
        redis1.setRedisConfigDesc(redis.getRedisConfigDesc());
        redis1.setRedisConfigContent(redis.getRedisConfigContent());
        redis1.setHostServeId(redis.getHostServeId());
        redis1.setHostId(redis.getHostId());
        return redis1;
    }

    /**
     * 重启
     */
    public boolean resetRediss(Redis redis){
        if (redis==null)
            return false;
        try {
            redis=redisRepository.findByRedisId(redis.getRedisId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(redis.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String show="ps -ef | grep redis | grep -v grep | awk -F ' ' '{print $2}'";
            String kill="ps -ef | grep redis |grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String srcPath=redis.getRedisConfigPath()+"/src";
            String reset="cd "+srcPath+" \n nohup ./redis-server >/dev/null 2>&1 &";
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
    public boolean stopRediss(Redis redis){
        if (redis==null)
            return false;
        try {
            redis=redisRepository.findByRedisId(redis.getRedisId());
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(redis.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
            String show="ps -ef | grep redis | grep -v grep | awk -F ' ' '{print $2}'";
            String kill="ps -ef | grep redis |grep -v grep|awk '{print \"kill -9 \"$2}'|sh";
            String execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                System.out.println("杀死进程");
                System.out.println("kill:"+kill);
                client.execCmdWithBack(kill);
            }
            execShow=client.execCmdWithBack(show);
            if(!execShow.equals("")){
                redis.setStatus(1);//查询有进程
                redisRepository.save(redis);//设置状态开启更新flume
            }else{
                redis.setStatus(0);//查询无进程
                redisRepository.save(redis);//设置状态未开启更新flume
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
    public boolean refreshRediss(){
        try {
            List<Redis> redisList=redisRepository.findAllByConfigType("redis");
            for(Redis redis :redisList){
                HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(redis.getHostServeId());
                HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
                String show="ps -ef | grep redis | grep -v grep | awk -F ' ' '{print $2}'";
                SSHClient client = SSHClient.getInstance(hostInfo.getHost(),hostInfo.getUserName(),hostInfo.getPassWord());
                String execShow=client.execCmdWithBack(show);
                if(!execShow.equals("")){
                    redis.setStatus(1);//查询有进程
                    redisRepository.save(redis);//设置状态开启更新flume
                }else{
                    redis.setStatus(0);//查询无进程
                    redisRepository.save(redis);//设置状态未开启更新flume
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 连接服务器查看配置文件
     * @return
     * @throws Exception
     */
    public List<Redis> queryRedisOnlineFile(String serveId) throws Exception {
        List<Redis> redisList=new ArrayList<>();
        if (serveId.equals("")) {
            return redisList;
        }
        HostServeInfo hostServeInfo= hostServeInfoRepository.findByHostServeId(Long.parseLong(serveId));
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            Vector<ChannelSftp.LsEntry>  flieVector= sftpTool.listFiles(hostServeInfo.getHostServePath());
            for(ChannelSftp.LsEntry entry:flieVector){
                if(entry.getFilename().equals(".")|| entry.getFilename().equals("..")|| entry.getFilename().indexOf(".")==-1 ||entry.getFilename().split("\\.")[0].equals("")){
                    continue;
                }
                Redis kafKa=new Redis();
                kafKa.setRedisConfig(entry.getFilename());
                kafKa.setIp(hostInfo.getHost());
                kafKa.setRedisConfigPath(hostServeInfo.getHostServePath());
                kafKa.setConfigType("redis");
                kafKa.setHostServeId(hostServeInfo.getHostServeId());
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                kafKa.setRedisConfigDesc(sf.format(new Date())+"更新");
                kafKa.setHostId(hostInfo.getHostId());
                redisList.add(kafKa);
        }
        return redisList;
    }

    /**
     * 服务器文件数据批量导入
     * @param redis
     * @return
     */
    public boolean redisOnlineImport( Redis[] redis) throws Exception {
        for(Redis redis1:redis){
            HostInfo hostInfo=hostInfoRepository.findByHostId(redis1.getHostId());
            SFTPTool sftpTool = new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()), hostInfo.getUserName(), hostInfo.getPassWord());
            sftpTool.download(redis1.getRedisConfigPath(),redis1.getRedisConfig(),fileDownPath);
            File file=new File(fileDownPath+File.separator+redis1.getRedisConfig());
            String str= FileRead.readToString(file);
            redis1.setRedisConfigContent(str);
            redis1.setUpdateDate(new Date());
            List<Redis> redisList=redisRepository.findAllByIpAndConfigTypeAndRedisConfigPathAndRedisConfig
                    (redis1.getIp(),redis1.getConfigType(),redis1.getRedisConfigPath(),redis1.getRedisConfig());
            List<Long> Ids=new ArrayList<>();
            for(Redis redis2:redisList){
                Ids.add(redis2.getRedisId());
            }
            if (Ids.size()>0){
                redisRepository.batchDeleteRediss(Ids);
            }
            redisRepository.save(redis1);
        }
        FileDelete.delAllFile(fileDownPath);//上传完成删除本地文件
        return true;
    }

    public List<Redis> findAllRedisMonitorGroupBy(){
        List _list=redisRepository.findAllRedisMonitorGroupBy();
        List<Redis> redisList = new ArrayList<>();
        for(Object row:_list){
            Object[] cells = (Object[]) row;
            Redis redis1 = new Redis();
            redis1.setRedisId((Long) cells[0]);
            redis1.setIp((String) cells[1]);
            redis1.setStatus((int) cells[2]);
            redisList.add(redis1);
        }
        return redisList;
    }

    /**
     * 批量删除
     *
     */
    public boolean batchDeleteRedis(Redis[] redis) throws Exception {
        for(Redis redis1 : redis){
            //删除每个flume主机下的配置文件
            HostServeInfo hostServeInfo=hostServeInfoRepository.findByHostServeId(redis1.getHostServeId());
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            SFTPTool sftpTool=new SFTPTool(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
            sftpTool.delete(redis1.getRedisConfigPath(),redis1.getRedisConfig());//删除服务器下文件
        }
        List<Long> ids = new ArrayList<>();
        Arrays.stream(redis).forEach(redis1 ->
                ids.add(redis1.getRedisId())
        );
        return redisRepository.batchDeleteRediss(ids)>0;
    }

    /**
     * 批量删除数据库信息
     *
     */
    public boolean batchDeleteRedisData(Redis[] redis) throws Exception {
        List<Long> ids = new ArrayList<>();
        Arrays.stream(redis).forEach(redis1 ->
                ids.add(redis1.getRedisId())
        );
        return redisRepository.batchDeleteRediss(ids)>0;
    }

}
