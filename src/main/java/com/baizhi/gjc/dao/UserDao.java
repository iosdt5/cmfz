package com.baizhi.gjc.dao;

import com.baizhi.gjc.entity.User;
import com.baizhi.gjc.entity.UserMapDTO;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    public Integer queryUserByTime(@Param("sex") String sex,@Param("day") Integer day);
    public List<UserMapDTO> map(@Param("sex") String sex);
    public User userlogin(@Param("phone")String phone,@Param("password") String password);
    public List<User>  queryFriend(String id);
}
