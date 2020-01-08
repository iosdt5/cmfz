package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Guru;
import com.baizhi.gjc.service.GuruService;
import com.baizhi.gjc.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("guru")
public class GuruController {
   @Autowired
    GuruService guruService;

    @RequestMapping("showAllGuru")
    public List<Guru> showAllGuru(){
        List<Guru> gurus = guruService.selectAll();
        System.out.println(gurus);
        return gurus;
    }

    @RequestMapping("GuruPage")
    public Map AlbumPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        Integer records = guruService.selectCount();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Guru> gurus = guruService.queryPage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", gurus);
        return hashMap;
    }

    @RequestMapping("GuruEdit")
    public Map albumEdit(Guru guru, String oper,String[] id){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            guru.setId(UUID.randomUUID().toString());
            guruService.insert(guru);
        } else if ("edit".equals(oper)) {
            System.out.println(guru);
            guruService.update(guru);
        } else if (oper.equals("del")) {
            System.out.println(id);
            guruService.delete(id);
        }
        hashMap.put("guruId", guru.getId());
        return hashMap;
    }
    @RequestMapping("uploadGuru")
    public Map uploadAlbum(HttpSession session, MultipartFile photo, String guruId, HttpServletRequest request) throws IOException {
        HashMap hashMap = new HashMap();
        System.out.println("zzz");
        String realPath = session.getServletContext().getRealPath("/upload/guruImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(photo, request, "/upload/guruImg/");
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(http);
        guruService.updateMap(guru);
        hashMap.put("status",200);
        return hashMap;
    }
}
