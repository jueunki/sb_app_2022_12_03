package com.sbs.exam.sb_app_2022_1203.service;

import com.sbs.exam.sb_app_2022_1203.repository.MemberRepository;
import com.sbs.exam.sb_app_2022_1203.vo.Member;
import org.springframework.stereotype.Service;



@Service
public class MemberService {
  private MemberRepository memberRepository;  //@Autowired하면 안된다.

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }


  public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {


    // 로그인 아이디 중복 체크!
    Member oldMember = getMemberByLoginId(loginId);

    if (oldMember != null) {
      return -1;
    }
    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    return memberRepository.getLastInsertId();
  }

  private Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public Member getMemberById(int id) {
    return memberRepository.getMemberById(id);
  }
}
