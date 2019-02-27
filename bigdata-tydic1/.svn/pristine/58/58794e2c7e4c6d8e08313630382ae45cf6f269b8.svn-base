package com.tydic.bigdata.repository.hostServeInfo;

import com.tydic.bigdata.domain.hostServleInfo.HostServeInfo;
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
public interface HostServeInfoRepository extends JpaRepository<HostServeInfo,Long> {
    /**
     * 根据主机查询主机数据
     */
    Page<HostServeInfo> findAllByHostServeNameContaining(String hostServeName, Pageable pageable);

    /**
     * 查询所有
     */
    List<HostServeInfo> findAllByHostServeNameContaining(String hostServeName);

    /**
     * 根据Id查找
     */
    HostServeInfo findByHostServeId(Long hostServeId);

    /**
     * 通过主机信息Id查找
     */
    List<HostServeInfo> findAllByHostId(Long hostId);


    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from HostServeInfo e where e.hostServeId in (:ids) ")
    int batchDeleteHostServeInfo(@Param("ids") List<Long> ids);
}
