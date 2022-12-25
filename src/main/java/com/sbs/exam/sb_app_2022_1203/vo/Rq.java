package com.sbs.exam.sb_app_2022_1203.vo;

import com.sbs.exam.sb_app_2022_1203.service.MemberService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

  @Getter
  private boolean isLogined;
  @Getter
  private int loginedMemberId;
  @Getter
  private Member loginedMember;
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private HttpSession session;
  public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
    this.req = req;
    this.resp = resp;
    this.session = req.getSession();

    HttpSession httpSession = req.getSession();
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      // 로그인 되어있는 상태
      isLogined = true;
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      loginedMember = memberService.getMemberById(loginedMemberId); // 위에는 Id만 가져오는것이고 여기는 작성자까지 가져오려는 부분이다.
      // loginedMemberId가 실제 작성자가 되는것이기 때문에 로그인한 멤버 아이디를 가져오는것이다.
    }
    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
    this.loginedMember = loginedMember;
    this.req.setAttribute("rq", this); // this는 자기자신이다.!!
  }

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8"); //한글 깨짐 방지
    print(Ut.jsHistoryBack(msg));
  }

  public void print(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void println(String str) {
    print(str + "\n");
  }

  public void login(Member member) {
    session.setAttribute("loginedMemberId", member.getId());
  }

  public void logout() {
    session.removeAttribute("loginedMemberId");
  }

  public String historyBackJsOnView(String msg) {
    req.setAttribute("msg", msg);
    req.setAttribute("historyBack", true);
    return "common/js";
  }

  public String jsHistoryBack(String msg) {
    return Ut.jsHistoryBack(msg);
  }

  public String jsReplace(String msg, String uri) {
    return Ut.jsReplace(msg, uri);
  }
}
