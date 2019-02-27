package com.tydic.bigdata.repository.kafka;

import com.tydic.bigdata.domain.kafka.KafKa;
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
public interface KafKaRepository  extends JpaRepository<KafKa,Long> {
    /**
     * 根据主机查询主机数据
     */
    Page<KafKa> findAllByConfigTypeAndIpContainingOrderByUpdateDateDesc(String configType,String ip, Pageable pageable);
    /**
     * 查询kafka监控
     */
    @Query(value = "SELECT k.kafkaId, k.ip ,k.status FROM KafKa k where k.configType='kafka' GROUP BY k.ip ")
    List<KafKa> findAllKafKaMonitorGroupBy();
    /**
     * 查询kafka
     */
    List<KafKa> findAllByIpAndConfigTypeAndKafkaConfigPathAndKafkaConfig(String ip,String configType,String kafkaConfigPath,String kafkaConfig);
    /**
     * 通过主机服务Id查找
     */
    List<KafKa> findAllByHostServeId(Long hostServeId);
    /**
     * 通过 ip和configType查找
     */
    List<KafKa> findAllByConfigType(String configType);
    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from KafKa e where e.kafkaId in (:ids) ")
    int batchDeleteKafKas(@Param("ids") List<Long> ids);

    /**
     * 通过KafKaId查找
     */
    KafKa findByKafkaId(Long kafkaId);

}
