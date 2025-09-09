package com.jiawa.train.number.controller;

import com.jiawa.train.number.mapper.NumberMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/number")
public class NumberController {

    @Resource
    NumberMapper numberMapper;

    @GetMapping("/count")

    public int count(){ return (int) numberMapper.countByExample(null);}
}
