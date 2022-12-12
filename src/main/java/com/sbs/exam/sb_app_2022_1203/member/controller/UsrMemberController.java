package com.sbs.exam.sb_app_2022_1203.member.controller;

import com.sbs.exam.sb_app_2022_1203.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
  private MemberService memberService;  //alt + insert해서 constructor를 눌러서 만들어준다.

  public UsrMemberController(MemberService memberService) {
    this.memberService = memberService;
  }


  @RequestMapping("/user/member/doJoin")
  @ResponseBody
  public String doJoin(String loginId, String loginPw, String name, String nickname,
                       String cellphoneNo, String email) {
    memberService.join(loginId, loginPw, name, nickname,
        cellphoneNo, email);
    return "성공!";
  }

}


