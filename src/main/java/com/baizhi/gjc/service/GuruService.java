package com.baizhi.gjc.service;

import com.baizhi.gjc.entity.Guru;

import java.util.List;

public interface GuruService {
    List<Guru> selectAll();

    public List<Guru> queryPage(Integer page, Integer rows);
    Integer selectCount();
    public void insert(Guru guru);
    public void delete(String[] id);
    public void update(Guru guru);
    public void updateMap(Guru guru);
}
