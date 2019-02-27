package com.tydic.bigdata.repository.hive;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tydic.bigdata.domain.hive.HiveMonitor;
import com.tydic.bigdata.domain.redis.Redis;

@Repository
public interface HiveMonitorRepository extends JpaRepository<HiveMonitor,Long> {
	
	
	/**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from HiveMonitor e where e.clusterName in (:ids) ")
    int batchDeletehiveMonitor(@Param("ids") List<String> ids);
    
    
    /**
     * group by 集群和hive
     */
    @Query(value = "SELECT max(id) as  id, clusterName, hiveName, sum(totalNum) as totalNum, sum(nowNum) as nowNum, sum(idleNum) as idleNum FROM " +
            "comm_hive_monitor WHERE clusterName LIKE CONCAT('%',?,'%') " +
            "GROUP BY clusterName, hiveName LIMIT ?,?"  ,nativeQuery = true)
    List<HiveMonitor> findAllMonitorList(String clusterName, Long start, Integer pageSize);
}
