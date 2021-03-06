package com.tydic.bigdata.controller.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiKafKaConfig;
import com.tydic.bigdata.service.apiconfig.ApiKafKaConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api-kafka-config")
public class ApiKafKaConfigController {

    @Autowired
    private ApiKafKaConfigService apiKafKaConfigService;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "apiconfig/api-kafka-config";
    }

    /**
     * 分页查找
     *
     * @param pageable
     * @param apiKafKaConfig
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/kafkas")
    @ResponseBody
    public ResponseEntity findAll(@PageableDefault Pageable pageable, ApiKafKaConfig apiKafKaConfig) {
        return ResponseEntity.ok(apiKafKaConfigService.findAll(pageable, apiKafKaConfig));
    }


    /**
     * 保存方法
     *
     * @param apiKafKaConfig
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    @ResponseBody
    public ResponseEntity save(ApiKafKaConfig apiKafKaConfig) {
        return ResponseEntity.ok(apiKafKaConfigService.save(apiKafKaConfig));
    }

    /**
     * 保存方法
     *
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    @ResponseBody
    public ResponseEntity delete(@RequestBody Long[] ids) {
        return ResponseEntity.ok(apiKafKaConfigService.delete(ids));
    }

    /**
     * 获取kafka属性list
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findKafkaCodeList")
    @ResponseBody
    public ResponseEntity getKafkaCodes() {
        return ResponseEntity.ok(apiKafKaConfigService.findKafkaCodeList());
    }

}
