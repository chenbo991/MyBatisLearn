package com.enjoylearning.mybatis.mapper;

import com.enjoylearning.mybatis.entity.TRole;
import java.util.List;

public interface TRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbggenerated
     */
    int insert(TRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbggenerated
     */
    TRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbggenerated
     */
    List<TRole> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TRole record);
}