package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.ChapterDao;
import com.baizhi.gjc.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public List<Chapter> queryPage(Integer page, Integer rows, Chapter chapter) {
        return chapterDao.selectByRowBounds(chapter,new RowBounds((page-1)*rows,rows));
    }

    @Override
    public Integer selectCount(Chapter chapter) {
        return chapterDao.selectCount(chapter);
    }

    @Override
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void delete(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKey(chapter);
    }

    @Override
    public void updateMap(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }
}
