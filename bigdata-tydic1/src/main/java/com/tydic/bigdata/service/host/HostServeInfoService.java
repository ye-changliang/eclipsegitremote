package com.tydic.bigdata.service.host;

import com.jcraft.jsch.ChannelSftp;
import com.tydic.bigdata.domain.flume.Flume;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
import com.tydic.bigdata.utils.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HostServeInfoService {
    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private HostInfoRepository hostInfoRepository;
    /**
     * 查找所有服务
     */
    public Page<HostServeInfo> findHostByName(Pageable pageable, HostServeInfo hostServeInfo){
        return  hostServeInfoRepository.findAllByHostServeNameContaining(hostServeInfo.getHostServeName(),pageable);
    }

    /**
     * 保存
     */
    public boolean saveHostServeInfo(HostServeInfo hostServeInfo){
        if(hostServeInfo!=null){
            HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
            hostServeInfo.setHost(hostInfo.getHost());
            hostServeInfo.setRent(hostInfo.getUserName());
            return hostServeInfoRepository.save(hostServeInfo)!=null;
        }else{
            return false;
        }
    }

    /**
     * 查询所有
     */
    public List<HostServeInfo> findAllHostServeListByServeName(HostServeInfo hostServeInfo){
      return   hostServeInfoRepository.findAllByHostServeNameContaining(hostServeInfo.getHostServeName());
    }

    /**
     * 测试主机连接
     */
    public boolean testHostConnet(HostServeInfo hostServeInfo){
        hostServeInfo=hostServeInfoRepository.findByHostServeId(hostServeInfo.getHostServeId());
        HostInfo hostInfo=hostInfoRepository.findByHostId(hostServeInfo.getHostId());
        ChannelSftp sftp=FileTool.connect(hostInfo.getHost(),Integer.parseInt(hostInfo.getPost()),hostInfo.getUserName(),hostInfo.getPassWord());
        if(sftp!=null){
            sftp.exit();
            return true;
        }else{
          return false;
        }
    }

    //批量删除
    public boolean batchDeleteHostServeInfo(HostServeInfo[] hostServeInfos){
        List<Long> ids = new ArrayList<>();
        Arrays.stream(hostServeInfos).forEach(hostServeInfo ->
                ids.add(hostServeInfo.getHostServeId())
        );
        return hostServeInfoRepository.batchDeleteHostServeInfo(ids)>0;
    }
}
