package com.baizhi.gjc.dao;

import com.baizhi.gjc.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter>, DeleteByIdListMapper<Counter,String> {
    void UpdateCount(@Param("id") String id,@Param("counts") Integer counts);
}
