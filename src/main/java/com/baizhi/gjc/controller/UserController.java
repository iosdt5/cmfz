package com.baizhi.gjc.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.gjc.dao.UserDao;
import com.baizhi.gjc.entity.User;
import com.baizhi.gjc.entity.UserListener;
import com.baizhi.gjc.service.UserService;
import com.baizhi.gjc.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    //导出
    @RequestMapping("imageUpload")
    public void imageUpload(){
        List<User> users = userDao.selectAll();
        String url="D:\\三阶段资料\\后期项目\\后期项目\\day6-富文本编辑器\\"+new Date().getTime()+".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }
    //导入

    @RequestMapping("leadExcel")
    public void leadExcel(){
        String url = "D:\\三阶段资料\\后期项目\\后期项目\\day6-富文本编辑器\\1578189850418.xls";
        EasyExcel.read(url,User.class,new UserListener()).sheet().doRead();
    }




    //分页查询
    @RequestMapping("userPage")
    public Map userPage(Integer page, Integer rows){
        return userService.selectPage(page,rows);
    }

    @RequestMapping("showUserTime")
    public Map showUserTime(){
        return userService.showUserTime();
    }

    @RequestMapping("showUserMap")
    public Map showUserMap(){
        HashMap hashMap = new HashMap();
        List man = userService.map("1");
        List women = userService.map("2");
        hashMap.put("man",man);
        hashMap.put("women",women);
        return hashMap;
    }
    //增删改
    @RequestMapping("save")
    public Map save(User user, String oper, String[] id){
        //  HashMap hashMap=null;
        if ("add".equals(oper)){

            // hashMap = new HashMap();
            String id1 = UUID.randomUUID().toString();
            user.setId(id1);
            user.setRigestDate(new Date());
            userService.insert(user);

        }else if("edit".equals(oper)){
            userService.update(user);
        }else{
            userService.delete(id);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("userId",user.getId());
        return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile photo, String userId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/user/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/user/");
        // 文件上传 工具类完成
        // 更新数据库信息
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.update(user);
        hashMap.put("status", 200);
        return hashMap;
    }
}
