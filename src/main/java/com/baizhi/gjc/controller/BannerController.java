package com.baizhi.gjc.controller;

import com.baizhi.gjc.entity.Banner;
import com.baizhi.gjc.entity.BannerDto;
import com.baizhi.gjc.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/bannerPage")
    @ResponseBody
    public BannerDto bannerPage(Integer page, Integer rows) {
        BannerDto queryPage = bannerService.queryPage(new Banner(), page, rows);
        return queryPage;
    }

    @RequestMapping("/banner")
    @ResponseBody
    public Map banner(Banner banner, String oper,String[] id) {
        HashMap hashMap = new HashMap();
            if ("add".equals(oper)) {
                banner.setId(UUID.randomUUID().toString());
                bannerService.insert(banner);
            } else if ("edit".equals(oper)) {
                bannerService.update(banner);
            } else if ("del".equals(oper)) {
                bannerService.delete(id);
            }
        hashMap.put("bannerId", banner.getId());
        return hashMap;
    }
    @RequestMapping("/uploadMap")
    public void uploadMap(HttpSession session,String bannerId, MultipartFile url) throws IOException {
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 防止文件重名
        String name = url.getOriginalFilename()+"_"+new Date().getTime();
            url.transferTo(new File(realPath,name));
            Banner banner = new Banner();//设置id(找到原始数据) url(更新使用)
            banner.setId(bannerId);
            banner.setUrl("/upload/img/"+name);
            bannerService.updateMap(banner);
    }
}
