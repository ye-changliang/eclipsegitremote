package com.tydic.bigdata.controller.redis;

import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.domain.redis.Redis;
import com.tydic.bigdata.service.host.HostServeInfoService;
import com.tydic.bigdata.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/redis")
public class RedisConfigController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private HostServeInfoService hostServeInfoService;
    /**
     * 跳转Redis管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String redis() {
        return "redis/redis";
    }

    /**
     * 跳转redis监控管理页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/monitor")
    public String redisMonitor() {
        return "redis/redis-monitor";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findRedisByIp")
    public ResponseEntity findRedisByIp(@PageableDefault(page = 1) Pageable pageable, Redis redis){
        Page page= redisService.findRedisByIp(pageable,redis);
        return ResponseEntity.ok(page);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/findAllRedisMonitorGroupBy")
    public List<Redis> findAllRedisMonitorGroupBy(){
        List<Redis> redisList= redisService.findAllRedisMonitorGroupBy();
        return redisList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findRedisAndGroupByIp")
    public List<Redis> findRedisAndGroupByIp(){
        List<Redis> redisList= redisService.findRedisAndGroupByIp();
        return redisList;
    }

    //保存
    @RequestMapping(method = RequestMethod.POST, path = "/redisSave")
    @ResponseBody
    public boolean redisSave(@ModelAttribute Redis redis) throws Exception {
        return redisService.saveRedis(redis);
    }

   /* //查询所有服务
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/findAllHostServeInfo")
    public List<HostServeInfo> findAllHostServeInfo(){
        return  hostServeInfoService.findAllHostServeInfo();
    }*/

    /**
     * 测试链接
     */
    @RequestMapping(method = RequestMethod.POST, path = "/testHostConnet")
    @ResponseBody
    public boolean testHostConnet(HostServeInfo hostServeInfo){
        return hostServeInfoService.testHostConnet(hostServeInfo);
    }



    //redis备份
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/backRediss")
    public boolean backRediss(@ModelAttribute Redis redis) throws Exception {
        return redisService.backRediss(redis);
    }
    //redis重启
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/resetRediss")
    public boolean resetRediss(@ModelAttribute Redis redis){
        return redisService.resetRediss(redis);
    }
    //redis停止
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/stopRediss")
    public boolean stopRediss(@ModelAttribute Redis redis){
        return redisService.stopRediss(redis);
    }
    //更新状态
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/refreshRediss")
    public boolean refreshRediss(){
        return redisService.refreshRediss();
    }

    //查询所有服务文件夹下配置
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/queryRedisOnlineFile")
    public List<Redis> queryRedisOnlineFile(String hostServeId) throws Exception {
        return  redisService.queryRedisOnlineFile(hostServeId);
    }

    //批量导入数据库
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/redisOnlineImport")
    public boolean redisOnlineImport(@RequestBody Redis[] redis) throws Exception {
        return redisService.redisOnlineImport(redis);
    }

    //批量删除
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteRedis")
    public ResponseEntity batchDeleteRedis(@RequestBody Redis[] redis) throws Exception {
        return ResponseEntity.ok(redisService.batchDeleteRedis(redis));
    }
    //批量删除数据库信息
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteRedisData")
    public ResponseEntity batchDeleteRedisData(@RequestBody Redis[] redis) throws Exception {
        return ResponseEntity.ok(redisService.batchDeleteRedisData(redis));
    }

}
