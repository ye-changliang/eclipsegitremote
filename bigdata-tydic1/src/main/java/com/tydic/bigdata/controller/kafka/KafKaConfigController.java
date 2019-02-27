package com.tydic.bigdata.controller.kafka;

import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.kafka.KafKa;
import com.tydic.bigdata.service.host.HostServeInfoService;
import com.tydic.bigdata.service.kafka.KafKaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kafKa")
public class KafKaConfigController {

    @Autowired
    private KafKaService kafKaService;

    @Autowired
    private HostServeInfoService hostServeInfoService;
    /**
     * 跳转KafKa管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String kafKa() {
        return "kafKa/kafKa";
    }

    /**
     * 跳转KafKa监控管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/monitor")
    public String kafKaMonitor() {
        return "kafKa/kafKa-monitor";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findKafKaByIp")
    public ResponseEntity findKafKaByIp(@PageableDefault(page = 1) Pageable pageable, KafKa kafKa){
        Page page= kafKaService.findKafKaByIp(pageable,kafKa);
        return ResponseEntity.ok(page);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/findAllKafKaMonitorGroupBy")
    public List<KafKa> findAllKafKaMonitorGroupBy(){
        List<KafKa> kafKaList= kafKaService.findAllKafKaMonitorGroupBy();
        return kafKaList;
    }

    //保存
    @RequestMapping(method = RequestMethod.POST, path = "/kafKaSave")
    @ResponseBody
    public boolean kafKaSave(@ModelAttribute KafKa kafKa) throws Exception {
        return kafKaService.saveKafKa(kafKa);
    }

    //查询所有kafka服务文件夹下配置
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/queryKafKaOnlineFile")
    public List<KafKa> queryKafKaOnlineFile(String hostServeId) throws Exception {
        return  kafKaService.queryKafKaOnlineFile(hostServeId);
    }

    /**
     * 测试链接
     */
    @RequestMapping(method = RequestMethod.POST, path = "/testHostConnet")
    @ResponseBody
    public boolean testHostConnet(HostServeInfo hostServeInfo){
        return hostServeInfoService.testHostConnet(hostServeInfo);
    }


    //批量导入数据库
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/kafKaOnlineImport")
    public boolean kafKaOnlineImport(@RequestBody KafKa[] kafKas) throws Exception {
        return kafKaService.kafKaOnlineImport(kafKas);
    }

    //KafKa备份
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/backKafKas")
    public boolean backKafKas(@ModelAttribute KafKa kafKa) throws Exception {
        return kafKaService.backKafKas(kafKa);
    }
    //KafKa重启
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/resetKafKas")
    public boolean resetKafKas(@ModelAttribute KafKa kafKa){
        return kafKaService.resetKafKas(kafKa);
    }
    //KafKa停止
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/stopKafKas")
    public boolean stopKafKas(@ModelAttribute KafKa kafKa){
        return kafKaService.stopKafKas(kafKa);
    }
    //更新状态
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/refreshKafKas")
    public boolean refreshKafKas(){
        return kafKaService.refreshKafKas();
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteKafKas")
    public ResponseEntity batchDeleteKafKas(@RequestBody KafKa[] kafKas) throws Exception {
        return ResponseEntity.ok(kafKaService.batchDeleteKafKas(kafKas));
    }

    //批量删除数据库信息
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteKafKasData")
    public ResponseEntity batchDeleteKafKasData(@RequestBody KafKa[] kafKas) throws Exception {
        return ResponseEntity.ok(kafKaService.batchDeleteKafKasData(kafKas));
    }
}
