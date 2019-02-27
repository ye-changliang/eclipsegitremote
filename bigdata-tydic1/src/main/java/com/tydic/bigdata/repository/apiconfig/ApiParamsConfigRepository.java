package com.tydic.bigdata.repository.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiParamsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApiParamsConfigRepository extends JpaRepository<ApiParamsConfig, Long> {
    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update ApiParamsConfig e  set e.status=0 where e.id in (:ids) ")
    int deleteByIds(@Param("ids") List<Long> ids);
}
