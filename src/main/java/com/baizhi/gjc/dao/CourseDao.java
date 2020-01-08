package com.baizhi.gjc.dao;

import com.baizhi.gjc.entity.Course;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends Mapper<Course>, DeleteByIdListMapper<Course,String> {
    public void delete(@Param("id")String id,@Param("userId")String userId);
}
