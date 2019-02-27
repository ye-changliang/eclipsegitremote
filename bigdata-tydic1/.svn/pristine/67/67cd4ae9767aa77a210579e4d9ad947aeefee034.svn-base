package com.tydic.bigdata.controller.hive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.bigdata.domain.hive.Hive;
import com.tydic.bigdata.domain.hive.HiveMonitor;
import com.tydic.bigdata.domain.hive.HiveReg;
import com.tydic.bigdata.domain.hive.HiveSet;
import com.tydic.bigdata.domain.hive.HiveTask;
import com.tydic.bigdata.repository.hive.HiveRegRepository;
import com.tydic.bigdata.service.hive.HiveMonitorService;
import com.tydic.bigdata.service.hive.HiveRegService;
import com.tydic.bigdata.service.hive.HiveService;
import com.tydic.bigdata.service.hive.HiveSetService;
import com.tydic.bigdata.service.hive.HiveTaskService;




@Controller
@RequestMapping("/hive")
public class HiveConfigController {
	
	 @Autowired
	 private HiveService hiveService;
	 
	 @Autowired
	 private HiveSetService hivesetService;
	 
	 @Autowired
	 private HiveRegService hiveregService;
	 
	 @Autowired
	 private HiveMonitorService hiveMonitorService;
	 
	 @Autowired
	 private HiveTaskService hiveTaskService;
	 
	 
	 /**
	 * 注册界面集群名称获取方法
	 * 
	 * @return
	 */
	 @RequestMapping(method = RequestMethod.POST, path = "/allHivejq")
	    public List<Hive> JquGetList() {
		 List<Hive> HiveList=hiveService.FindAllList();
			return HiveList;
	    }
	 
	 /**
     * 跳转hive组件配置页面
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String hive() {
        return "hive/hive";
    }
    
    /**
     * 跳转hive主页面
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/set")
    public String hiveSet() {
        return "hive/hive-set";
    }
    
    /**
     * 跳转hive接入监控管理
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/monitor")
    public String hiveMonitor() {
        return "hive/hive-monitor";
    }
    
    /**
     * 跳转hive任务日志列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/task")
    public String hiveTakk() {
        return "hive/hive-task";
    }
    
    /**
     * 组件配置保存入库
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/hiveSave")
    @ResponseBody
    public Boolean HiveSave(HiveSet hiveSet) {
    	//System.out.println("zou");
		return hivesetService.saveHiveSet(hiveSet);
    }
    
    
    /**
     * 配置完成后 查询配置列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/hivesetList")
    @ResponseBody
     public Page<HiveSet> findAllList(@PageableDefault Pageable pageable) {
    	  return hivesetService.findAll(pageable);
     }
    
    
    /**
     * 配置信息单个删除
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/deleteHiveSet")
    @ResponseBody
    public boolean HiveSetDelete(Long id) {
		return hivesetService.deleteHiveSet(id);
   }
    
    /**
     * 配置信息批量删除
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHiveSet")
    @ResponseBody
    public ResponseEntity HiveSetBatchDelete(@RequestBody HiveSet[] hiveSets) {
 		return ResponseEntity.ok(hivesetService.BatchdeleteHive(hiveSets));
    }
    
    /**
     * 租户注册激活部分
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/hiveAdd")
    @ResponseBody
    public Boolean HiveRegSave(HiveReg hivereg) {
		return hiveregService.saveHiveReg(hivereg);
    }
    
    /**
     * 租户配置信息修改
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path ="/hiveSetUpdate")
    @ResponseBody
    public Boolean HiveSetUpdate(HiveSet hiveset,Long id) {
		return hivesetService.updateHiveSet(hiveset,id);
    }
    
    
    /**
     * 租户信息修改
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/hiveUpdate")
    @ResponseBody
    public Boolean HiveRegUpdate(HiveReg hivereg,Long id) {
		return hiveregService.updateHiveReg(hivereg,id);
    }
    
    
    /**
    * 租户信息删除
    * @return
    */
    
    
   @RequestMapping(method = RequestMethod.POST, path = "/deleteHivereg")
   @ResponseBody
   public boolean HiveRegDelete(Long id) {
		return hiveregService.deleteHiveReg(id);
   }
   
   
   /**
    * 租户信息批量删除
    * @return
    */
   @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHive")
   @ResponseBody
   public ResponseEntity HiveRegBatchDelete(@RequestBody HiveReg[] hiveRegs) {
		return ResponseEntity.ok(hiveregService.BatchdeleteHiveReg(hiveRegs));
   }
    
   
    /**
     * 注册完成后 查询注册列表
     * @return
     */
    
   
    @RequestMapping(method = RequestMethod.GET, path = "/hiveregList")
    @ResponseBody
     public Page<HiveReg> findAllLists(@PageableDefault Pageable pageable) {
    	  return hiveregService.findAlls(pageable);
     }
    
    
    /**
     * 监控管理模块查询列表
     * @return
     */
    
    @RequestMapping(method = RequestMethod.GET, path = "/hivemonitorList")
    @ResponseBody
     public Page<HiveMonitor> findAllJList(@PageableDefault Pageable pageable,HiveMonitor hiveMonitor) {
    	  return hiveMonitorService.findAllJ(pageable,hiveMonitor);
     }
    
    
    
    /**
     * 监控管理信息修改
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/HiveMonitorSave")
    @ResponseBody
    public Boolean HiveMonitorUpdate(HiveMonitor hiveMonitor,Long id) {
		return hiveMonitorService.updateHiveMonitor(hiveMonitor,id);
    }

    
    
    /**
     * 监控信息删除
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/deleteHiveMonitor")
    @ResponseBody
    public boolean HiveMonitorDeletes(@RequestBody HiveMonitor[] hiveMonitors) {
 		return hiveMonitorService.BatchdeleteHiveMonitor(hiveMonitors);
    }
    
    
    
    /**
     * 监控管理信息批量删除
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHiveMonitor")
    @ResponseBody
    public ResponseEntity HiveMonBatchDelete(@RequestBody HiveMonitor[] hiveMonitors) {
 		return ResponseEntity.ok(hiveMonitorService.BatchdeleteHiveMonitor(hiveMonitors));
    }
    
    /**
     * 任务日志管理模块查询列表
     * @return
     */
    
    @RequestMapping(method = RequestMethod.GET, path = "/hivetaskLists")
    @ResponseBody
     public Page<HiveTask> findAllRList(@PageableDefault Pageable pageable,HiveTask hiveTask) {
    	  return hiveTaskService.findAllR(pageable,hiveTask); ///
     }
    
    
    /**
     * 任务日志信息删除
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/deleteHiveTask")
    @ResponseBody
    public boolean HiveTaskDeletes(String  id) {
 		return hiveTaskService.deleteHiveTask(id);
    }
    
    
    /**
     * 日志管理信息批量删除
     * @return
     */
   @RequestMapping(method = RequestMethod.DELETE, path = "/batchDeleteHiveTask")
    @ResponseBody
    public ResponseEntity HiveTaskBatchDelete(@RequestBody HiveTask[] hiveTasks) {
 		return ResponseEntity.ok(hiveTaskService.BatchdeleteHiveTask(hiveTasks));
    }
    
}
