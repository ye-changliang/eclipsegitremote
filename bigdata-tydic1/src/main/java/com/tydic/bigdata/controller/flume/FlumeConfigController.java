package com.tydic.bigdata.controller.flume;

import com.tydic.bigdata.domain.flume.Flume;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.service.flume.FlumeService;
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
@RequestMapping("/flume")
public class FlumeConfigController {
    @Autowired
    private FlumeService flumeService;

    @Autowired
    private HostServeInfoService hostServeInfoService;
    /**
     * 跳转flume管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String flume() {
        return "flume/flume";
    }

    /**
     * 跳转flume监控管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/monitor")
    public String flumeMonitor() {
        return "flume/flume-monitor";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findFlumeByIp")
    public ResponseEntity findFlumeByIp(@PageableDefault(page = 1) Pageable pageable, Flume flume){
        Page page= flumeService.findFlumeByIp(pageable,flume);
        return ResponseEntity.ok(page);
    }

    //保存
    @RequestMapping(method = RequestMethod.POST, path = "/flumeSave")
    @ResponseBody
    public boolean flumeSave(@ModelAttribute Flume flume) throws Exception {
        return flumeService.saveFlume(flume);
    }
    
    /**
     * 测试链接
     */
    @RequestMapping(method = RequestMethod.POST, path = "/testHostConnet")
    @ResponseBody
    public boolean testHostConnet(HostServeInfo hostServeInfo){
       return hostServeInfoService.testHostConnet(hostServeInfo);
    }


    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteFlumes")
    public ResponseEntity batchDeleteFlumes(@RequestBody Flume[] flumes) throws Exception {
        return ResponseEntity.ok(flumeService.batchDeleteFlumes(flumes));
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteFlumesData")
    public ResponseEntity batchDeleteFlumesData(@RequestBody Flume[] flumes) throws Exception {
        return ResponseEntity.ok(flumeService.batchDeleteFlumesData(flumes));
    }

    //flume备份
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/backFlumes")
    public boolean backFlumes(@ModelAttribute Flume flume) throws Exception {
        return flumeService.backFlumes(flume);
    }
    //flume重启
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/resetFlumes")
    public boolean resetFlumes(@ModelAttribute Flume flume){
        return flumeService.resetFlumes(flume);
    }
    //flume停止
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/stopFlumes")
    public boolean stopFlumes(@ModelAttribute Flume flume){
        return flumeService.stopFlumes(flume);
    }
    //更新状态
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/refreshFlumes")
    public boolean refreshFlumes(){
        return flumeService.refreshFlumes();
    }

    //校验文件名唯一
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/checkFlumesByConfigName")
    public boolean checkFlumesByConfigName(String configName){
        return flumeService.refreshFlumes();
    }

    //查询所有服务文件夹下配置
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/queryFlumeOnlineFile")
    public List<Flume> queryFlumeOnlineFile(String hostServeId) throws Exception {
        return  flumeService.queryFlumeOnlineFile(hostServeId);
    }

    //批量导入数据库
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/flumeOnlineImport")
    public boolean flumeOnlineImport(@RequestBody Flume[] flumes) throws Exception {
        return flumeService.flumeOnlineImport(flumes);
    }
}
