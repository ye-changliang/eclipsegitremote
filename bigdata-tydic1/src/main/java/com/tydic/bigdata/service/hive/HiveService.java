package com.tydic.bigdata.service.hive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.bigdata.domain.hive.Hive;
import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
import com.tydic.bigdata.repository.hive.HiveRepository;

@Service
public class HiveService {
	
	@Autowired
	private HiveRepository hiveRepository;
	
	/**
     * 查询所有
     */
    public List<Hive> FindAllList(){
      return   hiveRepository.findAll();
    }
}
