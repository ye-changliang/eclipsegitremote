package com.tydic.bigdata.service.hive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tydic.bigdata.domain.hive.HiveReg;
import com.tydic.bigdata.domain.hostInfo.HostInfo;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.repository.hive.HiveRegRepository;

@Service
public class HiveRegService {
	 
	 @Autowired
     private HiveRegRepository hiveRegRepository;
	 
	 /*
	  * 保存事件
	  * */
	 public boolean saveHiveReg(HiveReg hivereg){
		 Calendar calendar = Calendar.getInstance();
		 Date date=new Date();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, +1);
		 date = calendar.getTime();
		 
		 hivereg.setRegTime(date);
		 //对应的租户生成随机的密钥
		 UUID uuid = UUID.randomUUID();
		 String str = uuid.toString();
			// 去掉"-"符号
			String temp = str.substring(0, 8) + str.substring(9, 13)
					+ str.substring(14, 18) + str.substring(19, 23)
					+ str.substring(24);
		// System.out.println("得到的密钥为="+temp);
		 hivereg.setUserKey(temp);
		 hiveRegRepository.save(hivereg);
		 return true;
	 }
	 
	public static void main(String[] args) {
		/*UUID uuid = UUID.randomUUID();
		 String str = uuid.toString();
		 // 去掉"-"符号
		 String temp = str.substring(0, 8) + str.substring(9, 13)
					+ str.substring(14, 18) + str.substring(19, 23)
					+ str.substring(24);
		 System.out.println("得到的密钥为="+temp);
		 Date date = new Date();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     String specifiedDay = sdf.format(date);*/
		 //Calendar c = Calendar.getInstance();
        //Date date = null;
		//Date date=new Date();
		//System.out.println("当前日期为：="+specifiedDay);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String specifiedDay = sdf.format(date);
		System.out.println(specifiedDay);
	}
	 
	 
	 /*
	  * 更新事件
	  * */
	 public boolean updateHiveReg(HiveReg hivereg,long ids ){
		 hivereg.setId(ids);
		 hivereg.setRegTime(new Date());//修改后的注册时间还是以当前的为准
		 hiveRegRepository.save(hivereg);
		 return true;
	 }
	 
	 
	 
	 /*
	  * 删除事件 单个删除
	  * */
	 public boolean deleteHiveReg(Long id){
		 //String flag="";
		 if (id!=null){
		       hiveRegRepository.deleteById(id);
		       return true;
		       }
		 return false;
	 }
	 
	 /*
	  * 批量删除
	  * */
	 public boolean BatchdeleteHiveReg(HiveReg[] hiveRegs){
		    String flag="";
	        List<Long> ids = new ArrayList<>();
	        Arrays.stream(hiveRegs).forEach(hiveReg ->
	                ids.add(hiveReg.getId())
	        );
	        hiveRegRepository.batchDeletehive(ids);
			return true;
			
	        
	 }
	 
	 
	 /*查询分页*/
	 public Page<HiveReg> findAlls(Pageable pageable){
	      return   hiveRegRepository.findAll(pageable);
	  }
}
