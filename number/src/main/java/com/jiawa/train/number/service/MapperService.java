package com.jiawa.train.number.service;

import com.jiawa.train.number.mapper.NumberMapper;
import jakarta.annotation.Resource;


public class MapperService {
    @Resource
    NumberMapper numberMapper;

    public int count(){
        return numberMapper.count();
    }
}
