package com.sbs.exam.sb_app_2022_1203.member.controller;

import com.sbs.exam.sb_app_2022_1203.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Member;

@Controller
public class UsrMemberController {
  private MemberService memberService;  //alt + insert해서 constructor를 눌러서 만들어준다.

  public UsrMemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //Object : int와 String을 둘다 받으려면 오브젝트를 써준다.
  @RequestMapping("/user/member/doJoin")
  @ResponseBody
  public Object doJoin(String loginId, String loginPw, String name, String nickname,
                       String cellphoneNo, String email) {
    int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

    if (id == -1 ) {
      return "해당 로그인 아이디는 이미 사용중 입니다.";
    }

    Member member = memberService.getMemberById(id);

    return member;
  }

}


