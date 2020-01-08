package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.AdminDao;
import com.baizhi.gjc.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminDao adminDao;

    @Override
    public Admin select(Admin admin) {
        return adminDao.selectOne(admin);
    }
}
