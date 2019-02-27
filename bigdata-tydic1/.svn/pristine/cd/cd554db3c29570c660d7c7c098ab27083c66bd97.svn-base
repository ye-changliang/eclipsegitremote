package com.tydic.bigdata.repository.hostInfo;

import com.tydic.bigdata.domain.hostInfo.HostInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 访问数据层
 */
@Repository
public interface HostInfoRepository extends JpaRepository<HostInfo,Long> {
    /**
     * 根据主机查询主机数据
     */
    Page<HostInfo> findAllByHostContaining(String host, Pageable pageable);

    /**
     * 活动所有主机信息
     *
     */
    List<HostInfo> findAll();

    /**
     * 通过id查主机信息
     */
    HostInfo findByHostId(Long hostId);

    /**
     *通过Id 删除主机信息
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    int deleteByHostId(Long hostId);

    /**
     * 批量删除
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from HostInfo e where e.hostId in (:ids) ")
    int batchDeleteHostInfo(@Param("ids") List<Long> ids);
}
