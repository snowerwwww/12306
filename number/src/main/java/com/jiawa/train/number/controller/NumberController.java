package com.jiawa.train.number.controller;

import com.jiawa.train.number.service.NumberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
public class NumberController {

    @Resource
    NumberService numberService;

    @GetMapping("/count")
    public Integer count(){
        return numberService.count();
    }

    @PostMapping("/register")
    public long register(String mobile){
        return numberService.register(mobile);
    }
}
