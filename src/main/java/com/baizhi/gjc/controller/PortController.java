package com.baizhi.gjc.controller;

import com.baizhi.gjc.dao.*;
import com.baizhi.gjc.entity.*;
import com.baizhi.gjc.service.UserService;
import com.baizhi.gjc.util.SmsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("port")
public class PortController {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    GuruDao guruDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    CounterDao counterDao;

    @RequestMapping("UserLogin")
    public Map UserLogin(String phone, String password) {
        User user = userService.userlogin(phone, password);
        HashMap hashMap = new HashMap();
        if (user == null) {
            hashMap.put("struts", "-200");
            hashMap.put("message", "登录失败");
        } else {
            hashMap.put("struts", "200");
            hashMap.put("message", user);
        }
        return hashMap;
    }

    @RequestMapping("/UserCode")
    public Map sendCode(String phone, HttpSession session) {
        String id = UUID.randomUUID().toString();
        String code = id.substring(0, 3);
        SmsUtil.send(phone, code);
        HashMap hashMap = new HashMap();
        //User user = userDao.selectByPrimaryKey(phone);
        if (phone == "") {
            hashMap.put("struts", "200");
            hashMap.put("message", "验证码发送成功");
            session.setAttribute("scode", code);
        } else {
            hashMap.put("struts", "-200");
            hashMap.put("message", "手机号错误");
        }
        return hashMap;
    }

    @RequestMapping("onePage")
    public Map onePage(String uid, String type, String sub_type) {
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")) {
                List<Banner> banners = bannerDao.queryBannersByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.selectAll();
                hashMap.put("status", 200);
                hashMap.put("head", banners);
                hashMap.put("albums", albums);
                hashMap.put("articles", articles);
            } else if (type.equals("wen")) {
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status", 200);
                hashMap.put("albums", albums);
            } else {
                if (sub_type.equals("ssyj")) {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status", 200);
                    hashMap.put("articles", articles);
                } else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status", 200);
                    hashMap.put("articles", articles);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "error");
        }
        return hashMap;
    }

    @RequestMapping("QueryCourse")
    public Map QueryCourse(String userId) {
        Course course = courseDao.selectByPrimaryKey(userId);
        HashMap hashMap = new HashMap();
        if (course != null) {
            hashMap.put("struts", "200");
            hashMap.put("message", course);
        } else {
            hashMap.put("struts", "-200");
            hashMap.put("message", "功课查询失败");
        }
        return hashMap;
    }

    @RequestMapping("InsertCourse")
    public Map InsertCourse(String id, String title, String userId) {
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setCreateDate(new Date());
        course.setTitle(title);
        course.setUserId(userId);
        courseDao.insert(course);
        HashMap hashMap = new HashMap();
        try {
            hashMap.put("struts", "200");
            hashMap.put("message", course);
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("struts", "-200");
            hashMap.put("message", "功课查询失败");
            return hashMap;
        }
    }

    @RequestMapping("DeleteCourse")
    public Map DeleteCourse(String id) {
        courseDao.deleteByPrimaryKey(id);
        HashMap hashMap = new HashMap();
        try {
            hashMap.put("struts", "200");
            hashMap.put("optoin", id);
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("struts", "-200");
            hashMap.put("optoin", "功课删除失败");
            return hashMap;
        }
    }

    @RequestMapping("selectGuru")
    public Map selectGuru(String id) {
        Guru guru = guruDao.selectByPrimaryKey(id);
        HashMap hashMap = new HashMap();
        try {
            hashMap.put("struts", "200");
            hashMap.put("optoin", guru);
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("struts", "-200");
            hashMap.put("optoin", "上师查询失败");
            return hashMap;
        }
    }

    @RequestMapping("UpdateUser")
    public Map UpdateUser(String id, String password, String nickName) {
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setId(id);
        user.setNickName(nickName);
        user.setPassword(password);
        System.out.println(user);
        userService.update(user);
        try {
            hashMap.put("struts", "200");
            hashMap.put("optoin", user);
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("struts", "-200");
            hashMap.put("optoin", "个人信息修改失败");
            return hashMap;
        }
    }

    @GetMapping("SelectArticle")
    public Map SelectArticle(String id,String guruId){
        HashMap hashMap = new HashMap();
        try {
            Example example = new Example(Article.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",id);
            criteria.andEqualTo("guruId",guruId);
            Article article = articleDao.selectOneByExample(example);
            hashMap.put("status","200");
            hashMap.put("article",article);
        }catch (Exception e){
            hashMap.put("status","-200");
            e.printStackTrace();
        }
        return hashMap;
    }
    //查询专辑详情
    @GetMapping("SelectAlbumMeg")
    public Map SelectAlbumMeg(String uid,String id){
        HashMap hashMap = new HashMap();
        Album album = new Album();
        album.setId(id);
        Album album1 = albumDao.selectOne(album);
        //查询该专辑下的所有章节
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("albumId",id);
        List<Chapter> chapters = chapterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("ablum",album1);
        hashMap.put("list",chapters);
        if(chapters.size()==0){
            hashMap.put("status","-200");
            hashMap.put("meg","未找到");
        }
        return hashMap;
    }

    @GetMapping("showAllCounter")
    public Map showAllCounter(String uid,String cid){
        HashMap hashMap = new HashMap();
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //添加计数器
    @PutMapping("addCounter")
    public Map addCounter(String uid,String title,String cid){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setId(UUID.randomUUID().toString());
        counter.setCounts(0);
        counter.setCourseId(cid);
        counter.setCreateDate(new Date());
        counter.setUserId(uid);
        counter.setTitle(title);
        counterDao.insert(counter);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //删除计数器
    @DeleteMapping("deopCounter")
    public Map deopCounter(String uid,String id,String cid){
        HashMap hashMap = new HashMap();
        counterDao.deleteByPrimaryKey(id);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //修改计数器
    @PostMapping("updateCounter")
    public Map updateCounter(String uid,String id,String cid,Integer counts){
        HashMap hashMap = new HashMap();
        counterDao.UpdateCount(id,counts);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }

    @GetMapping("UserQuery")
    public Map UserQuery(String id){
        HashMap hashMap = new HashMap();
        List<User> users =userDao.queryFriend(id);
        hashMap.put("users",users);
        return hashMap;
    }
}
