package com.apptrainer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apptrainer.constant.AppTrainerConstant;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/configure")
public class PageController {
    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
    }
}