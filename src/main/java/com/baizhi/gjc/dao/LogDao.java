package com.baizhi.gjc.dao;

import com.baizhi.gjc.entity.Log;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LogDao extends Mapper<Log> {
    //添加日志
    public void addLog(Log log);
    //查所有
    public List<Log> queryAll();

}
