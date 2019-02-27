package com.tydic.bigdata.service.hive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tydic.bigdata.domain.hive.HiveRef;
import com.tydic.bigdata.domain.hive.HiveReg;
import com.tydic.bigdata.domain.hive.HiveSet;
import com.tydic.bigdata.repository.hive.HiveRefRepository;
import com.tydic.bigdata.repository.hive.HiveSetRepository;


@Service
public class HiveSetService {
	
	
	@Autowired
	private HiveSetRepository hiveSetRepository;
	
	@Autowired
	private HiveRefRepository hiveRefRepository;
	 public boolean saveHiveSet(HiveSet hiveSet){
		 HiveRef hiveRef=new HiveRef();
		 hiveRef.setHiveName(hiveSet.getHiveName());
		 hiveRef.setUserName(hiveSet.getUserName());
		 Calendar calendar = Calendar.getInstance();
		 Date date=new Date();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, +1);
		 date = calendar.getTime();
		 hiveSet.setSetTime(date);
		 hiveRefRepository.save(hiveRef);
		 hiveSetRepository.save(hiveSet);
		 return true;
	 }
	 
	 /*
	  * 配置信息修改
	  * */
	
	 public boolean updateHiveSet(HiveSet hiveSet,long ids ){
		 hiveSet.setId(ids);
		 hiveSet.setSetTime(new Date());
		 hiveSetRepository.save(hiveSet);
		 return true;
	 }
	 
	 /*
	  * 删除事件 单个删除
	  * */
	 public boolean deleteHiveSet(Long id){
		 //String flag="";
		 if (id!=null){
		       hiveSetRepository.deleteById(id);
		       return true;
		       }
		 return false;
	 }
	 
	 /*
	  * 批量删除
	  * */
	public boolean BatchdeleteHive(HiveSet[] hiveSets){
		    String flag="";
	        List<Long> ids = new ArrayList<>();
	        
	        Arrays.stream(hiveSets).forEach(hiveSet ->
	                ids.add(hiveSet.getId())
	        );
	        hiveSetRepository.batchDeletehiveset(ids);
			return true;
			
	        
	 }
	
	 
	 
	 public Page<HiveSet> findAll(Pageable pageable){
	      return    hiveSetRepository.findAll(pageable);
	  }
	 
	 
}
