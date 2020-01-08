package com.baizhi.gjc.service;

import com.baizhi.gjc.entity.Album;

import java.util.List;

public interface AlbumService  {
    public List<Album> queryPage(Integer page, Integer rows);
    Integer selectCount();
    public void insert(Album album);
    public void delete(String[] id);
    public void update(Album album);
    public void updateMap(Album album);
}
