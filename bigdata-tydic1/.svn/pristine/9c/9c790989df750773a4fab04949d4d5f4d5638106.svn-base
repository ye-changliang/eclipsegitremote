package com.tydic.bigdata.controller.host;

import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.service.host.HostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/host-info")
public class HostInfoController {
    @Autowired
    private HostInfoService hostInfoService;
    /**
     * 跳转主机管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String hostInfo() {
        return "host/hostInfo";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hostInfoList")
    public ResponseEntity findAllHostInfoList(@PageableDefault(page = 1) Pageable pageable, HostInfo hostInfo){
        Page page= hostInfoService.findHostByName(pageable,hostInfo);
        return ResponseEntity.ok(page);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/allHostInfoList")
    @ResponseBody
    public List<HostInfo> findAll()
    {
        return hostInfoService.findAllHost();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/hostInfoSave")
    @ResponseBody
    public boolean hostInfoSave(@ModelAttribute HostInfo hostInfo){
        return hostInfoService.saveHostInfo(hostInfo);
    }

    //删除
    @RequestMapping(method = RequestMethod.POST, path = "/deleteHostInfo")
    @ResponseBody
    public String deleteHostInfo(Long hostId){
       return hostInfoService.deleteHostInfoById(hostId);
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHostInfo")
    public ResponseEntity batchDeleteHostInfo(@RequestBody HostInfo[] hostInfos){
        return ResponseEntity.ok(hostInfoService.batchDeleteHostInfo(hostInfos));
    }

}
