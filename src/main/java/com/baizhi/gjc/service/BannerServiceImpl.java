package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.BannerDao;
import com.baizhi.gjc.entity.Banner;
import com.baizhi.gjc.entity.BannerDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    //@Log(name = "轮播图分页")
    public BannerDto queryPage(Banner banner,Integer page,Integer count) {
        BannerDto bannerDto = new BannerDto();
        bannerDto.setPage(page);
        int i = bannerDao.selectCount(new Banner());
        bannerDto.setRecords(i);
        bannerDto.setTotal(i%count==0?i/count:i/count+1);
        Integer inx=(page-1)*count;
        bannerDto.setRows(bannerDao.selectByRowBounds(new Banner(),new RowBounds(inx,count)));
        return bannerDto;
    }

    @Override
    //@Log(name = "轮播图添加")
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    //@Log(name = "轮播图删除")
    public void delete(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
   // @Log(name = "轮播图修改")
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKey(banner);
    }

    @Override
    //@Log(name = "轮播图文件")
    public void updateMap(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
}
