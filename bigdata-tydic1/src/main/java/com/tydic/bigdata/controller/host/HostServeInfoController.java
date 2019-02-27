package com.tydic.bigdata.controller.host;

import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.service.host.HostServeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/host-serve")
public class HostServeInfoController {
    @Autowired
    private HostServeInfoService hostServeInfoService;

    /**
     * 跳转主机管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String hostInfo() {
        return "host/hostServeInfo";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hostServeInfoList")
    public ResponseEntity findAllHostServeInfoList(@PageableDefault(page = 1) Pageable pageable, HostServeInfo hostServeInfo){
        Page page= hostServeInfoService.findHostByName(pageable,hostServeInfo);
        return ResponseEntity.ok(page);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/hostServeInfoSave")
    @ResponseBody
    public boolean hostInfoSave(@ModelAttribute HostServeInfo hostServeInfo){
        return hostServeInfoService.saveHostServeInfo(hostServeInfo);
    }

    /**
     * 批量删除
     */
    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHostServeInfo")
    public ResponseEntity batchDeleteHostServeInfo(@RequestBody HostServeInfo[] hostServeInfos){
        return ResponseEntity.ok(hostServeInfoService.batchDeleteHostServeInfo(hostServeInfos));
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findAllHostServeListByServeName")
    @ResponseBody
    public List<HostServeInfo> findAllHostServeListByServeName(HostServeInfo hostServeInfo)
    {
        List<HostServeInfo> hostServeInfos= hostServeInfoService.findAllHostServeListByServeName(hostServeInfo);
        return hostServeInfos;
    }

    /**
     * 测试链接
     */
    @RequestMapping(method = RequestMethod.POST, path = "/testHostConnet")
    @ResponseBody
    public boolean testHostConnet(HostServeInfo hostServeInfo){
        return hostServeInfoService.testHostConnet(hostServeInfo);
    }

}
