package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Article;
import com.baizhi.gjc.service.ArticleService;
import com.baizhi.gjc.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("ArticlePage")
    public Map ArticlePage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        Integer records = articleService.selectCount();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Article> articles = articleService.queryPage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", articles);
        return hashMap;
    }

    @RequestMapping("uploadMap")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        System.out.println("111111");
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            String http = HttpUtil.getHttp(imgFile, request, ("/upload/articleImg/"));
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("erro",1);
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showAllMap")
    public Map showAllMap(HttpServletRequest request,HttpSession session){
        System.out.println("22222222");
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }

    @RequestMapping("ArticleInsert")
    public void ArticleInsert(Article article,HttpSession session,HttpServletRequest request,MultipartFile inputfile){
        if(article.getId()==null||"".equals(article.getId())){
            System.out.println("11111111");
            String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
                String http = HttpUtil.getHttp(inputfile, request, ("/upload/articleImg/"));
                article.setImg(http);
                article.setId(UUID.randomUUID().toString());
                article.setCreateDate(new Date());
                article.setPublishDate(new Date());
            System.out.println(http);
            articleService.insert(article);

        }else {
            System.out.println("22222222");
            String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String http = HttpUtil.getHttp(inputfile, request, ("/upload/articleImg/"));
            article.setImg(http);
            System.out.println(article);
            articleService.updateMap(article);

        }
    }

    @RequestMapping("ArticleDelete")
    public void ArticleInsert(String[] id){
        System.out.println(id);
        articleService.delete(id);
    }
}


