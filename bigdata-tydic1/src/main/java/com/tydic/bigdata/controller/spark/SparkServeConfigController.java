package com.tydic.bigdata.controller.spark;

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
@RequestMapping("/spark-serve")
public class SparkServeConfigController {
    @Autowired
    private SparkService sparkService;

    @Autowired
    private HostServeInfoService hostServeInfoService;
    /**
     * 跳转spark服务及管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/serve")
    public String spark() {
        return "spark/spark-serve";
    }

    /**
     * 查询spark服务级列表配置，应用名为空
     * @param spark
     * @return
     * @throws Exception
     */

    @RequestMapping(method = RequestMethod.GET, path = "/findSparkByIpAndAppNameIsNull")
    public ResponseEntity findSparkByIpAndAppNameIsNull(@PageableDefault(page = 1) Pageable pageable, Spark spark){
        Page page= sparkService.findSparkByIpAndConfigTypeAndAppNameIsNull(pageable,spark);
        return ResponseEntity.ok(page);
    }

    //保存
    @RequestMapping(method = RequestMethod.POST, path = "/sparkSave")
    @ResponseBody
    public boolean sparkSave(@ModelAttribute Spark spark) throws Exception {
        return sparkService.saveSpark(spark);
    }


    //spark备份
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/backSparks")
    public boolean backSparks(@ModelAttribute Spark spark) throws Exception {
        return sparkService.backSparks(spark);
    }


    //查询所有kafka服务文件夹下配置
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/querySparkOnlineFile")
    public List<Spark> querySparkOnlineFile(String hostServeId) throws Exception {
        return  sparkService.querySparkOnlineFile(hostServeId,"");
    }

    //批量导入数据库
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/sparkOnlineImport")
    public boolean sparkOnlineImport(@RequestBody Spark[] sparks) throws Exception {
        return sparkService.sparkOnlineImport(sparks,"");
    }
}
