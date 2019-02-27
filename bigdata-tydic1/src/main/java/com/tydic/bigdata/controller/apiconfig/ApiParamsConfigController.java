package com.tydic.bigdata.controller.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiKafKaConfig;
import com.tydic.bigdata.domain.apiconfig.ApiParamsConfig;
import com.tydic.bigdata.service.apiconfig.ApiParamsConfigService;
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
@RequestMapping("/api-params-config")
public class ApiParamsConfigController {

    @Autowired
    private ApiParamsConfigService apiParamsConfigService;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "apiconfig/api-params-config";
    }

    /**
     * 分页查找
     *
     * @param pageable
     * @param apiParamsConfig
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/params")
    @ResponseBody
    public ResponseEntity findAll(@PageableDefault Pageable pageable, ApiParamsConfig apiParamsConfig) {
        return ResponseEntity.ok(apiParamsConfigService.findAll(pageable, apiParamsConfig));
    }


    /**
     * 保存方法
     *
     * @param apiParamsConfig
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    @ResponseBody
    public ResponseEntity save(ApiParamsConfig apiParamsConfig) {
        return ResponseEntity.ok(apiParamsConfigService.save(apiParamsConfig));
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
        return ResponseEntity.ok(apiParamsConfigService.delete(ids));
    }
}
