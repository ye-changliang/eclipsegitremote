package com.tydic.bigdata.service.host;

import com.tydic.bigdata.domain.flume.Flume;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.repository.flume.FlumeRepository;
import com.tydic.bigdata.repository.hostInfo.HostInfoRepository;
import com.tydic.bigdata.repository.hostServeInfo.HostServeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class HostInfoService {
    @Autowired
    private HostInfoRepository hostInfoRepository;

    @Autowired
    private HostServeInfoRepository hostServeInfoRepository;

    @Autowired
    private FlumeRepository flumeRepository;

    /**
     * 查找所有主机
     */
    public Page<HostInfo> findHostByName(Pageable pageable, HostInfo hostInfo){
        return  hostInfoRepository.findAllByHostContaining(hostInfo.getHost(),pageable);
    }

    /**
     * 查询所有
     */
    public List<HostInfo> findAllHost(){
        return hostInfoRepository.findAll();
    }

    /**
     * 保存
     */
    public boolean saveHostInfo(HostInfo hostInfo){
       /* Optional<HostInfo> hostInfoOptionalOptional = hostInfoRepository.findById(hostInfo.getHostId());*/
        if(hostInfo!=null){
          return hostInfoRepository.save(hostInfo)!=null;
        }else{
            return false;
        }
    }

    /**
     * 删除
     */
    public String deleteHostInfoById(Long hostId) {
        String flag="";
       if (hostId!=null){
          HostInfo hostInfo= hostInfoRepository.findByHostId(hostId);
          List<HostServeInfo> hostServeInfos =hostServeInfoRepository.findAllByHostId(hostId);
          flag=hostServeInfos.size()==0?"":"该主机信息有"+hostServeInfos.size()+"个服务已配置";
          if(flag.equals("")){
              return hostInfoRepository.deleteByHostId(hostId)>0?"删除成功":"删除失败";
          }else{
              return flag;
          }
       }
       return "删除失败";
    }

    //批量删除
    public boolean batchDeleteHostInfo(HostInfo[] hostInfos){
        String flag="";
        List<Long> ids = new ArrayList<>();
        for(HostInfo hostInfo :hostInfos){
            List<HostServeInfo> hostServeInfos =hostServeInfoRepository.findAllByHostId(hostInfo.getHostId());
            flag=hostServeInfos.size()==0?"":"主机"+hostInfo.getHost()+"有"+hostServeInfos.size()+"个服务已配置";
        }
        if(!flag.equals("")){
            return false;
        }
        Arrays.stream(hostInfos).forEach(hostInfo ->
                ids.add(hostInfo.getHostId())
        );
        return hostInfoRepository.batchDeleteHostInfo(ids)>0;
    }
}
