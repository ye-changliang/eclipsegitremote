package com.tydic.bigdata.controller.taskconfig;

import com.tydic.bigdata.domain.shortlink.ShortLink;
import com.tydic.bigdata.domain.taskconfig.TaskInfo;
import com.tydic.bigdata.service.taskconfig.TaskApplyService;
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
@RequestMapping("/task-apply")
public class TaskApplyController {

    @Autowired
    private TaskApplyService taskApplyService;

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index() {
        return "task-config/task-apply";
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
        return ResponseEntity.ok(page);
    }

    /**
     * 保存操作
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public boolean save(@ModelAttribute TaskInfo taskInfo) {
        return taskApplyService.save(taskInfo);
    }


    /**
     * 查询任务
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/findTask")
    public ResponseEntity findTaskByNameOrId(@ModelAttribute TaskInfo taskInfo) {
        return ResponseEntity.ok(taskApplyService.findTaskByNameOrId(taskInfo));
    }

    /**
     * 查询任务组件
     * @param jobId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/findTaskDataSource")
    public ResponseEntity findJobDataSource(String jobId,String type) {
        return ResponseEntity.ok(taskApplyService.findAllChildrenJobDataSource(jobId,type));
    }


    /**
     * 查找数据源
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/findDataSources")
    public ResponseEntity findAllDataBase(String id) {
        return ResponseEntity.ok(taskApplyService.findAllDataBase(id));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/release")
    public ResponseEntity release(String jobId,String type,String dataBaseId,String jobEntryId,Long id) {
        return ResponseEntity.ok(taskApplyService.release(jobId,type,dataBaseId,jobEntryId,id));
    }


    /**
     * 保存操作
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/check")
    public boolean check(@ModelAttribute TaskInfo taskInfo) {
        return taskApplyService.check(taskInfo);
    }






}
