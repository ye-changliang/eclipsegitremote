package com.tydic.bigdata.repository.hive;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tydic.bigdata.domain.hive.HiveReg;

/**
 * 访问数据层
 */
@Repository
public interface HiveRegRepository extends JpaRepository<HiveReg,Long> {

	/**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from HiveReg e where e.id in (:ids) ")
    int batchDeletehive(@Param("ids") List<Long> ids);
}
