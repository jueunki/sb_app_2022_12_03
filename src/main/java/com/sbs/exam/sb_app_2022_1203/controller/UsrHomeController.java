package com.sbs.exam.sb_app_2022_1203.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {

  @RequestMapping("/user/home/main")
  @ResponseBody
  public String main(){
    return "안녕";
  }
}

