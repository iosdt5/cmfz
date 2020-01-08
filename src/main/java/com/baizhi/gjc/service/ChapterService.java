package com.baizhi.gjc.service;

import com.baizhi.gjc.entity.Chapter;

import java.util.List;

public interface ChapterService {
    public List<Chapter> queryPage(Integer page, Integer rows, Chapter chapter);
    Integer selectCount(Chapter chapter);
    public void insert(Chapter chapter);
    public void delete(String[] id);
    public void update(Chapter chapter);
    public void updateMap(Chapter chapter);
}
