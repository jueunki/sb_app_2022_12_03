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
      return -1;  //실패하면 -1로 설정.
    }

    // 이름 + 이메일 중복 체크(동시중복 체크)
    oldMember = getMemberByNameAndEmail(name, email);

    if (oldMember != null) {
      return -2; //실패하면 -2로 설정.
    }
    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    return memberRepository.getLastInsertId();
  }

  private Member getMemberByNameAndEmail(String name, String email) {
    return memberRepository.getMemberByNameAndEmail(name, email);
  }

  private Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public Member getMemberById(int id) {
    return memberRepository.getMemberById(id);
  }
}


