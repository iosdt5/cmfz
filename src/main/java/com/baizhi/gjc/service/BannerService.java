package com.baizhi.gjc.service;

import com.baizhi.gjc.entity.Banner;
import com.baizhi.gjc.entity.BannerDto;

public interface BannerService {
    public BannerDto queryPage(Banner banner, Integer page, Integer count);
    public void insert(Banner banner);
    public void delete(String[] id);
    public void update(Banner banner);
    public void updateMap(Banner banner);
}
