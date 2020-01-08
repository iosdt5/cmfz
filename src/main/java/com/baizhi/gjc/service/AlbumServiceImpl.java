package com.baizhi.gjc.service;

import com.baizhi.gjc.dao.AlbumDao;
import com.baizhi.gjc.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override

    public List<Album> queryPage(Integer page, Integer rows) {
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page-1)*rows, rows));
        return albums;
    }

    @Override
    public Integer selectCount() {
        int i = albumDao.selectCount(null);
        return i;
    }

    @Override
    public void insert(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void delete(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Album album) {
        albumDao.updateByPrimaryKey(album);
    }

    @Override
    public void updateMap(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }
}
