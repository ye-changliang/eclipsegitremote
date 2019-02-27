package com.tydic.bigdata.repository.flume;

import com.tydic.bigdata.domain.flume.Flume;
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
public interface FlumeRepository extends JpaRepository<Flume,Long> {
    /**
     * 根据主机查询主机数据
     */
    Page<Flume> findAllByConfigTypeAndIpContaining(String configType,String ip, Pageable pageable);

    List<Flume> findAllByIpAndConfigTypeAndFlumeConfigPathAndFlumeConfig(String ip,String configType,String flumeConfigPath,String flumeConfig);

    /**
     * 根据主机和文件名查询主机数据
     */
    Page<Flume> findAllByConfigTypeAndIpContainingAndFlumeConfigContainingOrderByUpdateDateDesc(String configType,String ip,String flumeConfig, Pageable pageable);
    /**
     * 通过主机服务Id查找
     */
    List<Flume> findAllByHostServeId(Long hostServeId);
    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from Flume e where e.flumeId in (:ids) ")
    int batchDeleteFlumes(@Param("ids") List<Long> ids);

    /**
     * 通过flumeId查找
     */
    Flume findByFlumeId(Long flumeId);

    //通过文件名查找
    List<Flume> findAllByFlumeConfigAndConfigType(String configName,String configType);
    //通过configType查找
    List<Flume> findAllByConfigType(String configType);

}
