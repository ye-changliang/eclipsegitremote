package com.tydic.bigdata.controller.hive;


import com.tydic.bigdata.domain.hive.Hive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.bigdata.service.hive.HiveMonitorService;
import com.tydic.bigdata.service.hive.HiveRegService;
import com.tydic.bigdata.service.hive.HiveService;
import com.tydic.bigdata.service.hive.HiveSetService;
import com.tydic.bigdata.service.hive.HiveTaskService;

@Controller
@RequestMapping("/hiveselect")
public class HiveSelectController {
	
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
	 @ResponseBody
	    public List<Hive> JquGetList() {
		 List<Hive> HiveList=hiveService.FindAllList();
			return HiveList;
	    }
	 
	 
	 
	/**
	 * 注册页面租户与集群联动的获取方法
	 * 
	 * @return
	 */
	 /*@RequestMapping(method = RequestMethod.POST, path = "/")
	    public String ZJGetList() {
	        return "hive/hive11";
	    }*/
	 
	 
	 
	 
	/**
	 * 注册页面租户与集群联动的获取方法
	 * 
	 * @return
	 */
	/* @RequestMapping(method = RequestMethod.POST, path = "/")
	    public String ZJGetList2() {
	        return "hive/hive11";
	    }*/
	 
	 
	 
	 
	/**
	 * 集群分类与hive连接联动的方法
	 * 
	 * @return
	 */
	/* @RequestMapping(method = RequestMethod.POST, path = "/")
	    public String hivess() {
	        return "hive/hive11";
	    }*/
}
