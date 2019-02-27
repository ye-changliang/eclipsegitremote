package com.tydic.bigdata.repository.redis;

import com.tydic.bigdata.domain.redis.Redis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RedisRepository extends JpaRepository<Redis,Long> {
    /**
     * 根据主机查询主机数据
     */
    Page<Redis> findAllByConfigTypeAndIpContainingOrderByUpdateDateDesc(String configType,String ip, Pageable pageable);

    /**
     * 查询kafka监控
     */
    @Query(value = "SELECT r.redisId, r.ip ,r.status FROM Redis r where r.configType='redis' GROUP BY r.ip ")
    List<Redis> findAllRedisMonitorGroupBy();
    /**
     * 精确查询redis
     */
    List<Redis> findAllByIpAndConfigTypeAndRedisConfigPathAndRedisConfig(String ip,String configType,String redisConfigPath,String redisConfig);
    /**
     * 通过主机服务Id查找
     */
    List<Redis> findAllByHostServeId(Long hostServeId);
    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from Redis e where e.redisId in (:ids) ")
    int batchDeleteRediss(@Param("ids") List<Long> ids);

    /**
     * 通过RedisId查找
     */
    Redis findByRedisId(Long redisId);

    /**
     * 通过configType查找所有
     */
    List<Redis> findAllByConfigType(String configType);

    /**
     * group by IP查询redis
     */
    @Query(value = "select e.id as id,e.ip as ip,e.status as status from comm_sys_fkr_config e where e.config_type='redis' group by e.ip " ,nativeQuery = true)
    List<Redis> findAllByConfigTypeAndIp( );

}
