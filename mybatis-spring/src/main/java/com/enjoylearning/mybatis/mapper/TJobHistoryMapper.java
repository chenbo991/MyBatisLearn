package com.enjoylearning.mybatis.mapper;

import com.enjoylearning.mybatis.entity.TJobHistory;
import java.util.List;

public interface TJobHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_job_history
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_job_history
     *
     * @mbggenerated
     */
    int insert(TJobHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_job_history
     *
     * @mbggenerated
     */
    TJobHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_job_history
     *
     * @mbggenerated
     */
    List<TJobHistory> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_job_history
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TJobHistory record);
}