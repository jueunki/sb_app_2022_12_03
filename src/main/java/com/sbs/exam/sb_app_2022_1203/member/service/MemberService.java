package com.sbs.exam.sb_app_2022_1203.member.service;

import com.sbs.exam.sb_app_2022_1203.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private MemberRepository memberRepository; //@Autowired하면 안된다.
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }



  public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
  }
}
