package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.MemberService;
import com.sbs.exam.sb_app_2022_1203.ut.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
public class UsrMemberController {
  private MemberService memberService;
  //alt + insert해서 constructor를 눌러서 만들어준다.
  public UsrMemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //Object : int와 String을 둘다 받으려면 오브젝트를 써준다.
  @RequestMapping("/user/member/doJoin")
  @ResponseBody
  public Object doJoin(String loginId, String loginPw, String name, String nickname,
                       String cellphoneNo, String email) {
    int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

    if( Ut.empty(loginId) ) {
      return  "loginId(을)를 입력 해주세요.";
    }

    if( Ut.empty(loginPw) ) {
      return  "loginPw(을)를 입력 해주세요.";
    }

    if( Ut.empty(name) ) {
      return  "name(을)를 입력 해주세요.";
    }


    if( Ut.empty(nickname) ) {
      return  "nickname(을)를 입력 해주세요.";
    }


    if( Ut.empty(cellphoneNo) ) {
      return  "cellphoneNo(을)를 입력 해주세요.";
    }

    if( Ut.empty(email) ) {
      return  "email(을)를 입력 해주세요.";
    }

    if (id == -1 ) {
      return "해당 로그인 아이디는 이미 사용중 입니다.";
    }

    Member member = memberService.getMemberById(id);

    return member;
  }

}


