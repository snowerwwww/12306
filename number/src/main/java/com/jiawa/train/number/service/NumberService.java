package com.jiawa.train.number.service;

import com.jiawa.train.number.domain.Number;
import com.jiawa.train.number.mapper.NumberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class NumberService {
    @Resource
    private NumberMapper numberMapper;

    public int count(){
        return  Math.toIntExact(numberMapper.countByExample(null));
    }
    public long register(String mobile){
        Number number = new Number();
        number.setId(System.currentTimeMillis());
        number.setMobile(mobile);
        numberMapper.insert(number);
        return number.getId();
    }
}
