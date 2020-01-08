package com.baizhi.gjc.service;

import com.baizhi.gjc.entity.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> queryPage(Integer page, Integer rows);
    Integer selectCount();
    public void insert(Article article);
    public void delete(String[] id);
    public void update(Article article);
    public void updateMap(Article article);
}
