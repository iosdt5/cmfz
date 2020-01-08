package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Chapter;
import com.baizhi.gjc.service.ChapterService;
import com.baizhi.gjc.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("chapterPage")
    public Map chapterPage(Integer page, Integer rows, String albumId){
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        Integer i = chapterService.selectCount(chapter);
        Integer total = i%rows==0?i/rows:i/rows+1;
        List<Chapter> chapters = chapterService.queryPage(page, rows, chapter);
        hashMap.put("records",i);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapters);
        return hashMap;
    }

    @RequestMapping("chapterEdit")
    // albumId 专辑id  通过editurl参数传递
    public Map editChapter(String oper, Chapter chapter, String[] id,String albumId){
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            Date date = new Date();
            chapter.setCreateTime(date);
            chapter.setId(UUID.randomUUID().toString());
            chapter.setAlbumId(albumId);
            chapterService.insert(chapter);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            chapterService.update(chapter);
            // 删除
        } else {
            chapterService.delete(id);
        }
        hashMap.put("chapterId", chapter.getId());
        return hashMap;
    }

    @RequestMapping("uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length-1];
        chapter.setTitle(name);
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterService.updateMap(chapter);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println("aa");
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}
