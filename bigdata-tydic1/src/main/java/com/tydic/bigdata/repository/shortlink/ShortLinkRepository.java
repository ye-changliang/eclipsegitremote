package com.tydic.bigdata.repository.shortlink;

import com.tydic.bigdata.domain.shortlink.ShortLink;
import org.springframework.data.domain.Example;
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
 * @desc 第二步 建立repository  访问数据库层数据
 */
@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "delete from ShortLink e where e.id in (:ids) ")
    int deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 查找最大
     *
     * @return
     */
    @Query(value = "select  max(PRIVILEGE_CODE)+1 from comm_sys_privilege_test where  PARENT_ID=0", nativeQuery = true)
    Long findMaxId();

    /**
     * 保存 记录
     */
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "insert into comm_sys_privilege_test values ( ?1 ,0,?1," +
            "?1,concat('@',?1,'@'),?2,1,4,?3,null ,0,0,0,0,1,?2,'2.png',1,1,?4)", nativeQuery = true)
    int saveLink(Long id, String name, String url, String shortUrl);
}
