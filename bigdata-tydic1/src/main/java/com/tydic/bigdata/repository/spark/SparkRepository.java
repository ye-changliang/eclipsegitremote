package com.tydic.bigdata.repository.spark;

import com.tydic.bigdata.domain.spark.Spark;
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
public interface SparkRepository extends JpaRepository<Spark,Long> {
    /**
     * 根据主机、服务类型、应用名查询主机数据
     */
    Page<Spark> findAllByConfigTypeAndIpContainingAndAppNameContainingAndSparkConfigContainingAndAppNameIsNotNullOrderByUpdateDateDesc(String configType, String ip,String appName,String sparkConfig, Pageable pageable);
    /**
     * 根据主机、服务类型、应用名查询主机数据
     */
    Page<Spark> findAllByConfigTypeAndIpContainingAndAppNameIsNullOrderByUpdateDateDesc(String configType, String ip, Pageable pageable);

    /**
     * 查询spark
     */
    List<Spark> findAllByIpAndConfigTypeAndSparkConfigPathAndSparkConfig(String ip,String configType,String sparkConfigPath,String SparkConfig);

    /**
     * 通过主机服务Id查找
     */
    List<Spark> findAllByHostServeId(Long hostServeId);
    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from Spark e where e.sparkId in (:ids) ")
    int batchDeleteSparks(@Param("ids") List<Long> ids);

    /**
     * 通过sparkId查找
     */
    Spark findBySparkId(Long sparkId);

    /**
     * 通过configType查找所有
     */
    List<Spark> findAllByConfigType(String configType);
    /**
     * 通过configType查找所有应用级spark
     */
    List<Spark> findAllByConfigTypeAndAppNameIsNotNull(String configType);
    /**
     * group by IP查询spark
     */
    @Query(value = "select e.id as id,e.ip as ip,e.status as status from comm_sys_fkr_config e where e.config_type='spark' group by e.ip " ,nativeQuery = true)
    List<Spark> findAllByConfigTypeAndIp( );
}
