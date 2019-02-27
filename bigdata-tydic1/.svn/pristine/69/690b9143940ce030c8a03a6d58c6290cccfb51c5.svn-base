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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/spark-monitor")
public class SparkMonitorContorller {
    @Autowired
    private SparkService sparkService;

    /**
     * 跳转spark监控管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/monitor")
    public String sparkMonitor() {
        return "spark/spark-monitor";
    }

    //通过IP和应用名称查找列表
    @RequestMapping(method = RequestMethod.GET, path = "/findSparkByIpAndAppNameIsNotNull")
    public ResponseEntity findSparkByIpAndAppNameIsNotNull(@PageableDefault(page = 1) Pageable pageable, Spark spark){
        Page page= sparkService.findSparkByIpAndConfigNameAndAppNameIsNotNull(pageable,spark);
        return ResponseEntity.ok(page);
    }

    //spark重启
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/resetSparks")
    public boolean resetSparks(@ModelAttribute Spark spark){
        return sparkService.resetSparks(spark);
    }
    //spark停止
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/stopSparks")
    public boolean stopSparks(@ModelAttribute Spark spark){
        return sparkService.stopSparks(spark);
    }
    //更新状态
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/refreshSparks")
    public boolean refreshSparks(){
        return sparkService.refreshSparks();
    }
}
