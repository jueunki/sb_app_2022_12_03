package com.sbs.exam.sb_app_2022_1203.member.service;

import com.sbs.exam.sb_app_2022_1203.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;


@Service
public class MemberService {

  private MemberRepository memberRepository; //@Autowired하면 안된다.
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }



  public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    return memberRepository.getLastInsertId();
  }



  public Member getMemberById(int id) {
  return memberRepository.getMemberById(id);
  }
}
