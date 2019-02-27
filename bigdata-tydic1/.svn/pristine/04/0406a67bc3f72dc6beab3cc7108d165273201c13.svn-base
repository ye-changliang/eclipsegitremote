package com.tydic.bigdata.service.hive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tydic.bigdata.domain.hive.HiveMonitor;
import com.tydic.bigdata.domain.hive.HiveReg;
import com.tydic.bigdata.repository.hive.HiveMonitorRepository;
import com.tydic.bigdata.repository.hive.HiveRegRepository;

@Service
public class HiveMonitorService {
	
	 @Autowired
     private HiveMonitorRepository hiveMonitorRepository;
	 public Page<HiveMonitor> findAllJ(Pageable pageable,HiveMonitor hiveMonitor){
		 ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				 .withMatcher("clusterName", ExampleMatcher.GenericPropertyMatcher::contains);
		 List<HiveMonitor> content = hiveMonitorRepository.findAllMonitorList(hiveMonitor.getClusterName(),pageable.getOffset(),pageable.getPageSize());
		 Page<HiveMonitor> page =new PageImpl<>(content);
	     return  page;
	  }
	 
	 
	 /*
	  * 监控管理的修改
	  * */
	 public boolean updateHiveMonitor(HiveMonitor hiveMonitor,Long ids ){
//		 hiveMonitor.setId(ids);
		 hiveMonitorRepository.save(hiveMonitor);
		 return true;
	 }
	 
	 
	 /*
	  * 删除事件 单个删除
	  * */
	 public boolean deleteHiveMonitor(Long id){
		 //String flag="";
		 if (id!=null){
			 hiveMonitorRepository.deleteById(id);
		       return true;
		       }
		 return false;
	 }
	 
	 
	 /*
	  * 批量删除
	  * */
	 public boolean BatchdeleteHiveMonitor(HiveMonitor[] hiveMonitors){
		    String flag="";
	        List<String> ids = new ArrayList<>();
	        
	        Arrays.stream(hiveMonitors).forEach(hiveMonitor ->
	                ids.add(hiveMonitor.getClusterName())
	        );
	        hiveMonitorRepository.batchDeletehiveMonitor(ids);
			return true;
			
	        
	 }
}
