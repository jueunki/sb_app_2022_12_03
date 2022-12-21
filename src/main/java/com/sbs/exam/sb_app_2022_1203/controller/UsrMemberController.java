package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.MemberService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Member;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
  public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname,
                           String cellphoneNo, String email) {


    if (Ut.empty(loginId)) {
      return ResultData.from("F-1", "loginId(을)를 입력 해주세요.");
    }

    if (Ut.empty(loginPw)) {
      return ResultData.from("F-2","loginPw(을)를 입력 해주세요.");
    }

    if (Ut.empty(name)) {
      return ResultData.from("F-3","name(을)를 입력 해주세요.");
    }


    if (Ut.empty(nickname)) {
      return ResultData.from("F-4", "nickname(을)를 입력 해주세요.");
    }


    if (Ut.empty(cellphoneNo)) {
      return ResultData.from("F-5", "cellphoneNo(을)를 입력 해주세요.");
    }

    if (Ut.empty(email)) {
      return ResultData.from("F-6", "email(을)를 입력 해주세요.");
    }


    // S-1
    //회원가입이 완료되었습니다.
    // 회원번호를 바꾸고 싶을때를 대비해 newData를 만듬
    ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);  // 받아야 할 정보가 resultCode 하나이다.

    if (joinRd.isFail()) {
      return (ResultData) joinRd;  // 실패해서 return하는것이다.
    }   //f : 포멧이다.


    Member member = memberService.getMemberById(joinRd.getData1());  //형변환 해준다.

    // 기존 보고서에서 데이터만 member로 바꾼것이다.
    return ResultData.newData(joinRd,"member", member);  //newData :
  }

  @RequestMapping("/user/member/login")  //@ResponseBody를 붙이면 return "user/member/login"; 이 글자를 보여주는것 이라서 붙이면 안된다.
  public String showLogin(HttpSession httpSession) {  //폼으로 보여지는것은 show, 처리되는것은 do 라고 설정했다.
    return "user/member/login"; // 이 요청은 login.jsp에게로 이동되는것이다.
  }


  @RequestMapping("/user/member/doLogin")
  @ResponseBody           //로그인 구현까지 마치면 여기 HttpSession httpsession 이렇게 세션에 저장을 해야한다.
  public String doLogin(HttpServletRequest req, String loginId, String loginPw) {
    Rq rq = (Rq) req.getAttribute("rq");

    if(rq.isLogined()) {
      return Ut.jsHistoryBack("이미 로그인 되었습니다.");
      // 이미 로그인이 되었다고 문구를 띄운다.
    }

    if (Ut.empty(loginId)) {
      return Ut.jsHistoryBack("loginId(을)를 입력 해주세요.");
    }

    if (Ut.empty(loginPw)) {
      return Ut.jsHistoryBack("loginPw(을)를 입력 해주세요.");
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if(member == null) {
      return Ut.jsHistoryBack("존재하지 않는 로그인 아이디 입니다.");
    }

    if(member.getLoginPw().equals(loginPw) == false) {
      return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
    }


    //이렇게 적으면 세션을 지나서 통과되는 문을 만드는것과 같다.
    rq.login(member);


    return Ut.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");

  }
  //로그인 구현까지 마치면 세션에 저장을 해야한다.

  @RequestMapping("/user/member/doLogout")
  @ResponseBody           //로그아웃 구현까지 마치면 여기 HttpSession httpsession 이렇게 세션에 저장을 해야한다.
  public ResultData doLogout(HttpSession httpSession) {

    boolean isLogined = false;

    if (httpSession.getAttribute("loginedMemberId") == null){
      //이미 로그아웃 되어있는 상태라면
      isLogined = true; // true라서 아래로 넘어가서
    }

    if(isLogined) {
      return ResultData.from("S-1", "이미 로그아웃 상태입니다.");
      // 이미 로그아웃이 되었다고 문구를 띄운다.
    }



    //이렇게 적으면 세션을 지나서 통과되는 문을 만드는것과 같다.
    httpSession.removeAttribute("loginedMemberId"); //로그아웃 구현까지 마치면 세션에 저장을 해야한다.


    return ResultData.from("S-2", "로그아웃 되었습니다.");

  }
}


