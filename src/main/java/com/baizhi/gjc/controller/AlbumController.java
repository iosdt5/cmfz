package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Album;
import com.baizhi.gjc.service.AlbumService;
import com.baizhi.gjc.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("AlbumPage")
    public Map AlbumPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        Integer records = albumService.selectCount();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Album> albums = albumService.queryPage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", albums);
        return hashMap;
    }

    @RequestMapping("albumEdit")
    public Map albumEdit(Album album, String oper,String[] id){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            album.setId(UUID.randomUUID().toString());
            albumService.insert(album);
        } else if ("edit".equals(oper)) {
            albumService.update(album);
        } else if (oper.equals("del")) {
            albumService.delete(id);
        }
        hashMap.put("albumId", album.getId());
        return hashMap;
    }
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(HttpSession session, MultipartFile cover, String albumId, HttpServletRequest request) throws IOException {
        HashMap hashMap = new HashMap();
        System.out.println("zzz");
        String realPath = session.getServletContext().getRealPath("/upload/albumImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        Album album = new Album();
        album.setId(albumId);
        album.setCover(http);
        albumService.updateMap(album);
        hashMap.put("status",200);
        return hashMap;
    }
}
