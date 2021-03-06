package com.tydic.bigdata.controller.taskconfig;

import com.tydic.bigdata.domain.taskconfig.TaskInfo;
import com.tydic.bigdata.service.taskconfig.TaskApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task-audit")
public class TaskAuditController {

    @Autowired
    private TaskApplyService taskApplyService;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "task-config/task-audit";
    }


    /**
     * 申请 列表数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/applies")
    public ResponseEntity shortLinks(TaskInfo taskInfo
            , @PageableDefault(page = 1) Pageable pageable) {
        Page<TaskInfo> page = taskApplyService.findAllByNameAndAuditStatus(pageable, taskInfo);
        page.getContent().forEach(taskInfo1 -> {
            taskApplyService.findStatusSaved(taskInfo1);
        });
        return ResponseEntity.ok(page);
    }


    /**
     * 审核
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/audit")
    public ResponseEntity audit(Long id, @RequestParam("type") Integer type) {
        return ResponseEntity.ok(taskApplyService.audit(id, type));
    }

    /**
     * 审核
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/check")
    public ResponseEntity audit(TaskInfo taskInfo) {
        taskApplyService.findStatus(taskInfo);
        return ResponseEntity.ok(taskInfo);
    }


}
