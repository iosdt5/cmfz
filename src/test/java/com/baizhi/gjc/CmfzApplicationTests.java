package com.baizhi.gjc;

import com.baizhi.gjc.dao.AdminDao;
import com.baizhi.gjc.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
class CmfzApplicationTests {
    @Autowired
    AdminDao adminDao;
    @Autowired
    AdminService adminService;
    @Test
    public void name() {
    }

}
