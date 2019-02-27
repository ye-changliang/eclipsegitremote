package com.tydic.bigdata.controller.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiConfigMenu;
import com.tydic.bigdata.service.apiconfig.ApiConfigMenuService;
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
@RequestMapping("/api-config")
public class ApiConfigMenuController {

    @Autowired
    private ApiConfigMenuService apiConfigMenuService;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "apiconfig/api-config";
    }

    /**
     * 分页查找
     *
     * @param pageable
     * @param apiConfigMenu
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/apis")
    @ResponseBody
    public ResponseEntity findAll(@PageableDefault Pageable pageable, ApiConfigMenu apiConfigMenu) {
        return ResponseEntity.ok(apiConfigMenuService.findAll(pageable, apiConfigMenu));
    }


    /**
     * 保存方法
     *
     * @param apiConfigMenu
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    @ResponseBody
    public ResponseEntity save(ApiConfigMenu apiConfigMenu) {
        return ResponseEntity.ok(apiConfigMenuService.save(apiConfigMenu));
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
        return ResponseEntity.ok(apiConfigMenuService.delete(ids));
    }


}
