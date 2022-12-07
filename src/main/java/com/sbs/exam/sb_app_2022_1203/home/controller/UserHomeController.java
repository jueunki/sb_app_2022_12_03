package com.sbs.exam.sb_app_2022_1203.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserHomeController {
  private int count;

  public UserHomeController(){
    count = -1;
  }

  @RequestMapping("/user/home/main")
  @ResponseBody
  public String showMain(){
    return "hi!";
  }
  @RequestMapping("/user/home/main2")
  @ResponseBody
  public String showMain2(){
    return "반가워!";
  }
  @RequestMapping("/user/home/main3")
  @ResponseBody
  public String showMain3(){
    return "잘가!";
  }
  @RequestMapping("/user/home/main4")
  @ResponseBody
  public int showMain4(){
    count++;
    return count;
  }
  @RequestMapping("/user/home/main5")
  @ResponseBody
  public String showMain5(){
    count = 0;
    return "count의 값이 0으로 초기화 되었습니다";
  }

}
