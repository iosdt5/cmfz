package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.ArticleDao;
import com.baizhi.gjc.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    public List<Article> queryPage(Integer page, Integer rows) {
        return articleDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
    }

    @Override
    public Integer selectCount() {
        return articleDao.selectCount(null);
    }

    @Override
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void delete(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKey(article);
    }

    @Override
    public void updateMap(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }
}
