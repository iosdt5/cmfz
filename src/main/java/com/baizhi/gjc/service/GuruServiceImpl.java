package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.GuruDao;
import com.baizhi.gjc.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public List<Guru> queryPage(Integer page, Integer rows) {

        return guruDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
    }

    @Override
    public Integer selectCount() {
        return guruDao.selectCount(null);
    }

    @Override
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    public void delete(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Guru guru) {
        guruDao.updateByPrimaryKey(guru);
    }

    @Override
    public void updateMap(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
}
