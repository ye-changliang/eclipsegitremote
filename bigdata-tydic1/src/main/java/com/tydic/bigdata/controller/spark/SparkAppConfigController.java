package com.tydic.bigdata.controller.spark;

import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.spark.Spark;
import com.tydic.bigdata.service.host.HostServeInfoService;
import com.tydic.bigdata.service.spark.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/spark-app")
public class SparkAppConfigController {
    @Autowired
    private SparkService sparkService;

    @Autowired
    private HostServeInfoService hostServeInfoService;

    /**
     * 跳转spark应用及配置管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/application")
    public String sparkApplication() {
        return "spark/spark-application";
    }

    //通过IP和应用名称查找列表
    @RequestMapping(method = RequestMethod.GET, path = "/findSparkByIpAndConfigNameAndAppNameIsNotNull")
    public ResponseEntity findSparkByIpAndAppNameIsNotNull(@PageableDefault(page = 1) Pageable pageable, Spark spark){
        Page page= sparkService.findSparkByIpAndConfigNameAndAppNameIsNotNull(pageable,spark);
        return ResponseEntity.ok(page);
    }

    //保存
    @RequestMapping(method = RequestMethod.POST, path = "/sparkAppSave")
    @ResponseBody
    public boolean sparkAppSave(@ModelAttribute Spark spark) throws Exception {
        return sparkService.saveAppSpark(spark);
    }

    /**
     * 测试链接
     */
    @RequestMapping(method = RequestMethod.POST, path = "/testHostConnet")
    @ResponseBody
    public boolean testHostConnet(HostServeInfo hostServeInfo){
        return hostServeInfoService.testHostConnet(hostServeInfo);
    }

    //spark备份
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/backSparks")
    public boolean backSparks(@ModelAttribute Spark spark) throws Exception {
        return sparkService.backSparks(spark);
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteSparks")
    public ResponseEntity batchDeleteSparks(@RequestBody Spark[] sparks) throws Exception {
        return ResponseEntity.ok(sparkService.batchDeleteSparks(sparks));
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteSparksData")
    public ResponseEntity batchDeleteSparksData(@RequestBody Spark[] sparks) throws Exception {
        return ResponseEntity.ok(sparkService.batchDeleteSparksData(sparks));
    }

    //查询所有服务文件夹下配置
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/querySparkAppOnlineFile")
    public List<Spark> querySparkAppOnlineFile(String hostServeId) throws Exception {
        return  sparkService.querySparkOnlineFile(hostServeId,"app");
    }

    //批量导入数据库
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/sparkAppOnlineImport")
    public boolean sparkAppOnlineImport(@RequestBody Spark[] sparks) throws Exception {
        return sparkService.sparkOnlineImport(sparks,"app");
    }


}
