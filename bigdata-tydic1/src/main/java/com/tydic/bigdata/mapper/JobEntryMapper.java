package com.tydic.bigdata.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobEntryMapper {

    /**
     * 通过名字 或Id 获取任务
     *
     * @return
     */
    List<Map> findJobByNameOrId(@Param("name") String name, @Param("id") String id);

    /**
     * 获取任务执行状态
     *
     * @param id
     * @return
     */
    Map findJobStatusById(@Param("id") String id);

    /**
     * 获取任务执行状态
     *
     * @param id
     * @return
     */
    Map findTransStatusById(@Param("id") String id);


    /**
     * 获取任务组件信息
     *
     * @param id
     * @return
     */
    List<Map> findJobDataSource(@Param("jobId") String id);

    /**
     * 获取转换组件信息
     *
     * @param id
     * @return
     */
    List<Map> findTransDataSource(@Param("jobId") String id);


    /**
     * 获取数据源信息
     *
     * @return
     */
    List<Map> findAllDataSource(@Param("id") String id);

    /**
     * 更新数据源
     *
     * @param jobId
     * @param dataBaseId
     * @param jobEntryId
     * @return
     */
    Integer updateJobDataSourceAttr(@Param("jobId") String jobId, @Param("dataBaseId") String dataBaseId, @Param("jobEntryId") String jobEntryId);

    /**
     * 更新数据源
     *
     * @param jobId
     * @param dataBaseId
     * @param jobEntryId
     * @return
     */
    Integer updateJobDataSource(@Param("jobId") String jobId, @Param("dataBaseId") String dataBaseId, @Param("jobEntryId") String jobEntryId);

    /**
     * 更新数据源
     *
     * @param jobId
     * @param dataBaseId
     * @param jobEntryId
     * @return
     */
    Integer updateTransDataSourceAttr(@Param("jobId") String jobId, @Param("dataBaseId") String dataBaseId, @Param("jobEntryId") String jobEntryId);
    /**
     * 更新数据源
     *
     * @param jobId
     * @param dataBaseId
     * @param jobEntryId
     * @return
     */
    Integer updateTransDataSource(@Param("jobId") String jobId, @Param("dataBaseId") String dataBaseId, @Param("jobEntryId") String jobEntryId);


    /**
     * 获取子job 和trans
     * @param jobId
     * @return
     */
    List<Map> selectChildrenJob(@Param("jobId") String jobId,@Param("type") String type);

    /**
     * 查找子job或者transId
     * @param jobEntryId
     * @return
     */
   String selectJobAttr(@Param("jobEntryId") String jobEntryId);
}
