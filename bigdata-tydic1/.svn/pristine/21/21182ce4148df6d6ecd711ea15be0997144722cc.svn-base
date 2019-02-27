package com.tydic.bigdata.repository.hive;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tydic.bigdata.domain.hive.HiveSet;
import com.tydic.bigdata.domain.hive.HiveTask;
@Repository
public interface HiveTaskRepository extends JpaRepository<HiveTask,Long>{
	
	/**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from HiveTask e where e.id in (:ids) ")
    int batchDeletehiveTasks(@Param("ids") List<String> ids);
    
    
    //单个删除操作
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from comm_hive_logtask   where id=?1",nativeQuery = true)
	void delete(@Param("id") String id);
	
}
