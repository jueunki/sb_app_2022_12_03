package com.sbs.exam.sb_app_2022_1203.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrHomeController {

  @RequestMapping("/user/home/main")
  public String showMain() {
    return "user/home/main";
  }

  @RequestMapping("/")
  public String showRoot() {
    return "redirect:/user/home/main";
  }
}

