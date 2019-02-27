package com.tydic.bigdata.service.hive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tydic.bigdata.domain.hive.HiveMonitor;
import com.tydic.bigdata.domain.hive.HiveTask;
import com.tydic.bigdata.repository.hive.HiveSetRepository;
import com.tydic.bigdata.repository.hive.HiveTaskRepository;

@Service
public class HiveTaskService {

	@Autowired
	private HiveTaskRepository hiveTaskRepository;
	
	public Page<HiveTask> findAllR(Pageable pageable,HiveTask hiveTask){
		 ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				 .withMatcher("clusterName", ExampleMatcher.GenericPropertyMatcher::contains);
	      return   hiveTaskRepository.findAll(Example.of(hiveTask,exampleMatcher),pageable);
	  }
	
	 /*
	  * 删除事件 单个删除
	  * */
	 public boolean deleteHiveTask(String id){
		 //String flag="";
		 if (id!=null){
			 hiveTaskRepository.delete(id);
		       return true;
		       }
		 return false;
	 }
	 
	 //
	 
	 /*
	  * 批量删除
	  * */
	 public boolean BatchdeleteHiveTask(HiveTask[] hiveTasks){
		    String flag="";
	        List<String> ids = new ArrayList<>();
	        
	        Arrays.stream(hiveTasks).forEach(hiveTask ->
	                ids.add(hiveTask.getId())
	        );
	        hiveTaskRepository.batchDeletehiveTasks(ids);
			return true;
			
	        
	 }
}
