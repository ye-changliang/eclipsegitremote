package com.tydic.bigdata.repository.apiconfig;

import com.tydic.bigdata.domain.apiconfig.ApiConfigMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApiConfigMenuRepository extends JpaRepository<ApiConfigMenu, Long> {

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update ApiConfigMenu e  set e.status=0 where e.id in (:ids) ")
    int deleteByIds(@Param("ids") List<Long> ids);

}
